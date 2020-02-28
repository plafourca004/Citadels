package com.montaury.citadels.player;

import com.montaury.citadels.actions.ActionType;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

import java.util.Random;

public class ComputerController implements PlayerController {
    private int random(int maxExcluded) {
        return random.nextInt(maxExcluded);
    }

    @Override
    public Character selectOwnCharacter(List<Character> availableCharacters, List<Character> faceUpRevealedCharacters) {
        return randomAmong(availableCharacters);
    }

    @Override
    public ActionType selectActionAmong(List<ActionType> actions) {
        return randomAmong(actions);
    }

    @Override
    public Card selectAmong(Set<Card> cards) {
        return cards.head();
    }

    @Override
    public Character selectAmong(List<Character> characters) {
        return randomAmong(characters);
    }

    @Override
    public Player selectPlayerAmong(List<Player> players) {
        return randomAmong(players);
    }

    @Override
    public Set<Card> selectManyAmong(Set<Card> cards) {
        return HashSet.of(cards.head());
    }

    @Override
    public DestructibleDistrict selectDistrictToDestroyAmong(Map<Player, List<DestructibleDistrict>> playersDistricts) {
        List<DestructibleDistrict> all = List.empty();
        for (List<DestructibleDistrict> ds : playersDistricts.values()) {
            all = all.appendAll(ds);
        }
        return randomAmong(all);
    }

    @Override
    public boolean acceptCard(Card card) {
        return random.nextBoolean();
    }

    private <T> T randomAmong(List<? extends T> list) {
        return list.get(random(list.size()));
    }

    private final Random random = new Random();
}
