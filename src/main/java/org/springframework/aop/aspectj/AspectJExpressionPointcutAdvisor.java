package org.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.PointCut;
import org.springframework.aop.PointcutAdvisor;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    private Advice advice;
    private AspectJExpressionPointcut pointcut;

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setPointcut(AspectJExpressionPointcut pointcut) {
        this.pointcut = pointcut;
    }


    public void setExpression(String expression){
        this.pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public PointCut getPointCut() {
        return pointcut;
    }
}
