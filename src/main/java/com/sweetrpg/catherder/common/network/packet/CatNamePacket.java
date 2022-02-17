package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.common.network.packet.data.CatNameData;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class CatNamePacket extends CatPacket<CatNameData> {

    @Override
    public void encode(CatNameData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeUtf(data.name, 64);
    }

    @Override
    public CatNameData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String name = buf.readUtf(64);
        return new CatNameData(entityId, name);
    }

    @Override
    public void handleCat(CatEntity catIn, CatNameData data, Supplier<Context> ctx) {
        if (!catIn.canInteract(ctx.get().getSender())) {
            return;
        }

        if (data.name.isEmpty()) {
            catIn.setCustomName(null);
        }
        else {
            catIn.setCustomName(new TextComponent(data.name));
        }
    }
}
