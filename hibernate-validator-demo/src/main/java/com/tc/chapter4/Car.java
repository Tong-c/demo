package com.tc.chapter4;

import com.tc.chapter2.withIterable.ValidPart;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Car {

    @NotNull(message = "The manufacturer name must not be null")
    private String manufacturer;
}
