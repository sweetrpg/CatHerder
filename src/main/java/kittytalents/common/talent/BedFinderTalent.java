package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BedFinderTalent extends TalentInstance {

    public BedFinderTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void livingTick(kittytalents.api.inferface.AbstractCatEntity cat) {

    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (this.level() > 0) {
            if (!playerIn.hasPassenger(catIn)) {
                if (playerIn.getItemInHand(handIn).getItem() == Items.BONE && catIn.canInteract(playerIn)) {

                    if (catIn.startRiding(playerIn)) {
                        if (!catIn.level.isClientSide) {
                            catIn.setOrderedToSit(true);
                        }

                        playerIn.displayClientMessage(new TranslatableComponent("talent.kittytalents.bed_finder.dog_mount", catIn.getGenderPronoun()), true);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                catIn.stopRiding();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
