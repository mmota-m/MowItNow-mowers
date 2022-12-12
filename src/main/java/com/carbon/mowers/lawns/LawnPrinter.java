package com.carbon.mowers.lawns;


import com.carbon.mowers.lawns.models.Lawn;

public class LawnPrinter implements Printer {

    private final LawnFormatter formatter;

    public LawnPrinter(LawnFormatter formatter) {
        this.formatter = formatter;
    }

    public void printLawnInformation(Lawn lawn) {
        String formattedLawnInformation = formatter.format(lawn);
        System.out.print(formattedLawnInformation);
    }
}
