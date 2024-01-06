package com.sweetrpg.catherder.common.block.entity;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModBlockEntityTypes;
import com.sweetrpg.catherder.common.storage.CatLocationData;
import com.sweetrpg.catherder.common.storage.CatLocationStorage;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class CatTreeBlockEntity extends PlacedBlockEntity {

    private IColorMaterial colorType = null;

    public static ModelProperty<IColorMaterial> COLOR = new ModelProperty<>();
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    @Deprecated
    @Nullable
    private CatEntity cat;
    @Nullable
    private UUID catUUID;

    @Nullable
    private Component name;
    @Nullable
    private Component ownerName;

    public int timeoutCounter;

    public CatTreeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.CAT_TREE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if(!(blockEntity instanceof CatTreeBlockEntity tree)) {
            return;
        }

        // Only run update code every 5 ticks (0.25s)
        if(++tree.timeoutCounter < 5) { return; }

        List<CatEntity> catList = tree.level.getEntitiesOfClass(CatEntity.class, new AABB(pos).inflate(5, 5, 5));

        for(CatEntity cat : catList) {
            // make cat tree remember who placed and only their cats can attach to it
            UUID placerId = tree.getPlacerId();
            if(placerId != null && placerId.equals(cat.getOwnerUUID()) && !cat.getCatTreePos().isPresent()) {
                cat.setCatTreePos(tree.worldPosition);
            }
        }

        tree.timeoutCounter = 0;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.colorType = NBTUtil.getRegistryValue(compound, "colorId", CatHerderAPI.COLOR_MATERIAL.get());
        if (this.colorType == null) {
            this.colorType = CatTreeUtil.pickRandom(CatHerderAPI.COLOR_MATERIAL.get());
        }
//        this.beddingType = NBTUtil.getRegistryValue(compound, "beddingId", CatHerderAPI.BEDDING_MATERIAL);

        this.catUUID = NBTUtil.getUniqueId(compound, "ownerId");
        this.name = NBTUtil.getTextComponent(compound, "name");
        this.ownerName = NBTUtil.getTextComponent(compound, "ownerName");
        this.requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        NBTUtil.putRegistryValue(compound, "colorId", this.colorType);
//        NBTUtil.putRegistryValue(compound, "beddingId", this.beddingType);

        NBTUtil.putUniqueId(compound, "ownerId", this.catUUID);
        NBTUtil.putTextComponent(compound, "name", this.name);
        NBTUtil.putTextComponent(compound, "ownerName", this.ownerName);
    }

    public void setColor(IColorMaterial colorType) {
        this.colorType = colorType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

//    public void setBedding(IBeddingMaterial beddingType) {
//        this.beddingType = beddingType;
//        this.setChanged();
//        this.requestModelDataUpdate();
//    }

    public IColorMaterial getColor() {
        return this.colorType;
    }

//    public IBeddingMaterial getBedding() {
//        return this.beddingType;
//    }

    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(COLOR, this.colorType)
//                .withInitial(BEDDING, this.beddingType)
                .withInitial(FACING, Direction.NORTH)
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
        if (this.catUUID == null || this.level == null) {
            return null;
        }

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
