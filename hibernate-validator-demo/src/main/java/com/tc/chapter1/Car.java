package com.tc.chapter1;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class Car {

    @NotNull/*(message = "{custom.NotNull.message}")*/
    private String manufacturer;

    private String licensePlate;

    private int seatCount;

    public Car(String manufacturer, String licensePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
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

    public static void main(String[] args) {
        List<@NotNull Car> carList = new ArrayList<Car>();
    }
}
