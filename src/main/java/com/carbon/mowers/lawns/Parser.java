package com.carbon.mowers.lawns;

import com.carbon.mowers.exceptions.LawnInitializationErrorException;
import com.carbon.mowers.models.Lawn;

import java.util.List;

public interface Parser {
    Lawn createLawnFromLines(List<String> lines) throws LawnInitializationErrorException;
}
