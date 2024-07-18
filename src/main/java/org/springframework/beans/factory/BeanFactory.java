package org.springframework.beans.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BeanFactory {
    private Map<String, Object>beanMap = new HashMap<>();
    public void registerBean(String name,Object obj){
        beanMap.put(name,obj);
    }

    public Object getBean(String name){
        return  beanMap.get(name);
    }
}
