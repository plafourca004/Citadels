package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class ExchangeCardsWithOtherPlayersAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        Player playerToSwapWith = joueur.player().controller.selectPlayerAmong(groupe.associations.map(Group::player).remove(joueur.player()));
        joueur.player().exchangeHandWith(playerToSwapWith);
    }

    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        possibleActions = possibleActions.append(ActionType.EXCHANGE_CARDS_WITH_OTHER_PLAYERS);
    }

}
