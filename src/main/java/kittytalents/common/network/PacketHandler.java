package kittytalents.common.network;

import kittytalents.KittyTalents2;
import kittytalents.common.network.packet.*;
import kittytalents.common.network.packet.data.*;
import net.minecraftforge.network.PacketDistributor;

public final class PacketHandler {

    private static int disc = 0;

    public static void init() {
        registerPacket(new CatModePacket(), CatModeData.class);
        registerPacket(new CatNamePacket(), CatNameData.class);
        registerPacket(new CatObeyPacket(), CatObeyData.class);
        registerPacket(new CatTalentPacket(), CatTalentData.class);
        //registerPacket(new DogTexturePacket(), DogTextureData.class);
        registerPacket(new FriendlyFirePacket(), FriendlyFireData.class);
        registerPacket(new SendSkinPacket(), SendSkinData.class);
        registerPacket(new RequestSkinPacket(), RequestSkinData.class);
        registerPacket(new OpenCatScreenPacket(), OpenCatScreenData.class);
        registerPacket(new CatInventoryPagePacket(), CatInventoryPageData.class);
        registerPacket(new CatTexturePacket(), CatTextureData.class);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        KittyTalents2.HANDLER.send(target, message);
    }

    public static <D> void registerPacket(IPacket<D> packet, Class<D> dataClass) {
        KittyTalents2.HANDLER.registerMessage(PacketHandler.disc++, dataClass, packet::encode, packet::decode, packet::handle);
    }
}
