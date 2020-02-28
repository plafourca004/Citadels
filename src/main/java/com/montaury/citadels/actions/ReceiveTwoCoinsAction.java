package com.montaury.citadels.actions;

import com.montaury.citadels.round.Group;

public class ReceiveTwoCoinsAction extends Action {
    @Override
    void execute(Group joueur) {
        joueur.player().add(2);
    }
}