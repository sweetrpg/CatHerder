package com.sweetrpg.catherder.common.entity.serializers;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.ArrayList;
import java.util.List;

public class TalentListSerializer implements EntityDataSerializer<List<TalentInstance>> {

    @Override
    public void write(FriendlyByteBuf buf, List<TalentInstance> value) {
        buf.writeInt(value.size());

        for (TalentInstance inst : value) {
            buf.writeRegistryIdUnsafe(CatHerderAPI.TALENTS.get(), inst.getTalent());
            inst.writeToBuf(buf);
        }
    }

    @Override
    public List<TalentInstance> read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<TalentInstance> newInst = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            TalentInstance inst = buf.readRegistryIdUnsafe(CatHerderAPI.TALENTS.get()).getDefault();
            inst.readFromBuf(buf);
            newInst.add(inst);
        }
        return newInst;
    }

    @Override
    public EntityDataAccessor<List<TalentInstance>> createAccessor(int id) {
        return new EntityDataAccessor<>(id, this);
    }

    @Override
    public List<TalentInstance> copy(List<TalentInstance> value) {
        List<TalentInstance> newInst = new ArrayList<>(value.size());

        for (TalentInstance inst : value) {
            newInst.add(inst.copy());
        }

        return newInst;
    }
}
