package org.springframework.aop.framework;

import org.springframework.aop.AdvisedSupport;

public class ProxyFactory {
    private AdvisedSupport advised;


    public ProxyFactory(AdvisedSupport advised) {
        this.advised = advised;
    }

    public Object getProxy(){
        return createAopProxy().getProxy();
    }
    public AopProxy createAopProxy(){
        if(advised.isProxyTargetClass()){
            return new CglibAopProxy(advised);
        }
        return new JdkDynamicAopProxy(advised);
    }
}
