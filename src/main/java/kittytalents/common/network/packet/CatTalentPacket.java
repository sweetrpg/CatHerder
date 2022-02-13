package kittytalents.common.network.packet;

import kittytalents.KittyTalents2;
import kittytalents.api.KittyTalentsAPI;
import kittytalents.api.registry.Talent;
import kittytalents.common.config.ConfigHandler;
import kittytalents.common.network.packet.data.CatTalentData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class CatTalentPacket extends CatPacket<CatTalentData> {

    @Override
    public void encode(CatTalentData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeRegistryIdUnsafe(KittyTalentsAPI.TALENTS, data.talent);
    }

    @Override
    public CatTalentData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Talent talent = buf.readRegistryIdUnsafe(KittyTalentsAPI.TALENTS);
        return new CatTalentData(entityId, talent);
    }

    @Override
    public void handleDog(kittytalents.common.entity.CatEntity catIn, CatTalentData data, Supplier<Context> ctx) {
        if (!catIn.canInteract(ctx.get().getSender())) {
            return;
        }

        if (!ConfigHandler.TALENT.getFlag(data.talent)) {
            KittyTalents2.LOGGER.info("{} tried to level a disabled talent ({})",
                    ctx.get().getSender().getGameProfile().getName(),
                    data.talent.getRegistryName());
            return;
        }

        int level = catIn.getCatLevel(data.talent);

        if (level < data.talent.getMaxLevel() && catIn.canSpendPoints(data.talent.getLevelCost(level + 1))) {
            catIn.setTalentLevel(data.talent, level + 1);
        }
    }
}
