package com.sweetrpg.catherder.common.block.tileentity;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
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
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;
import java.util.UUID;

public class PetDoorBlockEntity extends PlacedBlockEntity {

    private IStructureMaterial structureType = null;
//
    public static ModelProperty<IStructureMaterial> STRUCTURE = new ModelProperty<>();
//    public static ModelProperty<IBeddingMaterial> BEDDING = new ModelProperty<>();
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

//    private @Deprecated @Nullable
//    CatEntity cat;
//    private @Nullable
//    UUID catUUID;

//    private @Nullable
//    Component name;
//    private @Nullable Component ownerName;

    public PetDoorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModTileEntityTypes.CAT_TREE.get(), pos, blockState);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.structureType = NBTUtil.getRegistryValue(compound, "structureId", CatHerderAPI.STRUCTURE_MATERIAL.get());
//        this.beddingType = NBTUtil.getRegistryValue(compound, "beddingId", CatHerderAPI.BEDDING_MATERIAL);

//        this.catUUID = NBTUtil.getUniqueId(compound, "ownerId");
//        this.name = NBTUtil.getTextComponent(compound, "name");
//        this.ownerName = NBTUtil.getTextComponent(compound, "ownerName");
        this.requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        NBTUtil.putRegistryValue(compound, "structureId", this.structureType);
//        NBTUtil.putRegistryValue(compound, "beddingId", this.beddingType);

//        NBTUtil.putUniqueId(compound, "ownerId", this.catUUID);
//        NBTUtil.putTextComponent(compound, "name", this.name);
//        NBTUtil.putTextComponent(compound, "ownerName", this.ownerName);
    }

    public void setStructure(IStructureMaterial structureType) {
        this.structureType = structureType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

//    public void setBedding(IBeddingMaterial beddingType) {
//        this.beddingType = beddingType;
//        this.setChanged();
//        this.requestModelDataUpdate();
//    }

    public IStructureMaterial getStructure() {
        return this.structureType;
    }

//    public IBeddingMaterial getBedding() {
//        return this.beddingType;
//    }

    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(STRUCTURE, this.structureType)
                .withInitial(FACING, Direction.NORTH)
                .build();
    }

//    public void setOwner(@Nullable CatEntity owner) {
//        this.setOwner(owner == null ? null : owner.getUUID());
//
//        this.cat = owner;
//
//    }
//
//    public void setOwner(@Nullable UUID owner) {
//        this.cat = null;
//        this.catUUID = owner;
//
//        this.setChanged();
//    }
//
//    @Nullable
//    public UUID getOwnerUUID() {
//        return this.catUUID;
//    }
//
//    @Nullable
//    public CatEntity getOwner() {
//       return WorldUtil.getCachedEntity(this.level, CatEntity.class, this.cat, this.catUUID);
//    }
//
//    @Nullable
//    public Component getBedName() {
//        return this.name;
//    }
//
//    @Nullable
//    public Component getOwnerName() {
//        if (this.catUUID == null || this.level == null) { return null; }
//
//        CatLocationData locData = CatLocationStorage
//                .get(this.level)
//                .getData(this.catUUID);
//
//        if (locData != null) {
//            Component text = locData.getName(this.level);
//            if (text != null) {
//                this.ownerName = text;
//            }
//        }
//
//        return this.ownerName;
//    }
//
//    public boolean shouldDisplayName(LivingEntity camera) {
//        return true;
//    }
//
//    public void setBedName(@Nullable Component nameIn) {
//        this.name = nameIn;
//        this.setChanged();
//    }
}