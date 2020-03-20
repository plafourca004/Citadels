package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public class ExchangeCardsWithPileAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        Set<Card> cardsToSwap = joueur.player().controller.selectManyAmong(joueur.player().cards());
        joueur.player().cards = joueur.player().cards().removeAll(cardsToSwap);
        joueur.player().add(pioche.swapWith(cardsToSwap.toList()));
    }

    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        if (!joueurCourant.player().cards().isEmpty() && pioche.canDraw(1))
            possibleActions.append(ActionType.EXCHANGE_CARDS_WITH_PILE);
        System.out.println("je suis dans EXCHANGE_CARDS_WITH_PILE ");
    }
}
