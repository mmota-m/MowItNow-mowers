package com.carbon.mowers;

import com.carbon.mowers.exceptions.LawnInitializationErrorException;
import com.carbon.mowers.models.Dimension;
import com.carbon.mowers.models.Lawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

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

}
