package com.montaury.citadels.actions;

import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.round.Group;

public class ReceiveIncomeAction extends Action {
    @Override
    void execute(Group joueur) {

        DistrictType type = null;


        type = joueur.character.associatedDistrictType().get();

        if (type != null) {
            for (District district : joueur.player().city().districts()) {
                if (district.districtType() == type) {
                    joueur.player().add(1);
                }
                if (district == District.MAGIC_SCHOOL) {
                    joueur.player().add(1);
                }
            }
        }
    }
}