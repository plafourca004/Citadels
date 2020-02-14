package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.player.Player;
import io.vavr.collection.List;

import static com.montaury.citadels.district.District.GREAT_WALL;
import static com.montaury.citadels.district.District.HAUNTED_CITY;

public class City {
    private static final int END_GAME_DISTRICT_NUMBER = 7;
    private final Board board;
    private List<Card> districtCards = List.empty();

    public City(Board board) {
        this.board = board;
    }

    public void buildDistrict(Card card) {
        districtCards = districtCards.append(card);
        if (isComplete()) {
            board.mark(this);
        }
    }

    public boolean isComplete() {
        return districtCards.size() >= END_GAME_DISTRICT_NUMBER;
    }

    public int score(Possession possession) {
        int score = 0;
        for (int a = 0; a < districts().size(); a++) {
            score += districts().get(a).cost();
        }

        score = score + districtsScoreBonus(possession);
        if (winsAllColorBonus()) {
            score += 3;
        }
        if (board.isFirst(this)) {
            score += (4);
        }
        if (isComplete()) {
            score += (2);
        }

        return score;
    }

    private int districtsScoreBonus(Possession possession) {
        int score = 0;
        for (District d : districts()) {
            if (d == District.DRAGON_GATE) {
                score = score + 2;
            }
            if (d == District.UNIVERSITY) {
                score = score + 2;
            }
            if (d == District.TREASURY) {
                score += score + possession.gold;
            }
            if (d == District.MAP_ROOM) {
                score += possession.hand.size();
            }
        }
        return score;
    }

    private boolean winsAllColorBonus() {
        int districtTypes[] = new int[DistrictType.values().length];
        for (District d : districts()) {
            districtTypes[d.districtType().ordinal()]++;
        }
        if (districtTypes[DistrictType.MILITARY.ordinal()] > 0 && districtTypes[DistrictType.NOBLE.ordinal()] > 0 && districtTypes[DistrictType.RELIGIOUS.ordinal()] > 0 && districtTypes[DistrictType.SPECIAL.ordinal()] > 0 && districtTypes[DistrictType.TRADE.ordinal()] > 0)
            return true;

        if (has(HAUNTED_CITY)) {
            int zeros = 0;
            for (int i = 0; i < districtTypes.length; i++) {
                if (districtTypes[i] == 0) {
                    zeros++;
                }
            }
            if (zeros == 1 && districtTypes[DistrictType.SPECIAL.ordinal()] > 1) {
                return true;
            }
            else return false;
        } else return false;
    }

    public boolean has(District district) {
        return districts().contains(district);
    }

    public void destroyDistrict(Card card) {
        districtCards = districtCards.remove(card);
    }

    public List<DestructibleDistrict> districtsDestructibleBy(Player player) {
        return isComplete() ?
                List.empty() :
                districtCards
                        .filter(card -> card.district().isDestructible())
                        .filter(card -> player.canAfford(destructionCost(card)))
                        .map(card -> new DestructibleDistrict(card, destructionCost(card)));
    }

    private int destructionCost(Card card) {
        return card.district().cost() - (has(GREAT_WALL) && card.district() != GREAT_WALL ? 0 : (1));
    }

    public List<District> districts() {
        return districtCards.map(Card::district);
    }
}
