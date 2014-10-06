package com.darkempire.anji.engine;

import com.darkempire.anji.annotation.AnjiUnknow;

/**
 * Created by siredvin on 27.12.13.
 */
@AnjiUnknow
public interface EngineElement {
    public void update();

    public void stop();

    public void init();

    public void start();

    public Engine getEngine();
}
