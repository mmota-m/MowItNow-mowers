package com.carbon.mowers.lawns.models;

import com.carbon.mowers.lawns.models.position.Coordinates;
import com.carbon.mowers.lawns.models.position.Dimension;

import java.util.Collections;
import java.util.List;

public record Lawn(Dimension dimension, List<Mower> mowers) {
    public Lawn(Dimension dimension, List<Mower> mowers) {
        this.dimension = dimension;
        this.mowers = Collections.unmodifiableList(mowers);
    }

    public boolean isInsideLawn(Coordinates point) {
        return point.x() <= this.dimension().width() & point.y() <= this.dimension().height()
                & point.x() >= 0 & point.y() >= 0;
    }
}
