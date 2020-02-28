package com.montaury.citadels.actions;

import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public class ExchangeCardsWithOtherPlayersAction extends Action {

    void execute(Group joueur,  GameRoundAssociations groupe)
    {
        Player playerToSwapWith = joueur.player().controller.selectPlayerAmong(groupe.associations.map(Group::player).remove(joueur.player()));
        joueur.player().exchangeHandWith(playerToSwapWith);
    }

}
