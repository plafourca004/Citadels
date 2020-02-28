package com.montaury.citadels.actions;

public enum ActionType {
    RECEIVE_2_COINS("Receive 2 coins"),
    RECEIVE_1_COIN("Receive 1 gold"),
    RECEIVE_INCOME("Receive income"),
    KILL("Kill"),
    ROB("Rob"),
    EXCHANGE_CARDS_WITH_OTHER_PLAYERS("Exchange cards with other player"),
    EXCHANGE_CARDS_WITH_PILE("Exchange cards with pile"),
    PICK_2_CARDS("Pick 2 cards"),
    BUILD_DISTRICT("Build district"),
    DESTROY_DISTRICT("Destroy district"),
    DRAW_2_CARDS_KEEP_1("Draw 2 cards and keep 1"),
    DISCARD_CARD_FOR_2_COINS("Discard card for 2 coins"),
    DRAW_3_CARDS_FOR_2_COINS("Draw 3 cards for 2 coins"),
    END_ROUND ("End round"),
    DRAW_3_CARDS_KEEP_1("Draw 3 cards and keep 1");

    public String getDescription()
    {
        return this.descriptionAction;
    }

    ActionType(String descriptionAction)
    {
        this.descriptionAction = descriptionAction;
    }

    private final String descriptionAction;

}
