package org.springframework.aop;

public class TargetSource {
    public TargetSource(Object target) {
        this.target = target;
    }

    private final Object target;

    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }
    public Object getTarget() {
        return target;
    }
}
