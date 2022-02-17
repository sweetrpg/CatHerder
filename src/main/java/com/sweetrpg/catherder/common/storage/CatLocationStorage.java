package com.sweetrpg.catherder.common.storage;

import com.google.common.collect.Maps;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class CatLocationStorage extends SavedData {

    private Map<UUID, CatLocationData> locationDataMap = Maps.newConcurrentMap();

    public CatLocationStorage() {}

    public static CatLocationStorage get(Level world) {
        if (!(world instanceof ServerLevel)) {
            throw new RuntimeException("Tried to access cat location data from the client. This should not happen...");
        }

        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);

        DimensionDataStorage storage = overworld.getDataStorage();
        return storage.computeIfAbsent(CatLocationStorage::load, CatLocationStorage::new, Constants.STORAGE_CAT_LOCATION);
    }

    public Stream<CatLocationData> getCats(LivingEntity owner) {
        UUID ownerId = owner.getUUID();

        return this.locationDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()));
    }

    public Stream<CatLocationData> getCats(LivingEntity owner, ResourceKey<Level> key) {
        UUID ownerId = owner.getUUID();

        return this.locationDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()))
                .filter(data -> key.equals(data.getDimension()));
    }

    @Nullable
    public CatLocationData getData(CatEntity catIn) {
        return getData(catIn.getUUID());
    }

    @Nullable
    public CatLocationData getData(UUID uuid) {
        if (this.locationDataMap.containsKey(uuid)) {
            return this.locationDataMap.get(uuid);
        }

        return null;
    }

    @Nullable
    public CatLocationData remove(CatEntity catIn) {
        return remove(catIn.getUUID());
    }

    @Nullable
    public CatLocationData getOrCreateData(CatEntity catIn) {
        UUID uuid = catIn.getUUID();

        return this.locationDataMap.computeIfAbsent(uuid, ($) -> {
            this.setDirty();
            return CatLocationData.from(this, catIn);
        });
    }

    @Nullable
    public CatLocationData remove(UUID uuid) {
        if (this.locationDataMap.containsKey(uuid)) {
            CatLocationData storage = this.locationDataMap.remove(uuid);

            // Mark dirty so changes are saved
            this.setDirty();
            return storage;
        }

        return null;
    }

    @Nullable
    public CatLocationData putData(CatEntity catIn) {
        UUID uuid = catIn.getUUID();

        CatLocationData storage = new CatLocationData(this, uuid);

        this.locationDataMap.put(uuid, storage);
        // Mark dirty so changes are saved
        this.setDirty();
        return storage;
    }

    public Set<UUID> getAllUUID() {
        return Collections.unmodifiableSet(this.locationDataMap.keySet());
    }

    public Collection<CatLocationData> getAll() {
        return Collections.unmodifiableCollection(this.locationDataMap.values());
    }

    public static CatLocationStorage load(CompoundTag nbt) {
        CatLocationStorage store = new CatLocationStorage();
        store.locationDataMap.clear();

        ListTag list = nbt.getList("locationData", Tag.TAG_COMPOUND);

        // Old style
        if (list.isEmpty()) {
            list = nbt.getList("cat_locations", Tag.TAG_COMPOUND);
        }

        for (int i = 0; i < list.size(); ++i) {
            CompoundTag locationCompound = list.getCompound(i);

            UUID uuid = NBTUtil.getUniqueId(locationCompound, "uuid");

            // Old style
            if (uuid == null) {
                uuid = NBTUtil.getUniqueId(locationCompound, "entityId");
            }

            CatLocationData locationData = new CatLocationData(store, uuid);
            locationData.read(locationCompound);

            if (uuid == null) {
                CatHerder.LOGGER.info("Failed to load cat location data. Please report to mod author...");
                CatHerder.LOGGER.info(locationData);
                continue;
            }

            store.locationDataMap.put(uuid, locationData);
        }

        return store;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        ListTag list = new ListTag();

        for (Entry<UUID, CatLocationData> entry : this.locationDataMap.entrySet()) {
            CompoundTag locationCompound = new CompoundTag();

            CatLocationData locationData = entry.getValue();
            NBTUtil.putUniqueId(locationCompound, "uuid", entry.getKey());
            locationData.write(locationCompound);

            list.add(locationCompound);
        }

        compound.put("locationData", list);

        return compound;
    }

}
