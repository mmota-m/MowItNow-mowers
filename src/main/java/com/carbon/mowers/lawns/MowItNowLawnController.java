package com.carbon.mowers.lawns;

import com.carbon.mowers.lawns.models.Instruction;
import com.carbon.mowers.lawns.models.Lawn;
import com.carbon.mowers.lawns.models.Mower;
import com.carbon.mowers.lawns.models.position.Coordinates;
import com.carbon.mowers.lawns.models.position.Orientation;

import java.util.List;

public class MowItNowLawnController implements LawnController {

    @Override
    public Lawn mow(Lawn lawn) {
        List<Mower> mowers = lawn.mowers().stream().map(mower -> executeAllMowersMovements(lawn, mower)).toList();
        return new Lawn(lawn.dimension(), mowers);
    }

    private Mower executeAllMowersMovements(Lawn lawn, Mower mower) {
        Mower mowerState = mower;
        for (Instruction instruction : mowerState.instructions()) {
            mowerState = switch (instruction) {
                case TURN_RIGHT -> goRight(mowerState);
                case TURN_LEFT -> goLeft(mowerState);
                case FORWARD -> goForward(lawn, mowerState);
            };
        }
        return mowerState;
    }

    private Mower goRight(Mower mower) {
        return switch (mower.orientation()) {
            case NORTH -> mower.withOrientation(Orientation.EAST);
            case EAST -> mower.withOrientation(Orientation.SOUTH);
            case SOUTH -> mower.withOrientation(Orientation.WEST);
            case WEST -> mower.withOrientation(Orientation.NORTH);
        };
    }

    private Mower goLeft(Mower mower) {
        return switch (mower.orientation()) {
            case NORTH -> mower.withOrientation(Orientation.WEST);
            case EAST -> mower.withOrientation(Orientation.NORTH);
            case SOUTH -> mower.withOrientation(Orientation.EAST);
            case WEST -> mower.withOrientation(Orientation.SOUTH);
        };
    }

    private Mower goForward(Lawn lawn, Mower mower) {
        Coordinates startingPoint = mower.coordinates();
        Mower mowerState = switch (mower.orientation()) {
            case NORTH -> mower.withCoordinates(new Coordinates(startingPoint.x(), startingPoint.y() + 1));
            case SOUTH -> mower.withCoordinates(new Coordinates(startingPoint.x(), startingPoint.y() - 1));
            case EAST -> mower.withCoordinates(new Coordinates(startingPoint.x() + 1, startingPoint.y()));
            case WEST -> mower.withCoordinates(new Coordinates(startingPoint.x() - 1, startingPoint.y()));
        };
        return lawn.isInsideLawn(mowerState.coordinates()) ? mowerState : mower;
    }
}
