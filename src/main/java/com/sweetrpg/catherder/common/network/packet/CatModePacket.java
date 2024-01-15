package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.network.packet.data.CatModeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class CatModePacket extends CatPacket<CatModeData> {

    @Override
    public void encode(CatModeData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.mode.getIndex());
    }

    @Override
    public CatModeData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int modeIndex = buf.readInt();
        return new CatModeData(entityId, Mode.byIndex(modeIndex));
    }

    @Override
    public void handleCat(CatEntity catIn, CatModeData data, Supplier<Context> ctx) {
        if (!catIn.canInteract(ctx.get().getSender())) {
            return;
        }

        catIn.setMode(data.mode);
    }
}
