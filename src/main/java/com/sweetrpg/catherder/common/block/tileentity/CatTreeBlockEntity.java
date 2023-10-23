package com.sweetrpg.catherder.common.block.tileentity;

import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModTileEntityTypes;
import com.sweetrpg.catherder.common.storage.CatLocationData;
import com.sweetrpg.catherder.common.storage.CatLocationStorage;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelDataManager;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;
import java.util.UUID;

public class CatTreeBlockEntity extends PlacedBlockEntity {

//    private ICasingMaterial casingType = null;
//    private IBeddingMaterial beddingType = null;
//
//    public static ModelProperty<ICasingMaterial> CASING = new ModelProperty<>();
//    public static ModelProperty<IBeddingMaterial> BEDDING = new ModelProperty<>();
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    private @Deprecated @Nullable
    CatEntity cat;
    private @Nullable
    UUID catUUID;

    private @Nullable
    Component name;
    private @Nullable Component ownerName;

    public CatTreeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModTileEntityTypes.CAT_TREE.get(), pos, blockState);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

//        this.casingType = NBTUtil.getRegistryValue(compound, "casingId", CatHerderAPI.CASING_MATERIAL);
//        this.beddingType = NBTUtil.getRegistryValue(compound, "beddingId", CatHerderAPI.BEDDING_MATERIAL);

        this.catUUID = NBTUtil.getUniqueId(compound, "ownerId");
        this.name = NBTUtil.getTextComponent(compound, "name");
        this.ownerName = NBTUtil.getTextComponent(compound, "ownerName");
        this.requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

//        NBTUtil.putRegistryValue(compound, "casingId", this.casingType);
//        NBTUtil.putRegistryValue(compound, "beddingId", this.beddingType);

        NBTUtil.putUniqueId(compound, "ownerId", this.catUUID);
        NBTUtil.putTextComponent(compound, "name", this.name);
        NBTUtil.putTextComponent(compound, "ownerName", this.ownerName);
    }

//    public void setCasing(ICasingMaterial casingType) {
//        this.casingType = casingType;
//        this.setChanged();
//        this.requestModelDataUpdate();
//    }
//
//    public void setBedding(IBeddingMaterial beddingType) {
//        this.beddingType = beddingType;
//        this.setChanged();
//        this.requestModelDataUpdate();
//    }
//
//    public ICasingMaterial getCasing() {
//        return this.casingType;
//    }
//
//    public IBeddingMaterial getBedding() {
//        return this.beddingType;
//    }

    @Override
    public ModelData getModelData() {
        return ModelData.builder()
//                .withInitial(CASING, this.casingType)
//                .withInitial(BEDDING, this.beddingType)
                .with(FACING, Direction.NORTH)
                .build();
    }

    public void setOwner(@Nullable CatEntity owner) {
        this.setOwner(owner == null ? null : owner.getUUID());

        this.cat = owner;

    }

    public void setOwner(@Nullable UUID owner) {
        this.cat = null;
        this.catUUID = owner;

        this.setChanged();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.catUUID;
    }

    @Nullable
    public CatEntity getOwner() {
       return WorldUtil.getCachedEntity(this.level, CatEntity.class, this.cat, this.catUUID);
    }

    @Nullable
    public Component getBedName() {
        return this.name;
    }

    @Nullable
    public Component getOwnerName() {
        if (this.catUUID == null || this.level == null) { return null; }

        CatLocationData locData = CatLocationStorage
                .get(this.level)
                .getData(this.catUUID);

        if (locData != null) {
            Component text = locData.getName(this.level);
            if (text != null) {
                this.ownerName = text;
            }
        }

        return this.ownerName;
    }

    public boolean shouldDisplayName(LivingEntity camera) {
        return true;
    }

    public void setBedName(@Nullable Component nameIn) {
        this.name = nameIn;
        this.setChanged();
    }
}
