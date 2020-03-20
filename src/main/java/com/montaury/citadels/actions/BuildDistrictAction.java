package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class BuildDistrictAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        Card card = joueur.player().controller.selectAmong(joueur.player().buildableDistrictsInHand());
        joueur.player().buildDistrict(card);
    }

    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        if (!joueurCourant.player().buildableDistrictsInHand().isEmpty() )
        {
            possibleActions.append(ActionType.BUILD_DISTRICT);
            System.out.println("je suis dans BUILD_DISTRICT");
        }
    }
}

