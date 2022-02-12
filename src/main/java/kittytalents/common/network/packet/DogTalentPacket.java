package kittytalents.common.network.packet;

import kittytalents.KittyTalents2;
import kittytalents.api.KittyTalentsAPI;
import kittytalents.api.registry.Talent;
import kittytalents.common.config.ConfigHandler;
import kittytalents.common.network.packet.data.DogTalentData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogTalentPacket extends DogPacket<DogTalentData> {

    @Override
    public void encode(DogTalentData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeRegistryIdUnsafe(KittyTalentsAPI.TALENTS, data.talent);
    }

    @Override
    public DogTalentData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Talent talent = buf.readRegistryIdUnsafe(KittyTalentsAPI.TALENTS);
        return new DogTalentData(entityId, talent);
    }

    @Override
    public void handleDog(kittytalents.common.entity.CatEntity dogIn, DogTalentData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        if (!ConfigHandler.TALENT.getFlag(data.talent)) {
            KittyTalents2.LOGGER.info("{} tried to level a disabled talent ({})",
                    ctx.get().getSender().getGameProfile().getName(),
                    data.talent.getRegistryName());
            return;
        }

        int level = dogIn.getDogLevel(data.talent);

        if (level < data.talent.getMaxLevel() && dogIn.canSpendPoints(data.talent.getLevelCost(level + 1))) {
            dogIn.setTalentLevel(data.talent, level + 1);
        }
    }
}
