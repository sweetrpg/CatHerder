package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.network.packet.data.CatTalentData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class CatTalentPacket extends CatPacket<CatTalentData> {

    @Override
    public void encode(CatTalentData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeRegistryIdUnsafe(CatHerderAPI.TALENTS.get(), data.talent);
        buf.writeInt(data.adjustment);
    }

    @Override
    public CatTalentData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Talent talent = buf.readRegistryIdUnsafe(CatHerderAPI.TALENTS.get());
        int adjustment = buf.readInt();
        return new CatTalentData(entityId, talent, adjustment);
    }

    @Override
    public void handleCat(CatEntity catIn, CatTalentData data, Supplier<Context> ctx) {
        if(!catIn.canInteract(ctx.get().getSender())) {
            return;
        }

        if(!ConfigHandler.TALENT.getFlag(data.talent)) {
            CatHerder.LOGGER.info("{} tried to adjust a disabled talent ({})",
                    ctx.get().getSender().getGameProfile().getName(),
                    data.talent.getRegistryName());
            return;
        }

        catIn.adjustTalentLevel(data.talent, data.adjustment);
    }
}
