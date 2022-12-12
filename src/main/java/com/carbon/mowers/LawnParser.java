package com.carbon.mowers;

import com.carbon.mowers.exceptions.LawnInitializationErrorException;
import com.carbon.mowers.models.Dimension;
import com.carbon.mowers.models.Lawn;
import com.carbon.mowers.models.Mower;

import java.util.ArrayList;
import java.util.List;

public class LawnParser implements Parser {

    private final String DELIMITER = " ";

    @Override
    public Lawn createLawnFromLines(List<String> lines) throws LawnInitializationErrorException {
        try {
            String[] lawnLine = lines.get(0).split(DELIMITER);
            List<Mower> mowers = new ArrayList<>();
            if (lines.size() > 1) {
                mowers = new ArrayList<>();
            }
            return new Lawn(createDimension(lawnLine), mowers);
        } catch (Exception e) {
            throw new LawnInitializationErrorException();
        }
    }

    private Dimension createDimension(String[] information) {
        return new Dimension(
                Integer.parseInt(information[0]),
                Integer.parseInt(information[1]));
    }


}
