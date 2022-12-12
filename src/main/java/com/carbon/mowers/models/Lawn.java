package com.carbon.mowers.models;

import com.carbon.mowers.models.position.Dimension;

import java.util.Collections;
import java.util.List;

public record Lawn(Dimension dimension, List<Mower> mowers) {
    public Lawn(Dimension dimension, List<Mower> mowers) {
        this.dimension = dimension;
        this.mowers = Collections.unmodifiableList(mowers);
    }
}
