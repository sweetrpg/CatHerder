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
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    public PetDoorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModTileEntityTypes.CAT_TREE.get(), pos, blockState);
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
