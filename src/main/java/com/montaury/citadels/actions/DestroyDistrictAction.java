package com.montaury.citadels.actions;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.round.action.DestroyDistrict;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;



public class DestroyDistrictAction extends Action {
    @Override
    void execute(Group joueur, List<Group> listeCouplesJoueursPersos, GameRoundAssociations groupe, CardPile pioche)
    {
        Map<Player, List<DestructibleDistrict>> districtDestructibleParPlayer = HashMap.empty();
        for(Group playerCourant : listeCouplesJoueursPersos){
            if(playerCourant.character != Character.WARLORD || (playerCourant.isMurdered() == false && playerCourant.character != Character.BISHOP)){
                districtDestructibleParPlayer.put(playerCourant.player(), playerCourant.player().city().districtsDestructibleBy(joueur.player()));
            }
        }
        //joueur.player().controller.selectDistrictToDestroyAmong(districtDestructibleParPlayer).card();

        Card card =  joueur.player().controller.selectDistrictToDestroyAmong(districtDestructibleParPlayer).card();




        joueur.player().city().destroyDistrict( joueur.player().controller.selectDistrictToDestroyAmong(districtDestructibleParPlayer).card());



        joueur.player().pay(  );


    }

    void canExecute(Group joueurCourant, List<ActionType> possibleActions, GameRoundAssociations groupeCoupleJoueurPerso, CardPile pioche)
    {
        if (DestroyDistrict.districtsDestructibleBy(groupeCoupleJoueurPerso, joueurCourant.player()).exists(districtsByPlayer -> !districtsByPlayer._2().isEmpty()))
             possibleActions.append(ActionType.DESTROY_DISTRICT);
        System.out.println("je suis dans DESTROY_DISTRICT");
    }






}