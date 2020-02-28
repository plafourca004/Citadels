package com.montaury.citadels.actions;

import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.Character;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;


public class DestroyDistrictAction extends Action {

    void execute(Group joueur, List<Group> listeCouplesJoueursPersos)
    {
        Map<Player, List<DestructibleDistrict>> districtDestructibleParPlayer = HashMap.empty();
        for(Group playerCourant : listeCouplesJoueursPersos){
            if(playerCourant.character != Character.WARLORD || (playerCourant.isMurdered() == false && playerCourant.character != Character.BISHOP)){
                districtDestructibleParPlayer.put(playerCourant.player(), playerCourant.player().city().districtsDestructibleBy(joueur.player()));
            }
        }
        joueur.player().controller.selectDistrictToDestroyAmong(districtDestructibleParPlayer);

    }

}