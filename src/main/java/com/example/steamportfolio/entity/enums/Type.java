package com.example.steamportfolio.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Type {
    PISTOL("Pistol"),
    SMG("SMG"),
    SNIPER_RIFLE("Sniper Rifle"),
    RIFLE("Rifle"),
    SHOTGUN("Shotgun"),
    MACHINEGUN("Machinegun"),
    CONTAINER("Container"),
    AGENT("Agent"),
    KNIFE("Knife"),
    STICKER("Sticker"),
    GLOVES("Gloves"),
    GRAFFITI("Graffiti"),
    MUSIC_KIT("Music Kit"),
    PATCH("Patch"),
    COLLECTIBLE("Collectible"),
    KEY("Key"),
    PASS("Pass"),
    GIFT("Gift"),
    TAG("Tag"),
    TOOL("Tool");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
