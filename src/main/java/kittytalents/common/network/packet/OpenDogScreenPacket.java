package kittytalents.common.network.packet;

import kittytalents.common.Screens;
import kittytalents.common.network.IPacket;
import kittytalents.common.network.packet.data.OpenDogScreenData;
import kittytalents.common.talent.PackPuppyTalent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.List;
import java.util.function.Supplier;

public class OpenDogScreenPacket implements IPacket<OpenDogScreenData>  {

    @Override
    public OpenDogScreenData decode(FriendlyByteBuf buf) {
        return new OpenDogScreenData();
    }


    @Override
    public void encode(OpenDogScreenData data, FriendlyByteBuf buf) {

    }

    @Override
    public void handle(OpenDogScreenData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
                ServerPlayer player = ctx.get().getSender();
                List<kittytalents.common.entity.CatEntity> dogs = player.level.getEntitiesOfClass(kittytalents.common.entity.CatEntity.class, player.getBoundingBox().inflate(12D, 12D, 12D),
                    (cat) -> cat.canInteract(player) && PackPuppyTalent.hasInventory(cat)
                );
				if (!dogs.isEmpty()) {
				    Screens.openDogInventoriesScreen(player, dogs);
				}
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
