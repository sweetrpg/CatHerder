package com.sweetrpg.catherder.common.block.entity;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.registry.ModBlockEntityTypes;
import com.sweetrpg.catherder.common.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelProperty;

public class PetDoorBlockEntity extends PlacedBlockEntity {

    private IStructureMaterial structureType = null;
//
    public static ModelProperty<IStructureMaterial> STRUCTURE = new ModelProperty<>();
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    public PetDoorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.CAT_TREE.get(), pos, blockState);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.structureType = NBTUtil.getRegistryValue(compound, "structureId", CatHerderAPI.STRUCTURE_MATERIAL.get());

        this.requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        NBTUtil.putRegistryValue(compound, "structureId", this.structureType);
    }

    public void setStructure(IStructureMaterial structureType) {
        this.structureType = structureType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

    public IStructureMaterial getStructure() {
        return this.structureType;
    }

    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(STRUCTURE, this.structureType)
                .withInitial(FACING, Direction.NORTH)
                .build();
    }
}
