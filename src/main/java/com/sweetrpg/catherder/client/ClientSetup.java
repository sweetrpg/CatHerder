package com.sweetrpg.catherder.client;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModContainerTypes;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.client.entity.model.CatBackpackModel;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.client.entity.model.CatRescueModel;
import com.sweetrpg.catherder.client.entity.render.CollarRenderManager;
import com.sweetrpg.catherder.client.entity.render.CatScreenOverlays;
import com.sweetrpg.catherder.client.entity.render.CatRenderer;
//import com.sweetrpg.catherder.client.entity.render.CatBeamRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.PackCatRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.RescueCatRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.accessory.ArmorAccessoryRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import com.sweetrpg.catherder.client.screen.CatInventoriesScreen;
import com.sweetrpg.catherder.client.screen.CatBowlScreen;
import com.sweetrpg.catherder.client.screen.PackCatScreen;
import com.sweetrpg.catherder.client.screen.TreatBagScreen;
//import com.sweetrpg.catherder.client.tileentity.renderer.CattreeRenderer;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static final ModelLayerLocation CAT = new ModelLayerLocation(new ResourceLocation(CatHerderAPI.MOD_ID, "cat"), "main");
    public static final ModelLayerLocation CAT_ARMOR = new ModelLayerLocation(new ResourceLocation(CatHerderAPI.MOD_ID, "cat"), "armor");
    public static final ModelLayerLocation CAT_BACKPACK = new ModelLayerLocation(new ResourceLocation(CatHerderAPI.MOD_ID, "cat_backpack"), "main");
    public static final ModelLayerLocation CAT_RESCUE_BOX = new ModelLayerLocation(new ResourceLocation(CatHerderAPI.MOD_ID, "cat_rescue_box"), "main");
//    public static final ModelLayerLocation CAT_BEAM = new ModelLayerLocation(new ResourceLocation(CatHerderAPI.MOD_ID, "cat"), "main");

    public static void setupScreenManagers(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WILD_CATNIP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CATNIP_CROP.get(), RenderType.cutout());

        MenuScreens.register(ModContainerTypes.CAT_BOWL.get(), CatBowlScreen::new);
        MenuScreens.register(ModContainerTypes.PACK_CAT.get(), PackCatScreen::new);
        MenuScreens.register(ModContainerTypes.TREAT_BAG.get(), TreatBagScreen::new);
        MenuScreens.register(ModContainerTypes.CAT_INVENTORIES.get(), CatInventoriesScreen::new);
    }

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CAT, CatModel::createBodyLayer);
        event.registerLayerDefinition(CAT_ARMOR, CatModel::createArmorLayer);
        event.registerLayerDefinition(CAT_BACKPACK, CatBackpackModel::createChestLayer);
        event.registerLayerDefinition(CAT_RESCUE_BOX, CatRescueModel::createRescueBoxLayer);
        // TODO: RenderingRegistry.registerEntityRenderingHandler(CatEntityTypes.CAT_BEAM.get(), manager -> new CatBeamRenderer<>(manager, event.getMinecraftSupplier().get().getItemRenderer()));
    }

    public static void setupTileEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CAT.get(), CatRenderer::new);
//        event.registerEntityRenderer(ModEntityTypes.CAT_BEAM.get(), CatBeamRenderer::new);
//        event.registerBlockEntityRenderer(ModTileEntityTypes.CAT_TREE.get(), CattreeRenderer::new);
    }

    public static void setupCollarRenderers(final FMLClientSetupEvent event) {
        CollarRenderManager.registerLayer(DefaultAccessoryRenderer::new);
        CollarRenderManager.registerLayer(ArmorAccessoryRenderer::new);
        CollarRenderManager.registerLayer(PackCatRenderer::new);
        CollarRenderManager.registerLayer(RescueCatRenderer::new);

        OverlayRegistry.registerOverlayTop("Cat Food Level", CatScreenOverlays.FOOD_LEVEL_ELEMENT);
        OverlayRegistry.registerOverlayTop("Cat Air Level", CatScreenOverlays.AIR_LEVEL_ELEMENT);
    }

    public static void addClientReloadListeners(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(CatTextureManager.INSTANCE);
    }
}
