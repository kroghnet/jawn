package net.javapla.jawn.server.undertow;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.undertow.server.session.Session;
import net.javapla.jawn.core.http.SessionFacade;

public class UndertowSession implements SessionFacade {

	private Session session;
	
	void init(Session session) {
		this.session = session;
	}
	
	@Override
	public int size() {
		return session.getAttributeNames().size();
	}

	@Override
	public boolean isEmpty() {
		return session.getAttributeNames().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return session.getAttributeNames().contains(key + "");
	}

	@Override
	public boolean containsValue(Object value) {
		return session.getAttributeNames().stream().anyMatch(k -> session.getAttribute(k).equals(value));
	}

	@Override
	public Object get(Object key) {
		return session.getAttribute(key + "");
	}

	@Override
	public Object put(String key, Object value) {
		return session.setAttribute(key, value);
	}

	@Override
	public Object remove(Object key) {
		return session.removeAttribute(key + "");
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		m.forEach((k,v) -> session.setAttribute(k, v));
	}

	@Override
	public void clear() {
		session.getAttributeNames().forEach(k -> session.removeAttribute(k));
	}

	@Override
	public Set<String> keySet() {
		return session.getAttributeNames();
	}

	@Override
	public Collection<Object> values() {
		return session.getAttributeNames().stream().map(k -> session.getAttribute(k)).collect(Collectors.toList());
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(String name, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object put(String name, Serializable value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCreationTime() {
		return session.getCreationTime();
	}

	@Override
	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getTimeToLive() {
		return session.getMaxInactiveInterval();
	}

	@Override
	public void setTimeToLive(int l) {
		session.setMaxInactiveInterval(l);
	}

	@Override
	public Object getAttribute(String name) {
		return session.getAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		session.setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		session.removeAttribute(name);
	}

	@Override
	public String[] names() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return session.getId();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
