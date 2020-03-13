package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class RobAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche){
        Character personnage = joueur.player().controller.selectAmong(List.of(Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD)
                .removeAll(groupe.associations.find(Group::isMurdered).map(Group::character)));
        groupe.associationToCharacter(personnage).peek(association -> association.stolenBy(joueur.player()));
    }
}
