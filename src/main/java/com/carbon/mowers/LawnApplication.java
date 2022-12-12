package com.carbon.mowers;


import com.carbon.mowers.lawns.*;
import com.carbon.mowers.lawns.models.Lawn;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LawnApplication {
    public static void main(String[] args) {
        try {
            LawnPrinter printer = new LawnPrinter(new LawnFormatter());
            LawnParser parser = new LawnParser();
            Lawn lawn = parser.createLawnFromLines(Files.readAllLines(Paths.get("src/main/resources/input.txt"), StandardCharsets.UTF_8));
            LawnController controller = new MowItNowLawnController();
            lawn = controller.mow(lawn);
            printer.printLawnInformation(lawn);
        } catch (Exception e) {
            System.out.println("Erreur lors du lancement de l'application");
        }

    }
}
