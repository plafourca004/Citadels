package com.montaury.citadels.actions;

import com.montaury.citadels.round.Group;

public class ReceiveOneCoinAction extends Action {
    @Override
    void execute(Group joueur) {
        joueur.player().add(1);
    }
}