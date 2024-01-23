package com.sweetrpg.catherder.client.block.model;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.common.block.CatTreeBlock;
import com.sweetrpg.catherder.common.block.entity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class CatTreeModel implements BakedModel {

    public static CatTreeItemOverride ITEM_OVERIDE = new CatTreeItemOverride();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    private ModelBakery modelLoader;
    private BlockModel model;
    private BakedModel bakedModel;

    private final Map<Tuple<IColorMaterial, Direction>, BakedModel> cache = Maps.newConcurrentMap();

    public CatTreeModel(ModelBakery modelLoader, BlockModel model, BakedModel bakedModel) {
        this.modelLoader = modelLoader;
        this.model = model;
        this.bakedModel = bakedModel;
    }

    public BakedModel getModelVariant(@Nonnull ModelData data) {
        return this.getModelVariant(data.get(CatTreeBlockEntity.COLOR), data.get(CatTreeBlockEntity.FACING));
    }

    public synchronized BakedModel getModelVariant(IColorMaterial color, Direction facing) {
        Tuple<IColorMaterial, Direction> key =
                new Tuple<>(color != null ? color : null, facing != null ? facing : Direction.NORTH);

        return this.cache.computeIfAbsent(key, (k) -> bakeModelVariant(k.getA(), k.getB()));
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
        return this.getModelVariant(null, Direction.NORTH).getQuads(state, side, rand, ModelData.EMPTY, null);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData data, @Nullable RenderType renderType) {
        return this.getModelVariant(data).getQuads(state, side, rand, data, renderType);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull ModelData data) {
        return this.getModelVariant(data).getParticleIcon(data);
    }

    @Override
    public ModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull ModelData tileData) {
        IColorMaterial color = null;
        Direction facing = Direction.NORTH;

        BlockEntity tile = world.getBlockEntity(pos);
        if(tile instanceof CatTreeBlockEntity catTreeBlockEntity) {
            color = catTreeBlockEntity.getColor();
        }

        if(state.hasProperty(CatTreeBlock.FACING)) {
            facing = state.getValue(CatTreeBlock.FACING);
        }

        return tileData.derive().with(CatTreeBlockEntity.COLOR, color).with(CatTreeBlockEntity.FACING, facing).build();
    }

    public BakedModel bakeModelVariant(@Nullable IColorMaterial colorResource, @Nonnull Direction facing) {
        List<BlockElement> parts = this.model.getElements();
        List<BlockElement> elements = new ArrayList<>(parts.size()); //We have to duplicate this so we can edit it below.
        for(BlockElement part : parts) {
            elements.add(new BlockElement(part.from, part.to, Maps.newHashMap(part.faces), part.rotation, part.shade));
        }

        BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
                Maps.newHashMap(this.model.textureMap), this.model.hasAmbientOcclusion(), this.model.getGuiLight(),
                this.model.getTransforms(), new ArrayList<>(this.model.getOverrides()));
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;

        Either<Material, String> colorTexture = findColorTexture(colorResource);
//        newModel.textureMap.put("bedding", findBeddingTexture(beddingResource));
        newModel.textureMap.put("color", colorTexture);
        newModel.textureMap.put("particle", colorTexture);

        return (new ModelBaker() {

            @Override
            public @Nullable BakedModel bake(ResourceLocation location, ModelState state, Function<Material, TextureAtlasSprite> sprites) {
                return newModel.bake(this, newModel, Material::sprite,
                        getModelRotation(facing),
                        createResourceVariant(colorResource, facing),
                        true
                );
            }

            @Override
            public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
                return Material::sprite;
            }

            @Override
            public UnbakedModel getModel(ResourceLocation location) {
                return newModel;
            }

            @Override
            @Nullable
            public BakedModel bake(ResourceLocation location, ModelState state) {
                return this.bake(location, state, this.getModelTextureGetter());
            }

        }).bake(null, null, null);
    }

    private ResourceLocation createResourceVariant(@Nonnull IColorMaterial colorResource, @Nonnull Direction facing) {
        String colorKey = colorResource != null
                ? colorResource.toString().replace(':', '.')
                : "catherder.cattree.color.missing";
        return new ModelResourceLocation(ModBlocks.CAT_TREE.getId(), "block/cat_tree#color=" + colorKey + ",facing=" + facing.getName());
    }

    private Either<Material, String> findColorTexture(@Nullable IColorMaterial resource) {
        return findTexture(resource != null ? resource.getTexture() : null);
    }

    private Either<Material, String> findTexture(@Nullable ResourceLocation resource) {
        if(resource == null) {
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
