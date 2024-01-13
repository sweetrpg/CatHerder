package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.common.Screens;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.network.IPacket;
import com.sweetrpg.catherder.common.network.packet.data.OpenCatScreenData;
import com.sweetrpg.catherder.common.talent.PackCatTalent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.List;
import java.util.function.Supplier;

public class OpenCatScreenPacket implements IPacket<OpenCatScreenData>  {

    @Override
    public OpenCatScreenData decode(FriendlyByteBuf buf) {
        return new OpenCatScreenData();
    }


    @Override
    public void encode(OpenCatScreenData data, FriendlyByteBuf buf) {

    }

    @Override
    public void handle(OpenCatScreenData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
                ServerPlayer player = ctx.get().getSender();
                List<CatEntity> cats = player.level.getEntitiesOfClass(CatEntity.class, player.getBoundingBox().inflate(12D, 12D, 12D),
                                                                       (cat) -> cat.canInteract(player) && PackCatTalent.hasInventory(cat)
                );
				if (!cats.isEmpty()) {
				    Screens.openCatInventoriesScreen(player, cats);
				}
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
