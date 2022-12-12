package com.carbon.mowers.lawns;

import com.carbon.mowers.exceptions.LawnInitializationErrorException;
import com.carbon.mowers.lawns.models.Instruction;
import com.carbon.mowers.lawns.models.Lawn;
import com.carbon.mowers.lawns.models.Mower;
import com.carbon.mowers.lawns.models.position.Coordinates;
import com.carbon.mowers.lawns.models.position.Dimension;
import com.carbon.mowers.lawns.models.position.Orientation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LawnParser implements Parser {
    private final String DELIMITER = " ";
    private final String INSTRUCTIONS_DELIMITER = "";

    @Override
    public Lawn createLawnFromLines(List<String> lines) throws LawnInitializationErrorException {
        try {
            String[] lawnCommand = lines.get(0).split(DELIMITER);
            List<Mower> mowers = new ArrayList<>();
            if (lines.size() > 1) {
                mowers = createAllMowers(lines.subList(1, lines.size()));
            }
            return new Lawn(createDimension(lawnCommand), mowers);
        } catch (Exception e) {
            throw new LawnInitializationErrorException();
        }
    }

    private List<Mower> createAllMowers(List<String> data) throws LawnInitializationErrorException {
        List<Mower> mowers = new ArrayList<>();
        if (data.size() < 2) {
            return mowers;
        }
        for (int line = 0; line <= data.size() / 2; line += 2) {
            Mower mower = createMower(
                    data.get(line).split(DELIMITER),
                    data.get(line + 1).split(INSTRUCTIONS_DELIMITER));

            mowers.add(mower);
        }
        return mowers;
    }

    private Mower createMower(String[] information, String[] instructions) throws LawnInitializationErrorException {
        try {
            Coordinates mowerCoordinates = createCoordinates(information);
            Orientation mowerOrientation = createOrientation(information);
            return new Mower(mowerCoordinates, mowerOrientation, createInstructions(instructions));
        } catch (Exception e) {
            throw new LawnInitializationErrorException();
        }
    }

    private Coordinates createCoordinates(String[] information) {
        return new Coordinates(
                Integer.parseInt(information[0]),
                Integer.parseInt(information[1]));
    }

    private Orientation createOrientation(String[] information) throws LawnInitializationErrorException {
        return mapToOrientation(information[2]);
    }

    private Orientation mapToOrientation(String value) throws LawnInitializationErrorException {
        return switch (value) {
            case "N" -> Orientation.NORTH;
            case "S" -> Orientation.SOUTH;
            case "W" -> Orientation.WEST;
            case "E" -> Orientation.EAST;
            default -> throw new LawnInitializationErrorException();
        };
    }

    private List<Instruction> createInstructions(String[] instruction) throws LawnInitializationErrorException {
        if (instruction.length > 0) {
            List<Instruction> instructions = new ArrayList<>();
            for (String anInstruction : instruction) {
                instructions.add(mapToInstruction(anInstruction));
            }
            return instructions;
        }
        return Collections.emptyList();
    }

    private Instruction mapToInstruction(String value) throws LawnInitializationErrorException {
        return switch (value) {
            case "D" -> Instruction.TURN_RIGHT;
            case "G" -> Instruction.TURN_LEFT;
            case "A" -> Instruction.FORWARD;
            default -> throw new LawnInitializationErrorException();
        };
    }

    private Dimension createDimension(String[] information) {
        return new Dimension(
                Integer.parseInt(information[0]),
                Integer.parseInt(information[1]));
    }


}
