package ru.rubalkin.stash;


import java.util.concurrent.ConcurrentHashMap;

public class Stash {
    private static final ThreadLocal<ConcurrentHashMap<String, ?>> stash = ThreadLocal.withInitial(ConcurrentHashMap::new);

    private Stash() {}

    public static <T> void store(String key, T variable) {
        ConcurrentHashMap<String, T> chm =
                (ConcurrentHashMap<String, T>) stash.get();
        if(chm.containsKey(key)) {
            chm.replace(key, variable);
        } else {
            chm.put(key, variable);
        }
    }

    public static<T> T get(String key) {
        ConcurrentHashMap<String, T> chm =
                (ConcurrentHashMap<String, T>) stash.get();
        return chm.get(key);
    }
}
