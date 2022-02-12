package kittytalents.common.talent;

import kittytalents.KittyTalents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LootingLevelEvent;

public class HunterDogTalent {

    public static void onLootDrop(final LootingLevelEvent event) {
        DamageSource damageSource = event.getDamageSource();

        // Possible to be null #265
        if (damageSource != null) {
            Entity trueSource = damageSource.getEntity();
            if (trueSource instanceof kittytalents.common.entity.CatEntity) {
                kittytalents.common.entity.CatEntity cat = (kittytalents.common.entity.CatEntity) trueSource;
                int level = cat.getDogLevel(KittyTalents.HUNTER_DOG);

                if (cat.getRandom().nextInt(6) < level + (level >= 5 ? 1 : 0)) {
                    event.setLootingLevel(event.getLootingLevel() + level / 2);
                }
            }
        }
    }
}
