package kittytalents.common.talent;

import kittytalents.KittyTalents;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import kittytalents.common.inventory.PackKittyItemHandler;
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
    public void tick(kittytalents.api.inferface.AbstractCatEntity catIn) {
        if (catIn.tickCount % 10 == 0 && catIn.isTame()) {

            BlockPos pos = catIn.blockPosition();
            BlockState torchState = Blocks.TORCH.defaultBlockState();

            if (catIn.level.getMaxLocalRawBrightness(catIn.blockPosition()) < 8 && catIn.level.isEmptyBlock(pos) && torchState.canSurvive(catIn.level, pos)) {
                PackKittyItemHandler inventory = catIn.getTalent(KittyTalents.PACK_KITTY)
                    .map((inst) -> inst.cast(PackKittyTalent.class).inventory()).orElse(null);

                // If null might be because no pack puppy
                if (this.level() >= 5) {
                    catIn.level.setBlockAndUpdate(pos, torchState);
                }
                else if (inventory != null) { // If null might be because no pack puppy
                    Pair<ItemStack, Integer> foundDetails = InventoryUtil.findStack(inventory, (stack) -> stack.getItem() == Items.TORCH);
                    if (foundDetails != null && !foundDetails.getLeft().isEmpty()) {
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
