package com.example.steamportfolio.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Quality {
    CONSUMER("Consumer Grade"),
    MIL_SPEC("Mil-Spec Grade"),
    RESTRICTED("Restricted"),
    INDUSTRIAL("Industrial Grade"),
    CLASSIFIED("Classified"),
    COVERT("Covert"),
    BASE("Base Grade"),
    EXCEPTIONAL("Exceptional"),
    SUPERIOR("Superior"),
    DISTINGUISHED("Distinguished"),
    EXTRAORDINARY("Extraordinary"),
    MASTER("Master"),
    HIGH("High Grade"),
    REMARKABLE("Remarkable"),
    EXOTIC("Exotic"),
    CONTRABAND("Contraband");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
