package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class CatSizerItem extends Item implements ICatItem {

    private final CatSizerItem.Type type;

    public enum Type {
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
    public InteractionResult processInteract(AbstractCatEntity cat, Level level, Player player, InteractionHand hand) {
        if(cat.getAge() < 0) {

            if(!player.level.isClientSide) {
                player.sendMessage(new TranslatableComponent("treat.catherder." + this.type.getName() + ".too_young"), cat.getUUID());
            }

            return InteractionResult.FAIL;
        }
        else {
            int size = cat.getCatSize();

            if(!player.getAbilities().instabuild) {
                if(size > CatEntity.MIN_CAT_SIZE && size < CatEntity.MAX_CAT_SIZE) {
                    player.getItemInHand(hand).shrink(1);
                }
            }

            if(!player.level.isClientSide) {
                cat.setCatSize(size + (this.type == Type.BIG ? 1 : -1));
            }

            return InteractionResult.SUCCESS;
        }
    }
}
