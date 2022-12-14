package com.carbon.mowers.lawns.models;
import com.carbon.mowers.lawns.models.position.Coordinates;
import com.carbon.mowers.lawns.models.position.Orientation;

import java.util.Collections;
import java.util.List;

public record Mower(
        Coordinates coordinates,
        Orientation orientation,
        List<Instruction> instructions
) {
    public Mower(
            Coordinates coordinates,
            Orientation orientation,
            List<Instruction> instructions
    ) {
        this.coordinates = coordinates;
        this.orientation = orientation;
        this.instructions = Collections.unmodifiableList(instructions);
    }

    public Mower withCoordinates(Coordinates coordinates) {
        return new Mower(coordinates, orientation(), instructions());
    }

    public Mower withOrientation(Orientation orientation) {
        return new Mower(coordinates(), orientation, instructions());
    }

}
