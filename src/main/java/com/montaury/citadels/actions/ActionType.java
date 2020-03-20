package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.DestroyDistrict;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public enum ActionType {
    RECEIVE_2_COINS("Receive 2 coins",new ReceiveTwoCoinsAction()),
    RECEIVE_1_COIN("Receive 1 gold", new ReceiveOneCoinAction()),
    RECEIVE_INCOME("Receive income", new ReceiveIncomeAction()),
    KILL("Kill", new KillAction()),
    ROB("Rob", new RobAction()),
    EXCHANGE_CARDS_WITH_OTHER_PLAYERS("Exchange cards with other player", new ExchangeCardsWithOtherPlayersAction()),
    EXCHANGE_CARDS_WITH_PILE("Exchange cards with pile", new ExchangeCardsWithPileAction()),
    PICK_2_CARDS("Pick 2 cards", new PickTwoCardsAction()),
    BUILD_DISTRICT("Build district", new BuildDistrictAction()),
    DESTROY_DISTRICT("Destroy district", new DestroyDistrictAction()),
    DRAW_2_CARDS_KEEP_1("Draw 2 cards and keep 1", new DrawTwoCardsKeepOneAction()),
    DISCARD_CARD_FOR_2_COINS("Discard card for 2 coins", new DiscardCardForTwoCoinsAction()),
    DRAW_3_CARDS_FOR_2_COINS("Draw 3 cards for 2 coins", new DrawThreeCardsForTwoCoinsAction()),
    END_ROUND ("End round", new EndRoundAction()),
    DRAW_3_CARDS_KEEP_1("Draw 3 cards and keep 1", new DrawThreeCardsKeepOneAction());

    public String getDescription()
    {
        return this.descriptionAction;
    }

    ActionType(String descriptionAction, Action uneAction)
    {
        this.descriptionAction = descriptionAction;
        this.uneAction = uneAction;
    }

    public void execute (Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        uneAction.execute(joueur, listeCouplesJoueursPersos, groupe, pioche);
    }

    public void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
            uneAction.canExecute(joueurCourant, possibleActions, groupeCoupleJoueurPerso, pioche);
    }


    private final Action uneAction;
    private final String descriptionAction;

}
