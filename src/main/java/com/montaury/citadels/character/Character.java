package com.montaury.citadels.character;

import com.montaury.citadels.actions.ActionType;
import com.montaury.citadels.district.DistrictType;
import io.vavr.control.Option;

import io.vavr.collection.List;

public enum Character {
    ASSASSIN(1, "Assassin",null, List.of(ActionType.KILL)),
    THIEF(2, "Thief", null, List.of(ActionType.ROB)),
    MAGICIAN(3, "Magician", null, List.of(ActionType.EXCHANGE_CARDS_WITH_OTHER_PLAYERS, ActionType.EXCHANGE_CARDS_WITH_PILE)),
    KING(4, "King", DistrictType.NOBLE,List.of(ActionType.RECEIVE_INCOME)),
    BISHOP(5, "Bishop", DistrictType.RELIGIOUS,List.of(ActionType.RECEIVE_INCOME)),
    MERCHANT(6, "Merchant", DistrictType.TRADE,List.of(ActionType.RECEIVE_INCOME, ActionType.RECEIVE_1_COIN)),
    ARCHITECT(7, "Architect", null, List.of(ActionType.PICK_2_CARDS, ActionType.BUILD_DISTRICT, ActionType.BUILD_DISTRICT)),
    WARLORD(8, "Warlord", DistrictType.MILITARY, List.of(ActionType.RECEIVE_INCOME, ActionType.DESTROY_DISTRICT));

    Character(int number, String name, DistrictType associatedDistrictType, List<ActionType> powers)
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

    public List<ActionType> getPowers (){return powers;}

    public Option<DistrictType> associatedDistrictType() {
        return associatedDistrictType;
    }

    private final int number;
    private final String name;
    private final Option<DistrictType> associatedDistrictType;
    private final List<ActionType> powers;
}
