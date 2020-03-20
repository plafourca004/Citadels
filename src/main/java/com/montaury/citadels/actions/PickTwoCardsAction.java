package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class PickTwoCardsAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        joueur.player().add(pioche.draw(2));

    }

    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        if (pioche.canDraw(2))
           possibleActions.append(ActionType.PICK_2_CARDS);
        System.out.println("je suis dans PICK_2_CARDS");
    }

}
