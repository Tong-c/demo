package com.tc.chapter2.withIterable;

import java.util.HashSet;
import java.util.Set;

public class Car {

    private Set<@ValidPart String> parts = new HashSet<>();

    public void addPart(String part) {
        parts.add(part);
    }
}
