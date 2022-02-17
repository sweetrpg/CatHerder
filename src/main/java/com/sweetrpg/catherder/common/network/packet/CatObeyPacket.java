package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.network.packet.data.CatObeyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class CatObeyPacket extends CatPacket<CatObeyData> {

    @Override
    public void encode(CatObeyData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.obeyOthers);
    }

    @Override
    public CatObeyData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean obeyOthers = buf.readBoolean();
        return new CatObeyData(entityId, obeyOthers);
    }

    @Override
    public void handleCat(CatEntity catIn, CatObeyData data, Supplier<Context> ctx) {
        if (!catIn.canInteract(ctx.get().getSender())) {
            return;
        }

        catIn.setWillObeyOthers(data.obeyOthers);
    }
}
