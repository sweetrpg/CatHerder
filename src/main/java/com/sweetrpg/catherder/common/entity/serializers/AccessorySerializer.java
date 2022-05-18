package com.sweetrpg.catherder.common.entity.serializers;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.Accessory;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.ArrayList;
import java.util.List;

public class AccessorySerializer implements EntityDataSerializer<List<AccessoryInstance>> {

    @Override
    public void write(FriendlyByteBuf buf, List<AccessoryInstance> value) {
        buf.writeInt(value.size());

        for (AccessoryInstance inst : value) {
            buf.writeRegistryIdUnsafe(CatHerderAPI.ACCESSORIES.get(), inst.getAccessory());
            inst.getAccessory().write(inst, buf);
        }
    }

    @Override
    public List<AccessoryInstance> read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<AccessoryInstance> newInst = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Accessory type = buf.readRegistryIdUnsafe(CatHerderAPI.ACCESSORIES.get());
            newInst.add(type.createInstance(buf));
        }

        return newInst;
    }

    @Override
    public List<AccessoryInstance> copy(List<AccessoryInstance> value) {
        List<AccessoryInstance> newInst = new ArrayList<>(value.size());

        for (AccessoryInstance inst : value) {
            newInst.add(inst.copy());
        }

        return newInst;
    }

}
