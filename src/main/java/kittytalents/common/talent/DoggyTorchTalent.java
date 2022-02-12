package kittytalents.common.talent;

import kittytalents.KittyTalents;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import kittytalents.common.inventory.PackPuppyItemHandler;
import kittytalents.common.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

public class KittyTorchTalent extends TalentInstance {

    public KittyTorchTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void tick(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        if (dogIn.tickCount % 10 == 0 && dogIn.isTame()) {

            BlockPos pos = dogIn.blockPosition();
            BlockState torchState = Blocks.TORCH.defaultBlockState();

            if (dogIn.level.getMaxLocalRawBrightness(dogIn.blockPosition()) < 8 && dogIn.level.isEmptyBlock(pos) && torchState.canSurvive(dogIn.level, pos)) {
                PackPuppyItemHandler inventory = dogIn.getTalent(KittyTalents.PACK_PUPPY)
                    .map((inst) -> inst.cast(PackPuppyTalent.class).inventory()).orElse(null);

                // If null might be because no pack puppy
                if (this.level() >= 5) {
                    dogIn.level.setBlockAndUpdate(pos, torchState);
                }
                else if (inventory != null) { // If null might be because no pack puppy
                    Pair<ItemStack, Integer> foundDetails = InventoryUtil.findStack(inventory, (stack) -> stack.getItem() == Items.TORCH);
                    if (foundDetails != null && !foundDetails.getLeft().isEmpty()) {
                        ItemStack torchStack = foundDetails.getLeft();
                        dogIn.consumeItemFromStack(dogIn, torchStack);
                        inventory.setStackInSlot(foundDetails.getRight(), torchStack);
                        dogIn.level.setBlockAndUpdate(pos, torchState);
                    }
                }
            }
        }
    }
}
