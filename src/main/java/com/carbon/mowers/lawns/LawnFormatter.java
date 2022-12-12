package com.carbon.mowers.lawns;

import com.carbon.mowers.lawns.models.Lawn;
import com.carbon.mowers.lawns.models.Mower;
import com.carbon.mowers.lawns.models.position.Orientation;

import java.util.List;
import java.util.stream.Collectors;

public class LawnFormatter implements Formatter {

    @Override
    public String format(Lawn lawn) {
        return formatMowers(lawn.mowers());
    }

    private String formatMowers(List<Mower> mowers) {
        return mowers.stream().map(this::formatMowerInformation).collect(Collectors.joining(System.lineSeparator()));
    }

    private String formatMowerInformation(Mower mower) {
        return String.format("%d %d %s", mower.coordinates().x(),
                mower.coordinates().y(),
                formatOrientation(mower.orientation()));
    }

    private String formatOrientation(Orientation orientation) {
        return switch (orientation) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }
}
