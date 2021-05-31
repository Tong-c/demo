package com.tc.chapter4.resourceBundleLocator;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public class Car {

    @NotNull
    private String licensePlate;

    @Max(300)
    private int topSpeed = 400;
}
