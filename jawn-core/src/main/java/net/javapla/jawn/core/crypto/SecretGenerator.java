package net.javapla.jawn.core.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SecretGenerator {
    
    public static final int AES_SECRET_LENGTH = 64;
    
    public static String generate() {
        try {
            return generate(SecureRandom.getInstance("SHA1PRNG"), AES_SECRET_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            return generate(new SecureRandom(), AES_SECRET_LENGTH);
        }
    }

    protected static String generate(Random random, final int length) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

        char[] result = new char[length];

        for (int i = 0; i < length; i++) {
            int pick = random.nextInt(chars.length);            
            result[i] = chars[pick];
        }

        return new String(result);
    }
}
