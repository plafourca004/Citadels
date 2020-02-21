package com.montaury.citadels.character;

import com.montaury.citadels.Action;
import com.montaury.citadels.district.DistrictType;
import io.vavr.control.Option;

import io.vavr.collection.List;

public enum Character {
    ASSASSIN(1, "Assassin",null, List.of(Action.KILL)),
    THIEF(2, "Thief", null, List.of(Action.ROB)),
    MAGICIAN(3, "Magician", null, List.of(Action.EXCHANGE_CARDS_WITH_OTHER_PLAYERS,Action.EXCHANGE_CARDS_WITH_PILE)),
    KING(4, "King", DistrictType.NOBLE,List.of(Action.RECEIVE_INCOME)),
    BISHOP(5, "Bishop", DistrictType.RELIGIOUS,List.of(Action.RECEIVE_INCOME)),
    MERCHANT(6, "Merchant", DistrictType.TRADE,List.of(Action.RECEIVE_INCOME, Action.RECEIVE_1_COIN)),
    ARCHITECT(7, "Architect", null, List.of(Action.PICK_2_CARDS, Action.BUILD_DISTRICT, Action.BUILD_DISTRICT)),
    WARLORD(8, "Warlord", DistrictType.MILITARY, List.of(Action.RECEIVE_INCOME,Action.DESTROY_DISTRICT));

    Character(int number, String name, DistrictType associatedDistrictType, List<Action> powers)
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

    public List<Action> getPowers (){return powers;}

    public Option<DistrictType> associatedDistrictType() {
        return associatedDistrictType;
    }

    private final int number;
    private final String name;
    private final Option<DistrictType> associatedDistrictType;
    private final List<Action> powers;
}
