package com.sweetrpg.catherder.common.network;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.network.packet.*;
import com.sweetrpg.catherder.common.network.packet.data.*;
import com.sweetrpg.catherder.common.network.packet.data.*;
import com.sweetrpg.catherder.common.network.packet.*;
import com.sweetrpg.catherder.common.network.packet.data.*;
import net.minecraftforge.network.PacketDistributor;

public final class PacketHandler {

    private static int disc = 0;

    public static void init() {
        registerPacket(new CatModePacket(), CatModeData.class);
        registerPacket(new CatNamePacket(), CatNameData.class);
        registerPacket(new CatObeyPacket(), CatObeyData.class);
        registerPacket(new CatTalentPacket(), CatTalentData.class);
        //registerPacket(new CatTexturePacket(), CatTextureData.class);
        registerPacket(new FriendlyFirePacket(), FriendlyFireData.class);
        registerPacket(new SendSkinPacket(), SendSkinData.class);
        registerPacket(new RequestSkinPacket(), RequestSkinData.class);
        registerPacket(new OpenCatScreenPacket(), OpenCatScreenData.class);
        registerPacket(new CatInventoryPagePacket(), CatInventoryPageData.class);
        registerPacket(new CatTexturePacket(), CatTextureData.class);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        CatHerder.HANDLER.send(target, message);
    }

    public static <D> void registerPacket(IPacket<D> packet, Class<D> dataClass) {
        CatHerder.HANDLER.registerMessage(PacketHandler.disc++, dataClass, packet::encode, packet::decode, packet::handle);
    }
}
