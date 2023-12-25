package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModTalents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LootingLevelEvent;

public class TomcatTalent extends TalentInstance {

    public TomcatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    public static void onLootDrop(final LootingLevelEvent event) {
        DamageSource damageSource = event.getDamageSource();

        // Possible to be null #265
        if(damageSource != null) {
            Entity trueSource = damageSource.getEntity();
            if(trueSource instanceof CatEntity) {
                CatEntity cat = (CatEntity) trueSource;
                int level = cat.getCatLevel(ModTalents.TOMCAT);

                if(cat.getRandom().nextInt(6) < level + (level >= 5 ? 1 : 0)) {
                    event.setLootingLevel(event.getLootingLevel() + level / 2);
                }
            }
        }
    }
}
