package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class CatSizerItem extends Item implements ICatItem {

    private final CatSizerItem.Type type;

    public static enum Type {
        SMALL("small_catsizer"),
        BIG("big_catsizer");

        String n;

        Type(String n) {
            this.n = n;
        }

        public String getName() {
            return this.n;
        }
    }

    public CatSizerItem(CatSizerItem.Type typeIn, Properties properties) {
        super(properties);
        this.type = typeIn;
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (catIn.getAge() < 0) {

            if (!playerIn.level.isClientSide){
                playerIn.sendMessage(new TranslatableComponent("treat."+this.type.getName()+".too_young"), catIn.getUUID());
            }

            return InteractionResult.FAIL;
        }
        else {
            if (!playerIn.getAbilities().instabuild) {
                playerIn.getItemInHand(handIn).shrink(1);
            }

            if (!playerIn.level.isClientSide) {
                catIn.setCatSize(catIn.getCatSize() + (this.type == Type.BIG ? 1 : -1));
            }
            return InteractionResult.SUCCESS;
        }
    }
}
