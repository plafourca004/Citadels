package com.montaury.citadels.actions;


import com.montaury.citadels.character.Character;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class KillAction extends Action {
    @Override
    void execute(Group joueur, GameRoundAssociations groupe) {
        Character characterToMurder = joueur.player().controller.selectAmong(List.of(Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD));
        groupe.associationToCharacter(characterToMurder).peek(Group::murder);
    }
}
