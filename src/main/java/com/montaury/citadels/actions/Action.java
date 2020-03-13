package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

abstract class Action {
    abstract void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche); //En parametre, mettre tous les parametres des classes enfants, et homogénéiser tous les parametres de chaques classes (ca doit etre tout pareil)

}
