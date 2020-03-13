package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.DestroyDistrict;
import io.vavr.collection.List;

public class DrawThreeCardsKeepOneAction extends Action {

    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        if (pioche.canDraw(3) && joueurCourant.player().canAfford(2))
            possibleActions = possibleActions.append(ActionType.DRAW_3_CARDS_FOR_2_COINS);
    }

    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {

    }

}
