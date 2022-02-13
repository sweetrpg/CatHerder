package kittytalents.common.storage;

import kittytalents.KittyAccessories;
import kittytalents.KittyItems;
import kittytalents.api.feature.EnumGender;
import kittytalents.common.util.NBTUtil;
import kittytalents.common.util.WorldUtil;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class CatLocationData implements ICatData {

    private final CatLocationStorage storage;
    private final UUID uuid;
    private @Nullable UUID ownerId;

    // Location data
    private @Nullable Vec3 position;
    private @Nullable ResourceKey<Level> dimension;

    // Other saved data
    private @Nullable Component name;
    private @Nullable Component ownerName;
    private @Nullable EnumGender gender;
    private boolean hasRadarCollar;

    // Cached objects
    private kittytalents.common.entity.CatEntity cat;
    private LivingEntity owner;

    protected CatLocationData(CatLocationStorage storageIn, UUID uuid) {
        this.storage = storageIn;
        this.uuid = uuid;
    }

    @Override
    public UUID getDogId() {
        return this.uuid;
    }

    @Override
    @Nullable
    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Override
    public String getDogName() {
        return this.name == null ? "" : this.name.getString();
    }

    @Override
    public String getOwnerName() {
        return this.ownerName == null ? "" : this.ownerName.getString();
    }

    public void populate(kittytalents.common.entity.CatEntity catIn) {
        this.update(catIn);
    }

    public void update(kittytalents.common.entity.CatEntity catIn) {
        this.ownerId = catIn.getOwnerUUID();
        this.position = catIn.position();
        this.dimension = catIn.level.dimension();

        this.name = catIn.getName();
        this.ownerName = catIn.getOwnersName().orElse(null);
        this.gender = catIn.getGender();
        this.hasRadarCollar = catIn.getAccessory(KittyAccessories.RADIO_BAND.get()).isPresent();

        this.cat = catIn;
        this.storage.setDirty();
    }


    public void read(CompoundTag compound) {
        this.ownerId = NBTUtil.getUniqueId(compound, "ownerId");
        this.position = NBTUtil.getVector3d(compound);
        this.dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, NBTUtil.getResourceLocation(compound, "dimension"));
        this.name = NBTUtil.getTextComponent(compound, "name_text_component");
        if (compound.contains("gender", Tag.TAG_STRING)) {
            this.gender = EnumGender.bySaveName(compound.getString("gender"));
        }
        this.hasRadarCollar = compound.getBoolean("collar");
    }

    public CompoundTag write(CompoundTag compound) {
        NBTUtil.putUniqueId(compound, "ownerId", this.ownerId);
        NBTUtil.putVector3d(compound, this.position);
        NBTUtil.putResourceLocation(compound, "dimension", this.dimension.location());
        NBTUtil.putTextComponent(compound, "name_text_component", this.name);
        if (this.gender != null) {
            compound.putString("gender", this.gender.getSaveName());
        }
        compound.putBoolean("collar", this.hasRadarCollar);
        return compound;
    }

    public static CatLocationData from(CatLocationStorage storageIn, kittytalents.common.entity.CatEntity catIn) {
        CatLocationData locationData = new CatLocationData(storageIn, catIn.getUUID());
        locationData.populate(catIn);
        return locationData;
    }

    @Nullable
    public Optional<LivingEntity> getOwner(@Nullable Level worldIn) {
        if (worldIn == null) {
            return Optional.ofNullable(this.owner);
        }

        MinecraftServer server = worldIn.getServer();
        if (server == null) {
            throw new IllegalArgumentException("worldIn must be of ServerWorld");
        }

        for (ServerLevel world : server.getAllLevels()) {
            LivingEntity possibleOwner = WorldUtil.getCachedEntity(world, LivingEntity.class, this.owner, this.uuid);
            if (possibleOwner != null) {
                this.owner = possibleOwner;
                return Optional.of(this.owner);
            }
        }

        this.owner = null;
        return Optional.empty();
    }

    @Nullable
    public Optional<kittytalents.common.entity.CatEntity> getDog(@Nullable Level worldIn) {
        if (worldIn == null) {
            return Optional.ofNullable(this.cat);
        }

        MinecraftServer server = worldIn.getServer();
        if (server == null) {
            throw new IllegalArgumentException("worldIn must be of ServerWorld");
        }

        for (ServerLevel world : server.getAllLevels()) {
            kittytalents.common.entity.CatEntity possibleDog = WorldUtil.getCachedEntity(world, kittytalents.common.entity.CatEntity.class, this.cat, this.uuid);
            if (possibleDog != null) {
                this.cat = possibleDog;
                return Optional.of(this.cat);
            }
        }

        this.cat = null;
        return Optional.empty();
    }

    public boolean shouldDisplay(Level worldIn, Player playerIn, InteractionHand handIn) {
        return this.hasRadarCollar || playerIn.isCreative() || playerIn.getItemInHand(handIn).getItem() == KittyItems.CREATIVE_RADAR.get();
    }

    @Nullable
    public Component getName(@Nullable Level worldIn) {
        return this.getDog(worldIn).map(kittytalents.common.entity.CatEntity::getDisplayName).orElse(this.name);
    }

    @Nullable
    public Vec3 getPos(@Nullable ServerLevel worldIn) {
        return this.getDog(worldIn).map(kittytalents.common.entity.CatEntity::position).orElse(this.position);
    }

    @Nullable
    public Vec3 getPos() {
        return this.position;
    }

    @Nullable
    public ResourceKey<Level> getDimension() {
        return this.dimension;
    }

    @Override
    public String toString() {
        return "DogLocationData [uuid=" + uuid + ", owner=" + ownerId + ", position=" + position + ", dimension=" + dimension + ", name=" + name + ", gender=" + gender + ", hasRadarCollar=" + hasRadarCollar + "]";
    }


}
