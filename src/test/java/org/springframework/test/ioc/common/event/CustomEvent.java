package org.springframework.test.ioc.common.event;

import org.springframework.context.event.ApplicationContextEvent;

public class CustomEvent extends ApplicationContextEvent {
    public CustomEvent(Object source) {
        super(source);
    }
}
