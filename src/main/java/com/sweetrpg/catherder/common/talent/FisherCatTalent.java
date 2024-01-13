package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.enu.WetSource;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.registry.ModTalents;
import net.minecraft.world.item.Items;

public class FisherCatTalent extends TalentInstance {

    public FisherCatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void onShakingDry(AbstractCatEntity catIn, WetSource source) {
        if(catIn.level.isClientSide) { // On client do nothing
            return;
        }

        if(source.isWaterBlock()) {
            if(catIn.getRandom().nextInt(15) < this.level() * 2) {
                int lvlHellHound = catIn.getCatLevel(ModTalents.HELL_BEAST);
                catIn.spawnAtLocation(catIn.getRandom().nextInt(15) < lvlHellHound * 2 ? Items.COOKED_COD : Items.COD);
            }
        }
    }
}
