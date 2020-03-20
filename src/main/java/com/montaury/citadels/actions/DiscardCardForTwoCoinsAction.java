package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.DestroyDistrict;
import io.vavr.collection.List;

public class DiscardCardForTwoCoinsAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        Player player = joueur.player();
        Card card = player.controller.selectAmong(player.cards());
        player.cards = player.cards().remove(card);
        pioche.discard(card);
        player.add(2);
    }

    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        if (!joueurCourant.player().cards().isEmpty())
            possibleActions = possibleActions.append(ActionType.DISCARD_CARD_FOR_2_COINS);
    }

}
