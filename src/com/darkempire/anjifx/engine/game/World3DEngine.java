package com.darkempire.anjifx.engine.game;

import com.darkempire.anji.annotation.AnjiUnknow;
import com.darkempire.anji.engine.Engine;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by siredvin on 26.12.13.
 */
@AnjiUnknow
public abstract class World3DEngine extends Engine {
    private Pane worldRegion;
    protected int width, height, deep;

    public World3DEngine(Pane worldRegion) {
        super();
        this.worldRegion = worldRegion;
    }

    public void addNode(Node node) {
        worldRegion.getChildren().add(node);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDeep() {
        return deep;
    }

    public Pane getWorldRegion() {
        return worldRegion;
    }

}
