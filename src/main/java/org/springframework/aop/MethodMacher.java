package org.springframework.aop;

import java.lang.reflect.Method;

public interface MethodMacher {
    boolean matches(Method method,Class<?> clazz);
}
