package com.carbon.mowers;

import com.carbon.mowers.lawns.LawnFormatter;
import com.carbon.mowers.lawns.models.Lawn;
import com.carbon.mowers.lawns.models.Mower;
import com.carbon.mowers.lawns.models.position.Coordinates;
import com.carbon.mowers.lawns.models.position.Dimension;
import com.carbon.mowers.lawns.models.position.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.carbon.mowers.LawnTestSample.instructionsSample;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Lawn Formatter Test should")
public class LawnFormatterTest {
    private LawnFormatter formatter;

    @BeforeEach
    void initFormatter() {
        this.formatter = new LawnFormatter();
    }

    @Test
    @DisplayName("return formatted String for Lawn formatting")
    void returnFormattedMowers() {
        List<Mower> mowersTested = List.of(
                new Mower(
                        new Coordinates(1, 2),
                        Orientation.EAST,
                        instructionsSample),
                new Mower(
                        new Coordinates(2, 3),
                        Orientation.NORTH,
                        instructionsSample)
        );
        String actual = formatter.format(new Lawn(
                new Dimension(4, 4),
                mowersTested
        ));
        assertEquals("1 2 E" + System.lineSeparator() + "2 3 N", actual);
    }

    @Test
    @DisplayName("return empty when list is empty")
    void returnEmptyGivenEmptyList() {
        String actual = formatter.format(new Lawn(
                new Dimension(3, 3),
                List.of()
        ));
        assertEquals("", actual);
    }
}
