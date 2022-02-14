package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.client.CatTextureManager;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.entity.texture.CatTextureServer;
import com.sweetrpg.catherder.common.network.IPacket;
import com.sweetrpg.catherder.common.network.packet.data.SendSkinData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Supplier;

public class SendSkinPacket implements IPacket<SendSkinData> {

    @Override
    public void encode(SendSkinData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
        buf.writeInt(data.image.length);
        buf.writeBytes(data.image);
    }

    @Override
    public SendSkinData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        byte[] targetArray = new byte[buf.readInt()];
        buf.readBytes(targetArray);
        return new SendSkinData(entityId, targetArray);
    }

    @Override
    public void handle(SendSkinData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LogicalSide side = ctx.get().getDirection().getReceptionSide();
            if (side.isClient()) {
                CatHerder.LOGGER.debug("Client: Received cat texture to save and load");
                String hash = "";
                try {
                    hash = CatTextureManager.INSTANCE.saveTextureAndLoad(CatTextureManager.INSTANCE.getClientFolder(), data.image);

                    CatTextureManager.INSTANCE.setRequestHandled(hash);
                } catch (IOException e) {
                    CatHerder.LOGGER.error("Cat skin failed to load");
                    CatTextureManager.INSTANCE.setRequestFailed(hash);
                }
            } else if (side.isServer()) {
                Entity target = ctx.get().getSender().level.getEntity(data.entityId);
                if (!(target instanceof CatEntity)) {
                    return;
                }

                CatEntity cat = (CatEntity) target;
                if (!cat.canInteract(ctx.get().getSender())) {
                    return;
                }

                try {
                    if (ctx.get().getSender().getServer().isDedicatedServer()) {

                        // Sanitise the data
                        ByteArrayInputStream bis = new ByteArrayInputStream(data.image);
                        BufferedImage bImage2 = ImageIO.read(bis);


                        CatTextureServer.INSTANCE.saveTexture(CatTextureServer.INSTANCE.getServerFolder(), IOUtils.toByteArray(bis));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String hash = CatTextureServer.INSTANCE.getHash(data.image);
                cat.setSkinHash(hash);
            }
        });

        ctx.get().setPacketHandled(true);

    }
}
