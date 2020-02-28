package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.Set;

public class ExchangeCardsWithPileAction extends Action {
    void execute(Group joueur, GameRoundAssociations groupe, CardPile pioche)
    {
        Set<Card> cardsToSwap = joueur.player().controller.selectManyAmong(joueur.player().cards());
        joueur.player().cards = joueur.player().cards().removeAll(cardsToSwap);
        joueur.player().add(pioche.swapWith(cardsToSwap.toList()));
    }
}
