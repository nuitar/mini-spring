package org.springframework.aop;

public interface PointCut {
    ClassFilter getClassFilter();
    MethodMacher getMethodFilter();
}
