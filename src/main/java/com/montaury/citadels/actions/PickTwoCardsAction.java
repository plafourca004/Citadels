package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.Group;

public class PickTwoCardsAction extends Action {
    void execute(Group joueur, CardPile pioche)
    {
        joueur.player().add(pioche.draw(2));

    }
}
