package com.sweetrpg.catherder.client.block.model;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.block.entity.PetDoorBlockEntity;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.registries.IRegistryDelegate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class PetDoorModel implements BakedModel {

    public static PetDoorItemOverride ITEM_OVERIDE = new PetDoorItemOverride();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    private ForgeModelBakery modelLoader;
    private BlockModel model;
    private BakedModel bakedModel;

    private final Map<Tuple<IRegistryDelegate<IStructureMaterial>, Direction>, BakedModel> cache = Maps.newHashMap();

    public PetDoorModel(ForgeModelBakery modelLoader, BlockModel model, BakedModel bakedModel) {
        this.modelLoader = modelLoader;
        this.model = model;
        this.bakedModel = bakedModel;
    }

    public BakedModel getModelVariant(@Nonnull IModelData data) {
        return this.getModelVariant(data.getData(PetDoorBlockEntity.STRUCTURE), data.getData(PetDoorBlockEntity.FACING));
    }

    public BakedModel getModelVariant(IStructureMaterial structure, Direction facing) {
        Tuple<IRegistryDelegate<IStructureMaterial>, Direction> key =
                new Tuple<>(structure != null ? structure.delegate : null, facing != null ? facing : Direction.NORTH);

        return this.cache.computeIfAbsent(key, (k) -> bakeModelVariant(k.getA(), k.getB()));
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
        return this.getModelVariant(null, Direction.NORTH).getQuads(state, side, rand, EmptyModelData.INSTANCE);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        return this.getModelVariant(data).getQuads(state, side, rand, data);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull IModelData data) {
        return this.getModelVariant(data).getParticleIcon(data);
    }

    @Override
    public IModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        IStructureMaterial structure = null;
        Direction facing = Direction.NORTH;

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof PetDoorBlockEntity) {
            structure = ((PetDoorBlockEntity) tile).getStructure();
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        }

        tileData.setData(PetDoorBlockEntity.STRUCTURE, structure);
        tileData.setData(PetDoorBlockEntity.FACING, facing);

        return tileData;
    }

    public BakedModel bakeModelVariant(@Nullable IRegistryDelegate<IStructureMaterial> structureResource, @Nonnull Direction facing) {
        List<BlockElement> parts = this.model.getElements();
        List<BlockElement> elements = new ArrayList<>(parts.size()); //We have to duplicate this so we can edit it below.
        for (BlockElement part : parts) {
            elements.add(new BlockElement(part.from, part.to, Maps.newHashMap(part.faces), part.rotation, part.shade));
        }

        BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
            Maps.newHashMap(this.model.textureMap), this.model.hasAmbientOcclusion(), this.model.getGuiLight(),
            this.model.getTransforms(), new ArrayList<>(this.model.getOverrides()));
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;


        Either<Material, String> structureTexture = findStructureTexture(structureResource);
//        newModel.textureMap.put("bedding", findBeddingTexture(beddingResource));
        newModel.textureMap.put("structure", structureTexture);
        newModel.textureMap.put("particle", structureTexture);

        return newModel.bake(this.modelLoader, newModel, ForgeModelBakery.defaultTextureGetter(), getModelRotation(facing), createResourceVariant(structureResource, facing), true);
    }

    private ResourceLocation createResourceVariant(@Nonnull IRegistryDelegate<IStructureMaterial> structureResource, @Nonnull Direction facing) {
        String structureKey = structureResource != null
                ? structureResource.name().toString().replace(':', '.')
                : "petdoor.structure.missing";
        return new ModelResourceLocation(CatHerderAPI.MOD_ID, "block/pet_door#structure=" + structureKey + ",facing=" + facing.getName());
    }

    private Either<Material, String> findStructureTexture(@Nullable IRegistryDelegate<IStructureMaterial> resource) {
        return findTexture(resource != null ? resource.get().getTexture() : null);
    }

    private Either<Material, String> findTexture(@Nullable ResourceLocation resource) {
        if (resource == null) {
            resource = MISSING_TEXTURE;
        }

        return Either.left(new Material(InventoryMenu.BLOCK_ATLAS, resource));
    }

    private BlockModelRotation getModelRotation(@Nonnull Direction dir) {
        return switch(dir) {
            default -> BlockModelRotation.X0_Y0; // North
            case EAST -> BlockModelRotation.X0_Y90;
            case SOUTH -> BlockModelRotation.X0_Y180;
            case WEST -> BlockModelRotation.X0_Y270;
        };
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.bakedModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.bakedModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.bakedModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return this.bakedModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.bakedModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return this.bakedModel.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {
        return ITEM_OVERIDE;
    }
}
