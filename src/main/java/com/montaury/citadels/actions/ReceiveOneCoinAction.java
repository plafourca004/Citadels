package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class ReceiveOneCoinAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche) {
        joueur.player().add(1);
    }
    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        possibleActions.append(ActionType.RECEIVE_1_COIN);
        System.out.println("je suis dans RECEIVE_1_COIN");
    }
}