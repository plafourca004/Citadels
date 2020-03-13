package com.montaury.citadels.round.action;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;

public class DestroyDistrict {

    public static Map<Player, List<DestructibleDistrict>> districtsDestructibleBy(GameRoundAssociations associations, Player player) {
        Map<Player, List<DestructibleDistrict>> destructibles = HashMap.empty();
        for (Group group : associations.associations) {
            if (group.isNot(Character.BISHOP) || group.isMurdered()) {
                destructibles = destructibles.put(group.player(), group.player().city().districtsDestructibleBy(player));
            }
        }
        return destructibles;
    }
}
