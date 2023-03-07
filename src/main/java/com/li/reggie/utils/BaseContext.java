package com.li.reggie.utils;

public class BaseContext {
    private static ThreadLocal<Long> thread = new ThreadLocal<Long>();

    public static void set(Long id) {
        thread.set(id);
    }

    public static Long get() {
        return thread.get();
    }
}
