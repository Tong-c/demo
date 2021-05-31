package com.tc.chapter4.complete;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class Car {

    @NotNull
    private String manufacturer;

    @Size(
            min = 2,
            max = 14,
            message = "The license plate '${validatedValue}' must be between {min} and {max} characters long")
    private String licensePlate;

    @Min(value = 2, message = "There must be at least {value} seat${value > 1 ? 's' : ''}")
    private int seatCount;

    @DecimalMax(value = "350", message = "The top speed ${formatter.format('%1$.2f', validatedValue)} is higher than {value}")
    private double topSpeed;


    @DecimalMax(value = "100000", message = "123")
    private BigDecimal price;


    public Car(String manufacturer, String licensePlate, int seatCount, double topSpeed, BigDecimal price) {
        this.manufacturer = manufacturer;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.topSpeed = topSpeed;
        this.price = price;
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
