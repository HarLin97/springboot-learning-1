package com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.core;

import java.lang.reflect.Method;

public class MethodWrapper {
    private final Method method;
    private final boolean present;

    private MethodWrapper(Method method, boolean present) {
        this.method = method;
        this.present = present;
    }

    static MethodWrapper wrap(Method method) {
        return method == null ? none() : new MethodWrapper(method, true);
    }

    static MethodWrapper none() {
        return new MethodWrapper(null, false);
    }

    Method getMethod() {
        return this.method;
    }

    boolean isPresent() {
        return this.present;
    }
}
