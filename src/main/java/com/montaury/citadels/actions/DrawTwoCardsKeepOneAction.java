package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class DrawTwoCardsKeepOneAction extends Action {



    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {

    }
    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        possibleActions.append(ActionType.DRAW_2_CARDS_KEEP_1);
        System.out.println("je suis dans DRAW_2_CARDS_KEEP_1");
    }


}
