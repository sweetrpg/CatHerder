package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.common.inventory.container.CatInventoriesContainer;
import com.sweetrpg.catherder.common.network.IPacket;
import com.sweetrpg.catherder.common.network.packet.data.CatInventoryPageData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class CatInventoryPagePacket implements IPacket<CatInventoryPageData>  {

    @Override
    public CatInventoryPageData decode(FriendlyByteBuf buf) {
        return new CatInventoryPageData(buf.readInt());
    }


    @Override
    public void encode(CatInventoryPageData data, FriendlyByteBuf buf) {
        buf.writeInt(data.page);
    }

    @Override
    public void handle(CatInventoryPageData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
                ServerPlayer player = ctx.get().getSender();
                AbstractContainerMenu container = player.containerMenu;
                if (container instanceof CatInventoriesContainer) {
                    CatInventoriesContainer inventories = (CatInventoriesContainer) container;
                    int page = Mth.clamp(data.page, 0, Math.max(0, inventories.getTotalNumColumns() - 9));

                    inventories.setPage(page);
                    inventories.setData(0, page);
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }

}
