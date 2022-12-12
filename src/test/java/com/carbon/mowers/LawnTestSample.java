package com.carbon.mowers;

import com.carbon.mowers.lawns.models.Instruction;
import com.carbon.mowers.lawns.models.Mower;
import com.carbon.mowers.lawns.models.position.Coordinates;
import com.carbon.mowers.lawns.models.position.Dimension;
import com.carbon.mowers.lawns.models.position.Orientation;

import java.util.List;

public class LawnTestSample {

    public static final List<Instruction> instructionsSample = List.of(
            Instruction.TURN_LEFT, Instruction.FORWARD,
            Instruction.TURN_LEFT, Instruction.FORWARD,
            Instruction.TURN_LEFT, Instruction.FORWARD,
            Instruction.TURN_LEFT, Instruction.FORWARD, Instruction.FORWARD);

    public static final Mower mowerCreationSample = new Mower(
            new Coordinates(1, 2),
            Orientation.NORTH,
            instructionsSample);

    public static final Coordinates coordinatesSample = new Coordinates(1, 2);

    public static final Dimension dimensionSample = new Dimension(2, 2);

}
