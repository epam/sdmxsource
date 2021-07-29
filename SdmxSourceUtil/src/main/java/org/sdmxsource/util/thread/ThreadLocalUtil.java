package org.sdmxsource.util.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The type Thread local util.
 */
public class ThreadLocalUtil {
    /**
     * The constant CLIENT_REQUEST.
     */
    public static final String CLIENT_REQUEST = "CLIENT_REQUEST";
    /**
     * The constant SESSION.
     */
    public static final String SESSION = "SESSION";
    /**
     * The constant WEB_SERVICE_TYPE.
     */
    public static final String WEB_SERVICE_TYPE = "WEB_SERVICE_TYPE";
    /**
     * The constant ACTION.
     */
    public static final String ACTION = "ACTION";
    /**
     * The constant UUID_KEY.
     */
    public static final String UUID_KEY = "UUID";
    /**
     * The constant REQUEST_ORIGIN.
     */
    public static final String REQUEST_ORIGIN = "REQUEST_ORIGIN";


    private static LocalObjectStore localObjectStore = new LocalObjectStore();

    private ThreadLocalUtil() {
    }

    /**
     * Create uid.
     */
    public static void createUID() {
        storeOnThread(UUID_KEY, UUID.randomUUID().toString());
    }

    /**
     * Remove uid.
     */
    public static void removeUID() {
        retrieveFromThread(UUID_KEY);
    }

    /**
     * Gets a Unique id for this thread
     *
     * @return uid uid
     */
    public static String getUID() {
        if (contains(UUID_KEY)) {
            return (String) localObjectStore.get().get(UUID_KEY);
        }
        return null;
    }

    /**
     * Store on thread.
     *
     * @param key the key
     * @param obj the obj
     */
    public static void storeOnThread(Object key, Object obj) {
        localObjectStore.get().put(key, obj);
    }

//	public static void storeClientRequest(ClientRequest clientRequest) {
//		storeOnThread(CLIENT_REQUEST, clientRequest);
//	}
//	
//	public static ClientRequest getClientRequest() {
//		if(contains(CLIENT_REQUEST)) {
//			return (ClientRequest) localObjectStore.get().get(CLIENT_REQUEST);
//		}
//		return null;
//	}

    /**
     * Retrieve from thread object.
     *
     * @param key the key
     * @return the object
     */
    public static Object retrieveFromThread(Object key) {
        return localObjectStore.get().get(key);
    }

    /**
     * Contains boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean contains(Object key) {
        return localObjectStore.get().containsKey(key);
    }

    /**
     * Clear from thread.
     *
     * @param key the key
     */
    public static void clearFromThread(Object key) {
        localObjectStore.get().remove(key);
    }

    /**
     * Remove everything stored on this thread
     */
    public static void clearThread() {
        localObjectStore.get().clear();
    }

    /**
     * Returns a copy of everything stored on the local thread
     *
     * @return thread contents
     */
    public static Map<Object, Object> getThreadContents() {
        return new HashMap<Object, Object>(localObjectStore.get());
    }

    /**
     * Close.
     */
    public static void close() {
        localObjectStore = null;
    }

    private static class LocalObjectStore extends ThreadLocal<Map<Object, Object>> {
        @Override
        public Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>();
        }
    }
}