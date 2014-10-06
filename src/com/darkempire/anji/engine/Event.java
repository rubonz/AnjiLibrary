package com.darkempire.anji.engine;

import com.darkempire.anji.annotation.AnjiUnknow;

/**
 * Created by siredvin on 26.12.13.
 */
@AnjiUnknow
public interface Event {
    public static enum EventType {
        UPDATE, DELETE, RESET, STOP, START;
    }

    public EventType getEvent();

    public Object getSource();
}
