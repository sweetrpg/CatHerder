package kittytalents.common.block.tileentity;

import kittytalents.KittyTileEntityTypes;
import kittytalents.api.KittyTalentsAPI;
import kittytalents.api.registry.IBeddingMaterial;
import kittytalents.api.registry.ICasingMaterial;
import kittytalents.common.storage.DogLocationData;
import kittytalents.common.storage.DogLocationStorage;
import kittytalents.common.util.NBTUtil;
import kittytalents.common.util.WorldUtil;
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

public class DogBedTileEntity extends PlacedTileEntity {

    private ICasingMaterial casingType = null;
    private IBeddingMaterial beddingType = null;


    public static ModelProperty<ICasingMaterial> CASING = new ModelProperty<>();
    public static ModelProperty<IBeddingMaterial> BEDDING = new ModelProperty<>();
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    private @Deprecated @Nullable
    kittytalents.common.entity.CatEntity cat;
    private @Nullable UUID dogUUID;

    private @Nullable Component name;
    private @Nullable Component ownerName;

    public DogBedTileEntity(BlockPos pos, BlockState blockState) {
        super(KittyTileEntityTypes.CAT_BED.get(), pos, blockState);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.casingType = NBTUtil.getRegistryValue(compound, "casingId", KittyTalentsAPI.CASING_MATERIAL);
        this.beddingType = NBTUtil.getRegistryValue(compound, "beddingId", KittyTalentsAPI.BEDDING_MATERIAL);

        this.dogUUID = NBTUtil.getUniqueId(compound, "ownerId");
        this.name = NBTUtil.getTextComponent(compound, "name");
        this.ownerName = NBTUtil.getTextComponent(compound, "ownerName");
        this.requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        NBTUtil.putRegistryValue(compound, "casingId", this.casingType);
        NBTUtil.putRegistryValue(compound, "beddingId", this.beddingType);

        NBTUtil.putUniqueId(compound, "ownerId", this.dogUUID);
        NBTUtil.putTextComponent(compound, "name", this.name);
        NBTUtil.putTextComponent(compound, "ownerName", this.ownerName);
    }

    public void setCasing(ICasingMaterial casingType) {
        this.casingType = casingType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

    public void setBedding(IBeddingMaterial beddingType) {
        this.beddingType = beddingType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

    public ICasingMaterial getCasing() {
        return this.casingType;
    }

    public IBeddingMaterial getBedding() {
        return this.beddingType;
    }

    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(CASING, this.casingType)
                .withInitial(BEDDING, this.beddingType)
                .withInitial(FACING, Direction.NORTH)
                .build();
    }

    public void setOwner(@Nullable kittytalents.common.entity.CatEntity owner) {
        this.setOwner(owner == null ? null : owner.getUUID());

        this.cat = owner;

    }

    public void setOwner(@Nullable UUID owner) {
        this.cat = null;
        this.dogUUID = owner;

        this.setChanged();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.dogUUID;
    }

    @Nullable
    public kittytalents.common.entity.CatEntity getOwner() {
       return WorldUtil.getCachedEntity(this.level, kittytalents.common.entity.CatEntity.class, this.cat, this.dogUUID);
    }

    @Nullable
    public Component getBedName() {
        return this.name;
    }

    @Nullable
    public Component getOwnerName() {
        if (this.dogUUID == null || this.level == null) { return null; }

        DogLocationData locData = DogLocationStorage
                .get(this.level)
                .getData(this.dogUUID);

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
