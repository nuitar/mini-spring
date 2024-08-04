package org.springframework.aop;

import org.aopalliance.aop.Advice;

public interface PointcutAdvisor extends Advisor {
    PointCut getPointCut();
}
