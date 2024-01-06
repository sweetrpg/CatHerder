package com.sweetrpg.catherder.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.sweetrpg.catherder.client.block.model.CatTreeModel;
import com.sweetrpg.catherder.client.block.model.PetDoorModel;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.client.screen.widget.CatInventoryButton;
import com.sweetrpg.catherder.common.network.PacketHandler;
import com.sweetrpg.catherder.common.network.packet.data.OpenCatScreenData;
//import com.sweetrpg.catherder.client.block.model.CattreeModel;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class ClientEventHandler {

    public static void onModelBakeEvent(final ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModelRegistry();

        // cat tree
        try {
            ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(ModBlocks.CAT_TREE.get());
            ResourceLocation unbakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());

            BlockModel model = (BlockModel) event.getModelLoader().getModel(unbakedModelLoc);
            BakedModel customModel = new CatTreeModel(event.getModelLoader(), model, model.bake(event.getModelLoader(), model, ForgeModelBakery.defaultTextureGetter(), BlockModelRotation.X180_Y180, unbakedModelLoc, true));

            // Replace all valid block states
            ModBlocks.CAT_TREE.get().getStateDefinition().getPossibleStates().forEach(state -> {
                modelRegistry.put(BlockModelShaper.stateToModelLocation(state), customModel);
            });

            // Replace inventory model
            modelRegistry.put(new ModelResourceLocation(resourceLocation, "inventory"), customModel);
        }
        catch(Exception e) {
            CatHerder.LOGGER.warn("Could not get base Cat Tree model. Reverting to default textures...");
            e.printStackTrace();
        }

        // pet door
        try {
            ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(ModBlocks.PET_DOOR.get());
            ResourceLocation unbakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());

            BlockModel model = (BlockModel) event.getModelLoader().getModel(unbakedModelLoc);
            BakedModel customModel = new PetDoorModel(event.getModelLoader(), model, model.bake(event.getModelLoader(), model, ForgeModelBakery.defaultTextureGetter(), BlockModelRotation.X180_Y180, unbakedModelLoc, true));

            // Replace all valid block states
            ModBlocks.PET_DOOR.get().getStateDefinition().getPossibleStates().forEach(state -> {
                modelRegistry.put(BlockModelShaper.stateToModelLocation(state), customModel);
            });

            // Replace inventory model
            modelRegistry.put(new ModelResourceLocation(resourceLocation, "inventory"), customModel);
        }
        catch(Exception e) {
            CatHerder.LOGGER.warn("Could not get base Cat Tree model. Reverting to default textures...");
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onInputEvent(final MovementInputUpdateEvent event) {
        if (event.getInput().jumping) {
            Entity entity = event.getPlayer().getVehicle();
            if (event.getPlayer().isPassenger() && entity instanceof CatEntity) {
                CatEntity cat = (CatEntity) entity;

                if (cat.canJump()) {
                    cat.setJumpPower(100);
                }
            }
        }
    }

    @SubscribeEvent
    public void onScreenInit(final ScreenEvent.InitScreenEvent.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            boolean creative = screen instanceof CreativeModeInventoryScreen;
            boolean dtLoaded = ModList.get().isLoaded("doggytalents");
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            int sizeX = creative ? 195 : 176;
            int sizeY = creative ? 136 : 166;
            int guiLeft = (width - sizeX) / 2 - ((dtLoaded && creative) ? 15 : 0);
            int guiTop = (height - sizeY) / 2 - ((dtLoaded && !creative) ? 13 : 0);

            int x = guiLeft + (creative ? 36 : sizeX / 2 - 10);
            int y = guiTop + (creative ? 7 : 48);

            event.addListener(new CatInventoryButton(x, y, screen, (btn) -> {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenCatScreenData());
                btn.active = false;
            }));
        }
    }

    @SubscribeEvent
    public void onScreenDrawForeground(final ScreenEvent.DrawScreenEvent event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            boolean creative = screen instanceof CreativeModeInventoryScreen;
            CatInventoryButton btn = null;

            //TODO just create a static variable in this class
            for (Widget widget : screen.renderables) {
                if (widget instanceof CatInventoryButton) {
                    btn = (CatInventoryButton) widget;
                    break;
                }
            }

            if (btn.visible && btn.isHoveredOrFocused()) {
                Minecraft mc = Minecraft.getInstance();
                int width = mc.getWindow().getGuiScaledWidth();
                int height = mc.getWindow().getGuiScaledHeight();
                int sizeX = creative ? 195 : 176;
                int sizeY = creative ? 136 : 166;
                int guiLeft = (width - sizeX) / 2;
                int guiTop = (height - sizeY) / 2;
                if (!creative) {
                    RecipeBookComponent recipeBook = ((InventoryScreen) screen).getRecipeBookComponent();
                    if (recipeBook.isVisible()) {
                        guiLeft += 76;
                    }
                }

                //event.getPoseStack().translate(-guiLeft, -guiTop, 0);
                btn.renderToolTip(event.getPoseStack(), event.getMouseX(), event.getMouseY());
                //event.getPoseStack().translate(guiLeft, guiTop, 0);
            }
        }
    }

// TODO Implement patrol item
//    @SubscribeEvent
//    public void onWorldRenderLast(RenderWorldLastEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        PlayerEntity player = mc.player;
//
//        if (player == null || player.getHeldItem(Hand.MAIN_HAND).getItem() != CatItems.PATROL.get()) {
//            return;
//        }
//
//        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
//
//        RenderSystem.pushMatrix();
//
//        if (stack.hasTag() && stack.getTag().contains("patrolPos", Tag.TAG_LIST)) {
//            ListNBT list = stack.getTag().getList("patrolPos", Tag.TAG_COMPOUND);
//            List<BlockPos> poses = new ArrayList<>(list.size());
//            for (int i = 0; i < list.size(); i++) {
//                poses.add(NBTUtil.getBlockPos(list.getCompound(i)));
//            }
//
//            for (BlockPos pos : poses) {
//                this.drawSelectionBox(event.getMatrixStack(), player, event.getPartialTicks(), new AxisAlignedBB(pos));
//            }
//        }
//
//
//        RenderSystem.popMatrix();
//    }

    public void drawSelectionBox(PoseStack matrixStackIn, Player player, float particleTicks, AABB boundingBox) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.7F);
        //TODO Used when drawing outline of bounding box
        RenderSystem.lineWidth(2.0F);


        RenderSystem.disableTexture();
        Vec3 vec3d = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double d0 = vec3d.x();
        double d1 = vec3d.y();
        double d2 = vec3d.z();

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        LevelRenderer.renderLineBox(matrixStackIn, bufferbuilder, boundingBox.move(-d0, -d1, -d2), 1F, 1F, 0F, 0.8F);
        Tesselator.getInstance().end();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.3F);
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        //RenderSystem.enableAlphaTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

//    @SubscribeEvent
//    public void onPreRenderGameOverlay(final RenderGameOverlayEvent.Post event) {
//        // TODO
//        label: if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) {
//            Minecraft mc = Minecraft.getInstance();
//
//            if (mc.player == null || !(mc.player.getVehicle() instanceof CatEntity)) {
//                break label;
//            }
//
//            PoseStack stack = event.getMatrixStack();
//
//            CatEntity cat = (CatEntity) mc.player.getVehicle();
//            int width = mc.getWindow().getGuiScaledWidth();
//            int height = mc.getWindow().getGuiScaledHeight();
//            RenderSystem.pushMatrix();
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION;
//            RenderSystem.enableBlend();
//            int left = width / 2 + 91;
//            int top = height - ((ForgeIngameGui) mc.gui).right_height;
//            ((ForgeIngameGui) mc.gui).right_height += 10;
//            int level = Mth.ceil((cat.getCatHunger() / cat.getMaxHunger()) * 20.0D);
//
//            for (int i = 0; i < 10; ++i) {
//                int idx = i * 2 + 1;
//                int x = left - i * 8 - 9;
//                int y = top;
//                int icon = 16;
//                byte backgound = 12;
//
//                mc.gui.blit(stack, x, y, 16 + backgound * 9, 27, 9, 9);
//
//
//                if (idx < level) {
//                    mc.gui.blit(stack, x, y, icon + 36, 27, 9, 9);
//                } else if (idx == level) {
//                    mc.gui.blit(stack, x, y, icon + 45, 27, 9, 9);
//                }
//            }
//            RenderSystem.disableBlend();
//
//            RenderSystem.enableBlend();
//            left = width / 2 + 91;
//            top = height - ForgeIngameGui.right_height;
//            RenderSystem.color4f(1.0F, 1.0F, 0.0F, 1.0F);
//            int l6 = cat.getAirSupply();
//            int j7 = cat.getMaxAirSupply();
//
//            if (cat.isEyeInFluid(FluidTags.WATER) || l6 < j7) {
//                int air = cat.getAirSupply();
//                int full = Mth.ceil((air - 2) * 10.0D / 300.0D);
//                int partial = Mth.ceil(air * 10.0D / 300.0D) - full;
//
//                for (int i = 0; i < full + partial; ++i) {
//                    mc.gui.blit(stack, left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
//                }
//                ForgeIngameGui.right_height += 10;
//            }
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.disableBlend();
//
//            RenderSystem.popMatrix();
////        }
//    }
}
