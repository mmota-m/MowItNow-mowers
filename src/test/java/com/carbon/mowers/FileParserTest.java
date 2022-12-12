package com.carbon.mowers;

import com.carbon.mowers.exceptions.LawnInitializationErrorException;
import com.carbon.mowers.lawns.LawnParser;
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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static com.carbon.mowers.LawnTestSample.instructionsSample;
import static com.carbon.mowers.LawnTestSample.mowerCreationSample;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("File Parser Test should")
public class FileParserTest {

    private LawnParser parser;

    @BeforeEach
    void initParser() {
        this.parser = new LawnParser();
    }


    @ParameterizedTest
    @DisplayName("not throw error and return new Lawn given coordinates")
    @CsvSource({"0 0,0,0", "2 6,2,6", "10 30,10,30"})
    void returnNewLawn(String lawnCoordinates, int width, int length) {
        Lawn expected = new Lawn(new Dimension(width, length), new ArrayList<>());
        Lawn actual = assertDoesNotThrow(() -> parser.createLawnFromLines(List.of(lawnCoordinates)));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("throw LawnErrorCoordinatesException")
    @CsvSource({"10.5 11", "a $", "test Error"})
    void throwExceptionWhenCreatingLawn(String lawnCoordinates) {
        assertThrows(LawnInitializationErrorException.class, () -> {
            parser.createLawnFromLines(List.of(lawnCoordinates.split("")));
        });
    }

    @Test
    @DisplayName("return a new mower without error")
    void returnLawnWithMowerWithoutError() {
        List<Mower> expectedMowers = List.of(new Mower
                (new Coordinates(0, 0),
                        Orientation.NORTH,
                        instructionsSample));
        Lawn expected = new Lawn(new Dimension(3, 3),
                expectedMowers);

        Lawn actual = assertDoesNotThrow(() -> parser.createLawnFromLines(List.of("3 3", "0 0 N", "GAGAGAGAA")));

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"3 3\n0 0 D\nGAGAGAGAA", "3 3\na 0 S\nGAGAGAGAA", "3 3\n34 27.5 S\nGAGAGAGAA", "3 3\n21 2 %\nGAGAGAGAA"})
    @DisplayName("throw MowerInitialisationErrorException when creating a mover with wrong information format")
    void throwExceptionWhenCreatingMowerWithWrongInformation(String LawnInformation) {
        assertThrows(LawnInitializationErrorException.class,
                () -> parser.createLawnFromLines(List.of(LawnInformation.split("\n"))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3 3\n0 0 D\nFF", "3 3\n0 0 D\n%(S", "3 3\n0 0 D\n0 0 S"})
    @DisplayName("throw MowerInitialisationErrorException when creating a mover with wrong instructions")
    void throwExceptionWhenCreatingWithWrongInstructions(String lawnInformation) {
        assertThrows(LawnInitializationErrorException.class,
                () -> parser.createLawnFromLines(List.of(lawnInformation)));
    }

    @Test
    @DisplayName("return list of mowers in Lawn")
    void returnListOfMowers() {
        List<Mower> expectedMowers = List.of(mowerCreationSample,
                new Mower(
                        new Coordinates(1, 2),
                        Orientation.SOUTH,
                        List.of(Instruction.TURN_RIGHT, Instruction.TURN_LEFT, Instruction.FORWARD)
                ));
        Lawn expected = new Lawn(
                new Dimension(3, 3),
                expectedMowers);
        List<String> data = List.of("3 3", "1 2 N", "GAGAGAGAA", "1 2 S", "DGA");
        Lawn actual = assertDoesNotThrow(() -> parser.createLawnFromLines(data));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("return empty List of Mowers in Lawn")
    void returnEmptyList() {
        Lawn expected = new Lawn(
                new Dimension(3, 3),
                List.of());
        List<String> data = List.of("3 3", "");
        Lawn actual = assertDoesNotThrow(() -> parser.createLawnFromLines(data));
        assertEquals(expected, actual);
    }

}