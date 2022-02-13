package kittytalents.common.network.packet;

import kittytalents.common.Screens;
import kittytalents.common.network.IPacket;
import kittytalents.common.network.packet.data.OpenCatScreenData;
import kittytalents.common.talent.PackKittyTalent;
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
                List<kittytalents.common.entity.CatEntity> dogs = player.level.getEntitiesOfClass(kittytalents.common.entity.CatEntity.class, player.getBoundingBox().inflate(12D, 12D, 12D),
                    (cat) -> cat.canInteract(player) && PackKittyTalent.hasInventory(cat)
                );
				if (!dogs.isEmpty()) {
				    Screens.openDogInventoriesScreen(player, dogs);
				}
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
