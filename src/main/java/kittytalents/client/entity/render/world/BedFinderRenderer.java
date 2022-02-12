package kittytalents.client.entity.render.world;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import kittytalents.KittyTalents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelLastEvent;

import java.util.Optional;

public class BedFinderRenderer {

    public static void onWorldRenderLast(RenderLevelLastEvent event) {
        Player player = Minecraft.getInstance().player;
        for (Entity passenger : player.getPassengers()) {
            if (passenger instanceof kittytalents.common.entity.CatEntity) {
                kittytalents.common.entity.CatEntity cat = (kittytalents.common.entity.CatEntity) passenger;
                Optional<BlockPos> bedPosOpt = cat.getBedPos();

                if (bedPosOpt.isPresent()) {
                    BlockPos bedPos = bedPosOpt.get();
                    int level = cat.getDogLevel(KittyTalents.BED_FINDER);
                    double distance = (level * 200D) - Math.sqrt(bedPos.distSqr(cat.blockPosition()));
                    if (level == 5 || distance >= 0.0D) {
                        PoseStack stack = event.getPoseStack();

                        AABB boundingBox = new AABB(bedPos).inflate(0.5D);
                        drawSelectionBox(stack, boundingBox);
                    }
                }
            }
        }
    }

    public static void drawSelectionBox(PoseStack stack, AABB boundingBox) {
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest(); //Make the line see thought blocks
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // TODO: This line has no effect RenderSystem.lineWidth(2.0F);

        RenderSystem.disableTexture();
        Vec3 vec3d = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double d0 = vec3d.x();
        double d1 = vec3d.y();
        double d2 = vec3d.z();

        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        LevelRenderer.renderLineBox(stack, buf, boundingBox.move(-d0, -d1, -d2), 1F, 1F, 0, 1F);
        buf.end();
        BufferUploader.end(buf);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest(); //Make the line see thought blocks
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
