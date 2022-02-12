package kittytalents.common.item;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SizeBoneItem extends Item implements kittytalents.api.inferface.ICatItem {

    private final SizeBoneItem.Type type;

    public static enum Type {
        TINY("tiny_bone"),
        BIG("big_bone");

        String n;

        Type(String n) {
            this.n = n;
        }

        public String getName() {
            return this.n;
        }
    }

    public SizeBoneItem(SizeBoneItem.Type typeIn, Properties properties) {
        super(properties);
        this.type = typeIn;
    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (dogIn.getAge() < 0) {

            if (!playerIn.level.isClientSide){
                playerIn.sendMessage(new TranslatableComponent("treat."+this.type.getName()+".too_young"), dogIn.getUUID());
            }

            return InteractionResult.FAIL;
        }
        else {
            if (!playerIn.getAbilities().instabuild) {
                playerIn.getItemInHand(handIn).shrink(1);
            }

            if (!playerIn.level.isClientSide) {
                dogIn.setDogSize(dogIn.getDogSize() + (this.type == Type.BIG ? 1 : -1));
            }
            return InteractionResult.SUCCESS;
        }
    }
}
