package com.tc.chapter5.groupinheritance;

import com.tc.chapter5.Car;
import jakarta.validation.constraints.AssertTrue;

public class SuperCar extends Car {

    @AssertTrue(
            message = "Race car must have a safety belt",
            groups = RaceCarChecks.class
    )
    private boolean safetyBelt;

    // getters and setters ...

    //end::include[]

    public SuperCar(String manufacturer, String licencePlate, int seatCount) {
        super( manufacturer, licencePlate, seatCount );
    }

    public boolean isSafetyBelt() {
        return safetyBelt;
    }

    public void setSafetyBelt(boolean safetyBelt) {
        this.safetyBelt = safetyBelt;
    }

}
