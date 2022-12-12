package com.carbon.mowers;

import com.carbon.mowers.lawns.LawnFormatter;
import com.carbon.mowers.lawns.LawnPrinter;
import com.carbon.mowers.lawns.models.Lawn;
import com.carbon.mowers.lawns.models.position.Dimension;
import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SystemOutGuard.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Operation Printer Test Should")
public class LawnPrinterTest {

    @Mock
    private LawnFormatter formatter;

    @InjectMocks
    private LawnPrinter printer;

    @Test
    @DisplayName("display text")
    void printText(final Capturable stream) {
        final String expected = "This text is expected.";
        when(formatter.format(any())).thenReturn(expected);

        stream.capture();
        printer.printLawnInformation(new Lawn(
                new Dimension(3, 3), List.of()
        ));

        verify(formatter).format(new Lawn(
                new Dimension(3, 3), List.of()
        ));
        verifyNoMoreInteractions(formatter);
        assertEquals(expected, stream.getCapturedData());
    }
}
