package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.inventory.PackCatItemHandler;
import com.sweetrpg.catherder.common.registry.ModTalents;
import com.sweetrpg.catherder.common.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

public class CatTorchTalent extends TalentInstance {

    public CatTorchTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void tick(AbstractCatEntity catIn) {
        if(catIn.tickCount % 10 == 0 && catIn.isTame()) {

            BlockPos pos = catIn.blockPosition();
            BlockState torchState = Blocks.TORCH.defaultBlockState();

            if(catIn.level.getMaxLocalRawBrightness(catIn.blockPosition()) < 8 && catIn.level.isEmptyBlock(pos) && torchState.canSurvive(catIn.level, pos)) {
                PackCatItemHandler inventory = catIn.getTalent(ModTalents.PACK_CAT)
                        .map((inst) -> inst.cast(PackCatTalent.class).inventory()).orElse(null);

                // If null might be because no pack cat
                if(this.level() >= 5) {
                    catIn.level.setBlockAndUpdate(pos, torchState);
                }
                else if(inventory != null) { // If null might be because no pack cat
                    Pair<ItemStack, Integer> foundDetails = InventoryUtil.findStack(inventory, (stack) -> stack.getItem() == Items.TORCH);
                    if(foundDetails != null && !foundDetails.getLeft().isEmpty()) {
                        ItemStack torchStack = foundDetails.getLeft();
                        catIn.consumeItemFromStack(catIn, torchStack);
                        inventory.setStackInSlot(foundDetails.getRight(), torchStack);
                        catIn.level.setBlockAndUpdate(pos, torchState);
                    }
                }
            }
        }
    }
}
