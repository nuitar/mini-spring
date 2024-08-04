package org.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class GenericInterceptor implements MethodInterceptor {
    private AfterAdvice afterAdvice;
    private BeforeAdvice beforeAdvice;
    private AfterReturningAdvice afterReturningAdvice;
    private ThrowsAdvice throwsAdvice;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;

        try {
            if (beforeAdvice != null) {
                beforeAdvice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
            }
            result = methodInvocation.proceed();
        } catch (Exception ex) {
            if (throwsAdvice != null) {
                throwsAdvice.throwsHandle(ex, methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
            }
        } finally {
            if (afterAdvice != null) {
                afterAdvice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
            }
        }

        if (afterReturningAdvice != null)
            afterReturningAdvice.afterReturning(result, methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return result;
    }

    public AfterAdvice getAfterAdvice() {
        return afterAdvice;
    }

    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }

    public BeforeAdvice getBeforeAdvice() {
        return beforeAdvice;
    }

    public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public AfterReturningAdvice getAfterReturningAdvice() {
        return afterReturningAdvice;
    }

    public void setAfterReturningAdvice(AfterReturningAdvice afterReturningAdvice) {
        this.afterReturningAdvice = afterReturningAdvice;
    }

    public ThrowsAdvice getThrowsAdvice() {
        return throwsAdvice;
    }

    public void setThrowsAdvice(ThrowsAdvice throwsAdvice) {
        this.throwsAdvice = throwsAdvice;
    }

}
