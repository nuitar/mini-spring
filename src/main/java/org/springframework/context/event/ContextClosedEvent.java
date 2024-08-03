package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;

public class ContextClosedEvent extends ApplicationEvent {
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
