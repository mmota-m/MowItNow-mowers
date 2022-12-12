package com.carbon.mowers.models;
import com.carbon.mowers.models.position.Coordinates;
import com.carbon.mowers.models.position.Orientation;

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

}
