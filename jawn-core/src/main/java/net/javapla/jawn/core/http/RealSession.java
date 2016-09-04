package net.javapla.jawn.core.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.inject.Inject;

import net.javapla.jawn.core.crypto.Crypto;
import net.javapla.jawn.core.util.Constants;
import net.javapla.jawn.core.util.DataCodec;
import net.javapla.jawn.core.util.StringUtil;

//SessionImpl
public class RealSession implements Session {
    
    private final Crypto crypto;
    
    private long sessionExpiryTimeInMs;
    private final boolean cookieOnlySession;
    private final String sessionCookieName;
    private final String applicationSecret; //TODO probably needs to be store somewhere else (perhaps the Crypto)
    private final boolean applicationCookieEncryption;
    
    private final Map<String, String> data = new HashMap<>();
    
    private boolean sessionDataHasChanged = false;




    @Inject
    public RealSession(Crypto crypto) {
        this.crypto = crypto;
        
        //TODO read a bunch of properties
        sessionExpiryTimeInMs = -1;
        cookieOnlySession = true; // if false have the cookie only save a session id that we then look up
        sessionCookieName = "jawncookie" + Constants.SESSION_SUFFIX;
        applicationSecret = "gawdDamnSecretThisIs!";
        applicationCookieEncryption = true;
        
    }

    @Override
    public void init(Context context) {
        Cookie cookie = context.getCookie(sessionCookieName);
        
        if (cookie != null && cookie.getValue() != null && !cookie.getValue().trim().isEmpty()) {
            String value = cookie.getValue();
            
            // the first substring until "-" is the sign
            String sign = value.substring(0, value.indexOf("-"));

            // rest from "-" until the end is the payload of the cookie
            String payload = value.substring(value.indexOf("-") + 1);

            
            // check if payload is valid:
            if (DataCodec.safeEquals(sign, crypto.hmac().sign(payload, applicationSecret))) {
                if (applicationCookieEncryption)
                    payload = crypto.encrypter().decrypt(payload);
                DataCodec.decode(data, payload);
            }

            // If an expiry time was set previously use that instead of the
            // default session expire time.
            if (data.containsKey(EXPIRY_TIME_KEY)) {
                Long expiryTime = Long.parseLong(data.get(EXPIRY_TIME_KEY));
                if (expiryTime >= 0) {
                    sessionExpiryTimeInMs = expiryTime;
                }
            }

            checkExpire();
        }
    }

    @Override
    public String getId() {
        if (!data.containsKey(ID_KEY)) {
            String uuid = UUID.randomUUID().toString();
            put(ID_KEY, uuid);
            return uuid;
        }

        return get(ID_KEY);
    }

    @Override
    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(data);
    }

    @Override
    public void put(String key, Object value) {
        if (StringUtil.contains(key, ':')) throw new IllegalArgumentException(); //TODO
        
        sessionDataHasChanged = true;
        
        if (value == null) {
            remove(key);
        } else {
            data.put(key, (String) value); // TODO make sure think about how methods should behave if cookieOnlySession (maps will only hold strings)
        }
    }

    @Override
    public String get(String key) {
        return data.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        Object o = data.get(key);
        return o == null? null : type.cast(o);
    }

    @Override
    public Object remove(String key) {
        return data.remove(key);
    }

    @Override
    public <T> T remove(String key, Class<T> type) {
        Object o = data.remove(key);
        return o == null? null : type.cast(o);
    }

    @Override
    public void invalidate() {
        sessionDataHasChanged = true;
        data.clear();
    }

    @Override
    public boolean isEmpty() {
        int itemsToIgnore = 0;
        if (data.containsKey(TIMESTAMP_KEY)) {
            itemsToIgnore++;
        }
        if (data.containsKey(EXPIRY_TIME_KEY)) {
            itemsToIgnore++;
        }
        return (data.isEmpty() || data.size() == itemsToIgnore);
    }

    @Override
    public void setExpiryTime(long expiryTimeMS) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void save(Context context) {
        if (!sessionDataHasChanged && sessionExpiryTimeInMs <= 0) return;
        
        if (isEmpty()) {
            if (context.hasCookie(sessionCookieName)) {
                Cookie cookie = createApplicationCookie("", context).build();
                context.addCookie(cookie);
            }
            return;
        }
        
        if (sessionExpiryTimeInMs > 0 && !data.containsKey(TIMESTAMP_KEY)) {
            data.put(TIMESTAMP_KEY, Long.toString(System.currentTimeMillis()));
        }
        
        String sessionData = DataCodec.encode(data);
        // first encrypt data and then generate HMAC from encrypted data
        // http://crypto.stackexchange.com/questions/202/should-we-mac-then-encrypt-or-encrypt-then-mac
        if (applicationCookieEncryption)
            sessionData = crypto.encrypter().encrypt(sessionData);
        String signing = crypto.hmac().sign(sessionData, applicationSecret);
        
        Cookie.Builder cookieBuilder = createApplicationCookie(signing + "-" + sessionData, context);
        if (sessionExpiryTimeInMs > 0) {
            cookieBuilder.setMaxAge((int) (sessionExpiryTimeInMs / 1000));
        }
        context.addCookie(cookieBuilder.build());
    }
    
    private static final String TIMESTAMP_KEY = "__TK";
    private static final String EXPIRY_TIME_KEY = "__ETK";
    private static final String ID_KEY = "___ID";
    protected boolean shouldExpire() {
        if (sessionExpiryTimeInMs > 0) {
            if (!data.containsKey(TIMESTAMP_KEY)) {
                return true;
            }
            
            long timestamp = Long.parseLong(data.get(TIMESTAMP_KEY));
            return timestamp + sessionExpiryTimeInMs < System.currentTimeMillis();
        }
        return false;
    }
    
    private void checkExpire() {
        if (sessionExpiryTimeInMs > 0) {
            if (shouldExpire()) {
                sessionDataHasChanged = true;
                data.clear();
            } else {
                // Seems okay - prolong session
                data.put(TIMESTAMP_KEY, "" + System.currentTimeMillis());
            }
        }
    }

    private Cookie.Builder createApplicationCookie(String value, Context context) {
        return Cookie.builder(sessionCookieName, value)
                .setPath(context.contextPath() + "/")
                //.setDomain(domain)
                //.setSecure(secure)
                //.setHttpOnly(httpOnly)
                ;
    }
}