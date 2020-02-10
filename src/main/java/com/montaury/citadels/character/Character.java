package com.montaury.citadels.character;

import com.montaury.citadels.district.DistrictType;
import io.vavr.control.Option;

import io.vavr.collection.List;

public enum Character {
    ASSASSIN(1, "Assassin",null, List.of("Kill")),
    THIEF(2, "Thief", null, List.of("Rob")),
    MAGICIAN(3, "Magician", null, List.of("Exchange cards with other player","Exchange cards with pile")),
    KING(4, "King", DistrictType.NOBLE,List.of("Receive income")),
    BISHOP(5, "Bishop", DistrictType.RELIGIOUS,List.of("Receive income")),
    MERCHANT(6, "Merchant", DistrictType.TRADE,List.of("Receive income", "Receive 1 gold")),
    ARCHITECT(7, "Architect", null, List.of("Pick 2 cards","Build district", "Build district")),
    WARLORD(8, "Warlord", DistrictType.MILITARY, List.of("Receive income","Destroy district"));

    Character(int number, String name, DistrictType associatedDistrictType, List<String> powers)
    {
        this.number = number;
        this.name = name;
        this.associatedDistrictType = Option.of(associatedDistrictType);
        this.powers = powers;
    }

    public int number() {
        return number;
    }

    public String getName() {
        return name;
    }

    public List<String> getPowers (){return powers;}

    public Option<DistrictType> associatedDistrictType() {
        return associatedDistrictType;
    }

    private final int number;
    private final String name;
    private final Option<DistrictType> associatedDistrictType;
    private final List<String> powers;
}
