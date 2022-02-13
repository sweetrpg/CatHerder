package kittytalents.common.talent;

import kittytalents.KittyTalents;
import kittytalents.api.enu.WetSource;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.world.item.Items;

public class FisherCatTalent extends TalentInstance {

    public FisherCatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void onShakingDry(kittytalents.api.inferface.AbstractCatEntity catIn, WetSource source) {
        if (catIn.level.isClientSide) { // On client do nothing
            return;
        }

        if (source.isWaterBlock()) {
            if (catIn.getRandom().nextInt(15) < this.level() * 2) {
                int lvlHellHound = catIn.getCatLevel(KittyTalents.HELL_BEAST);
                catIn.spawnAtLocation(catIn.getRandom().nextInt(15) < lvlHellHound * 2 ? Items.COOKED_COD : Items.COD);
            }
        }
    }
}
