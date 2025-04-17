package com.sweetrpg.catherder.common.storage;

import com.google.common.collect.Lists;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class CatRespawnData implements ICatData {

    private final CatRespawnStorage storage;
    private final UUID uuid;
    private CompoundTag data;

    //TODO Make it list you can only add too
    private static final List<String> TAGS_TO_REMOVE = Lists.newArrayList(
            "Pos", "Health", "Motion", "Rotation", "FallDistance", "Fire", "Air", "OnGround",
            "Dimension", "PortalCooldown", "Passengers", "Leash", "InLove", "Leash", "HurtTime",
            "HurtByTimestamp", "DeathTime", "AbsorptionAmount", "FallFlying", "Brain", "Sitting"); // Remove cat mode

    protected CatRespawnData(CatRespawnStorage storageIn, UUID uuid) {
        this.storage = storageIn;
        this.uuid = uuid;
    }

    @Override
    public UUID getCatId() {
        return this.uuid;
    }

    @Override
    public String getCatName() {
        Component name = NBTUtil.getTextComponent(this.data, "CustomName");
        return name == null ? "" : name.getString();
    }

    @Override
    public UUID getOwnerId() {
        String str = data.getString("OwnerUUID");
        return "".equals(str) ? null : UUID.fromString(str);
    }

    @Override
    public String getOwnerName() {
        Component name = NBTUtil.getTextComponent(this.data, "lastKnownOwnerName");
        return name == null ? "" : name.getString();
    }

    public void populate(CatEntity catIn) {
        this.data = new CompoundTag();
        catIn.saveWithoutId(this.data);

        // Remove tags that don't need to be saved
        for (String tag : TAGS_TO_REMOVE) {
            this.data.remove(tag);
        }

        this.data.remove("UUID");
        this.data.remove("LoveCause");
    }

    @Nullable
    public CatEntity respawn(ServerLevel worldIn, Player playerIn, BlockPos pos) {
        CatEntity cat = ModEntityTypes.CAT.get().spawn(worldIn, (CompoundTag) null, (Component) null, playerIn, pos, MobSpawnType.TRIGGERED, true, false);

        // Failed for some reason
        if (cat == null) {
            return null;
        }

        CompoundTag compoundnbt = cat.saveWithoutId(new CompoundTag());
        UUID uuid = cat.getUUID();
        compoundnbt.merge(this.data);
        cat.setUUID(uuid);
        cat.load(compoundnbt);

        cat.setMode(Mode.DOCILE);
        cat.setOrderedToSit(true);

        return cat;
    }

    public void read(CompoundTag compound) {
        this.data = compound.getCompound("data");
    }

    public CompoundTag write(CompoundTag compound) {
        compound.put("data", this.data);
        return compound;
    }
}
