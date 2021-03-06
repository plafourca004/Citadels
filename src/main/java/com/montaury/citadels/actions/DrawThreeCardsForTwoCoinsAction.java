package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class DrawThreeCardsForTwoCoinsAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        joueur.player().add(pioche.draw(3));
        joueur.player().pay(2);
    }
    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        possibleActions.append(ActionType.DRAW_3_CARDS_FOR_2_COINS);
        System.out.println("je suis dans DRAW_3_CARDS_FOR_2_COINS");
    }

}
