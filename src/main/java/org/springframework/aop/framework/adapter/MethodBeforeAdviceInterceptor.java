package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.BeforeAdvice;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
    private BeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(BeforeAdvice beforeAdvice) {
        this.advice = beforeAdvice;
    }

    public MethodBeforeAdviceInterceptor() {
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.proceed();
    }

    public BeforeAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(BeforeAdvice beforeAdvice) {
        this.advice = beforeAdvice;
    }
}
