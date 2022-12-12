package com.carbon.mowers;

import com.carbon.mowers.lawns.MowItNowLawnController;
import com.carbon.mowers.lawns.models.Instruction;
import com.carbon.mowers.lawns.models.Lawn;
import com.carbon.mowers.lawns.models.Mower;
import com.carbon.mowers.lawns.models.position.Coordinates;
import com.carbon.mowers.lawns.models.position.Dimension;
import com.carbon.mowers.lawns.models.position.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.carbon.mowers.LawnTestSample.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Lawn Controller Test should")
public class MowItNowLawnControllerTest {

    private MowItNowLawnController controller;

    @BeforeEach
    void initController() {
        this.controller = new MowItNowLawnController();
    }

    @ParameterizedTest
    @DisplayName("execute Mower orientation change")
    @MethodSource
    void moveMowerAccordingToTheInstruction(Mower mower, Orientation orientationExpected) {
        List<Mower> expectedMowers = List.of(new Mower(
                mower.coordinates(),
                orientationExpected,
                mower.instructions()
        ));
        Lawn expected = new Lawn(
                new Dimension(3, 3),
                expectedMowers
        );
        Lawn actual = controller.mow(new Lawn(
                new Dimension(3, 3),
                List.of(mower)
        ));
        assertEquals(expected, actual);
    }

    static Stream<Arguments> moveMowerAccordingToTheInstruction() {
        final var mowerRightNorth = new Mower(coordinatesSample, Orientation.NORTH, List.of(Instruction.TURN_RIGHT));
        final var mowerRightEast = new Mower(coordinatesSample, Orientation.EAST, List.of(Instruction.TURN_RIGHT));
        final var mowerRightSouth = new Mower(coordinatesSample, Orientation.SOUTH, List.of(Instruction.TURN_RIGHT));
        final var mowerRightWest = new Mower(coordinatesSample, Orientation.WEST, List.of(Instruction.TURN_RIGHT));

        final var mowerLeftNorth = new Mower(coordinatesSample, Orientation.NORTH, List.of(Instruction.TURN_LEFT));
        final var mowerLeftEast = new Mower(coordinatesSample, Orientation.EAST, List.of(Instruction.TURN_LEFT));
        final var mowerLeftSouth = new Mower(coordinatesSample, Orientation.SOUTH, List.of(Instruction.TURN_LEFT));
        final var mowerLeftWest = new Mower(coordinatesSample, Orientation.WEST, List.of(Instruction.TURN_LEFT));

        return Stream.of(
                arguments(mowerRightNorth, Orientation.EAST),
                arguments(mowerRightEast, Orientation.SOUTH),
                arguments(mowerRightSouth, Orientation.WEST),
                arguments(mowerRightWest, Orientation.NORTH),
                arguments(mowerLeftNorth, Orientation.WEST),
                arguments(mowerLeftEast, Orientation.NORTH),
                arguments(mowerLeftSouth, Orientation.EAST),
                arguments(mowerLeftWest, Orientation.SOUTH)
        );
    }

    @ParameterizedTest
    @DisplayName("execute Mower coordinates change")
    @MethodSource
    void moveMowerCoordinatesAccordingToTheInstruction(Mower mower, Coordinates coordinatesExpected) {
        List<Mower> expectedMowers = List.of(new Mower(
                coordinatesExpected,
                mower.orientation(),
                mower.instructions()
        ));
        Lawn expected = new Lawn(
                new Dimension(3, 3),
                expectedMowers
        );
        Lawn actual = controller.mow(new Lawn(
                new Dimension(3, 3),
                List.of(mower)
        ));
        assertEquals(expected, actual);
    }

    static Stream<Arguments> moveMowerCoordinatesAccordingToTheInstruction() {
        final var mowerForwardNorth = new Mower(coordinatesSample, Orientation.NORTH, List.of(Instruction.FORWARD));
        final var mowerForwardEast = new Mower(coordinatesSample, Orientation.EAST, List.of(Instruction.FORWARD));
        final var mowerForwardSouth = new Mower(coordinatesSample, Orientation.SOUTH, List.of(Instruction.FORWARD));
        final var mowerForwardWest = new Mower(coordinatesSample, Orientation.WEST, List.of(Instruction.FORWARD));

        return Stream.of(
                arguments(mowerForwardNorth, new Coordinates(1, 3)),
                arguments(mowerForwardEast, new Coordinates(2, 2)),
                arguments(mowerForwardSouth, new Coordinates(1, 1)),
                arguments(mowerForwardWest, new Coordinates(0, 2))
        );
    }

    @Test
    @DisplayName("move all mowers")
    void moveAllMowers() {
        List<Mower> expectedMowers = List.of(new Mower(
                        new Coordinates(1, 1),
                        Orientation.NORTH,
                        List.of(Instruction.FORWARD)),
                new Mower(
                        new Coordinates(2, 1),
                        Orientation.WEST,
                        List.of(Instruction.TURN_RIGHT)));
        Lawn expected = new Lawn(dimensionSample,
                expectedMowers
        );
        List<Mower> mowersTested = List.of(new Mower(
                        new Coordinates(1, 0),
                        Orientation.NORTH,
                        List.of(Instruction.FORWARD)),
                new Mower(
                        new Coordinates(2, 1),
                        Orientation.SOUTH,
                        List.of(Instruction.TURN_RIGHT))
        );
        Lawn actual = controller.mow(
                new Lawn(dimensionSample,
                        mowersTested
                ));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("execute all mower's instructions")
    void executeAllMowerInstructions() {
        List<Mower> expectedMowers = List.of(new Mower(
                new Coordinates(1, 3),
                Orientation.NORTH,
                instructionsSample)
        );
        Lawn expected = new Lawn(new Dimension(5, 5),
                expectedMowers
        );
        Lawn actual = controller.mow(
                new Lawn(new Dimension(5, 5),
                        List.of(new Mower(
                                new Coordinates(1, 2),
                                Orientation.NORTH,
                                instructionsSample)
                        )
                ));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("do nothing when Mower going outside the lawn")
    @MethodSource
    void doNotMoveMowersWhenTheyAreGoingOustideTheLawn(Mower mower) {
        List<Mower> expectedMowers = List.of(new Mower(
                mower.coordinates(),
                mower.orientation(),
                mower.instructions()
        ));
        Lawn expected = new Lawn(
                new Dimension(3, 3),
                expectedMowers
        );
        Lawn actual = controller.mow(new Lawn(
                new Dimension(3, 3),
                List.of(mower)
        ));
        assertEquals(expected, actual);
    }

    static Stream<Arguments> doNotMoveMowersWhenTheyAreGoingOustideTheLawn() {
        final var mowerNorth = new Mower(new Coordinates(2, 3), Orientation.NORTH, List.of(Instruction.FORWARD));
        final var mowerEast = new Mower(new Coordinates(3, 2), Orientation.EAST, List.of(Instruction.FORWARD));
        final var mowerSouth = new Mower(new Coordinates(2, 0), Orientation.SOUTH, List.of(Instruction.FORWARD));
        final var mowerWest = new Mower(new Coordinates(0, 2), Orientation.WEST, List.of(Instruction.FORWARD));

        return Stream.of(
                arguments(mowerNorth),
                arguments(mowerEast),
                arguments(mowerSouth),
                arguments(mowerWest)
        );
    }

}
