package com.example.steamportfolio.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    NORMAL("Normal"),
    STATTRACK("StatTrak™"),
    SOUVENIR("Souvenir"),
    KNIFE("★"),
    KNIFE_STATTRACK("★ StatTrak™");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
