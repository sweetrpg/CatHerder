package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.DataKey;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.common.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatrolItem extends Item implements ICatItem {

    public static DataKey<List<BlockPos>> POS = DataKey.make();

    public PatrolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        this.addPosToStack(playerIn.getItemInHand(handIn), playerIn.blockPosition());
        return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
    }

    public void addPosToStack(ItemStack stackIn, BlockPos posIn) {
        CompoundTag tag = stackIn.getOrCreateTag();
        ListTag list = tag.getList("patrolPos", Tag.TAG_COMPOUND);
        CompoundTag pos = new CompoundTag();
        NBTUtil.putBlockPos(pos, posIn);
        list.add(pos);
        tag.put("patrolPos", list);
    }

    public List<BlockPos> getPos(ItemStack stackIn) {
        if (stackIn.hasTag() && stackIn.getTag().contains("patrolPos", Tag.TAG_LIST)) {
            ListTag list = stackIn.getTag().getList("patrolPos", Tag.TAG_COMPOUND);
            List<BlockPos> pos = new ArrayList<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                pos.add(NBTUtil.getBlockPos(list.getCompound(i)));
            }
            return pos;
        }

        return Collections.emptyList();
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        List<BlockPos> pos = getPos(playerIn.getItemInHand(handIn));
        CatHerder.LOGGER.debug("{}", pos);
        catIn.setData(POS, pos);
        return InteractionResult.SUCCESS;
    }
}
