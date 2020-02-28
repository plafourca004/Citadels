package com.montaury.citadels.actions;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.Group;

public class BuildDistrictAction extends Action {

    void execute(Group joueur)
    {
        Card card = joueur.player().controller.selectAmong(joueur.player().buildableDistrictsInHand());
        joueur.player().buildDistrict(card);
    }

}

