package com.sweetrpg.catherder.common.storage;

import com.google.common.collect.Maps;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class CatRespawnStorage extends SavedData {

    private Map<UUID, CatRespawnData> respawnDataMap = Maps.newConcurrentMap();

    public CatRespawnStorage() {}

    public static CatRespawnStorage get(Level world) {
        if (!(world instanceof ServerLevel)) {
            throw new RuntimeException("Tried to access cat respawn data from the client. This should not happen...");
        }

        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);

        DimensionDataStorage storage = overworld.getDataStorage();
        return storage.computeIfAbsent(CatRespawnStorage::load, CatRespawnStorage::new, Constants.STORAGE_CAT_RESPAWN);
    }

    public Stream<CatRespawnData> getCats(@Nonnull UUID ownerId) {
        return this.respawnDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()));
    }

    public Stream<CatRespawnData> getCats(@Nonnull String ownerName) {
        return this.respawnDataMap.values().stream()
                .filter(data -> ownerName.equals(data.getOwnerName()));
    }

    @Nullable
    public CatRespawnData getData(UUID uuid) {
        if (this.respawnDataMap.containsKey(uuid)) {
            return this.respawnDataMap.get(uuid);
        }

        return null;
    }

    @Nullable
    public CatRespawnData remove(UUID uuid) {
        if (this.respawnDataMap.containsKey(uuid)) {
            CatRespawnData storage = this.respawnDataMap.remove(uuid);

            // Mark dirty so changes are saved
            this.setDirty();
            return storage;
        }

        return null;
    }

    @Nullable
    public CatRespawnData putData(CatEntity catIn) {
        UUID uuid = catIn.getUUID();

        CatRespawnData storage = new CatRespawnData(this, uuid);
        storage.populate(catIn);

        this.respawnDataMap.put(uuid, storage);
        // Mark dirty so changes are saved
        this.setDirty();
        return storage;
    }

    public Set<UUID> getAllUUID() {
        return Collections.unmodifiableSet(this.respawnDataMap.keySet());
    }

    public Collection<CatRespawnData> getAll() {
        return Collections.unmodifiableCollection(this.respawnDataMap.values());
    }

    public static CatRespawnStorage load(CompoundTag nbt) {
        CatRespawnStorage store = new CatRespawnStorage();
        store.respawnDataMap.clear();

        ListTag list = nbt.getList("respawnData", Tag.TAG_COMPOUND);

        for (int i = 0; i < list.size(); ++i) {
            CompoundTag respawnCompound = list.getCompound(i);

            UUID uuid = NBTUtil.getUniqueId(respawnCompound, "uuid");
            CatRespawnData respawnData = new CatRespawnData(store, uuid);
            respawnData.read(respawnCompound);

            if (uuid == null) {
                CatHerder.LOGGER.info("Failed to load cat respawn data. Please report to mod author...");
                CatHerder.LOGGER.info(respawnData);
                continue;
            }

            store.respawnDataMap.put(uuid, respawnData);
        }

        return store;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        ListTag list = new ListTag();

        for (Map.Entry<UUID, CatRespawnData> entry : this.respawnDataMap.entrySet()) {
            CompoundTag respawnCompound = new CompoundTag();

            CatRespawnData respawnData = entry.getValue();
            NBTUtil.putUniqueId(respawnCompound, "uuid", entry.getKey());
            respawnData.write(respawnCompound);

            list.add(respawnCompound);
        }

        compound.put("respawnData", list);

        return compound;
    }

}
