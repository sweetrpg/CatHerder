package com.sweetrpg.catherder.client;

import com.sweetrpg.catherder.CatContainerTypes;
import com.sweetrpg.catherder.CatEntityTypes;
import com.sweetrpg.catherder.CatTileEntityTypes;
import com.sweetrpg.catherder.client.entity.model.CatBackpackModel;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.client.entity.model.CatRescueModel;
import com.sweetrpg.catherder.client.entity.render.CollarRenderManager;
import com.sweetrpg.catherder.client.entity.render.CatScreenOverlays;
import com.sweetrpg.catherder.client.entity.render.CatRenderer;
import com.sweetrpg.catherder.client.entity.render.CatBeamRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.PackKittyRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.RescueCatRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.accessory.ArmorAccessoryRenderer;
import com.sweetrpg.catherder.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import com.sweetrpg.catherder.client.screen.CatInventoriesScreen;
import com.sweetrpg.catherder.client.screen.FoodBowlScreen;
import com.sweetrpg.catherder.client.screen.PackKittyScreen;
import com.sweetrpg.catherder.client.screen.TreatBagScreen;
import com.sweetrpg.catherder.client.tileentity.renderer.CatBedRenderer;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static final ModelLayerLocation CAT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "cat"), "main");
    public static final ModelLayerLocation DOG_ARMOR = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "cat"), "armor");
    public static final ModelLayerLocation DOG_BACKPACK = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_backpack"), "main");
    public static final ModelLayerLocation DOG_RESCUE_BOX = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_rescue_box"), "main");
    public static final ModelLayerLocation DOG_BEAM = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "cat"), "main");

    public static void setupScreenManagers(final FMLClientSetupEvent event) {
        MenuScreens.register(CatContainerTypes.FOOD_BOWL.get(), FoodBowlScreen::new);
        MenuScreens.register(CatContainerTypes.PACK_CAT.get(), PackKittyScreen::new);
        MenuScreens.register(CatContainerTypes.TREAT_BAG.get(), TreatBagScreen::new);
        MenuScreens.register(CatContainerTypes.CAT_INVENTORIES.get(), CatInventoriesScreen::new);
    }

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CAT, CatModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ARMOR, CatModel::createArmorLayer);
        event.registerLayerDefinition(DOG_BACKPACK, CatBackpackModel::createChestLayer);
        event.registerLayerDefinition(DOG_RESCUE_BOX, CatRescueModel::createRescueBoxLayer);
        // TODO: RenderingRegistry.registerEntityRenderingHandler(KittyEntityTypes.DOG_BEAM.get(), manager -> new KittyBeamRenderer<>(manager, event.getMinecraftSupplier().get().getItemRenderer()));
    }

    public static void setupTileEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CatEntityTypes.CAT.get(), CatRenderer::new);
        event.registerEntityRenderer(CatEntityTypes.DOG_BEAM.get(), CatBeamRenderer::new);
        event.registerBlockEntityRenderer(CatTileEntityTypes.CAT_BED.get(), CatBedRenderer::new);
    }

    public static void setupCollarRenderers(final FMLClientSetupEvent event) {
        CollarRenderManager.registerLayer(DefaultAccessoryRenderer::new);
        CollarRenderManager.registerLayer(ArmorAccessoryRenderer::new);
        CollarRenderManager.registerLayer(PackKittyRenderer::new);
        CollarRenderManager.registerLayer(RescueCatRenderer::new);

        OverlayRegistry.registerOverlayTop("Cat Food Level", CatScreenOverlays.FOOD_LEVEL_ELEMENT);
        OverlayRegistry.registerOverlayTop("Cat Air Level", CatScreenOverlays.AIR_LEVEL_ELEMENT);
    }

    public static void addClientReloadListeners(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(CatTextureManager.INSTANCE);
    }
}
