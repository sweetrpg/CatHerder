package kittytalents.client;

import kittytalents.KittyContainerTypes;
import kittytalents.KittyEntityTypes;
import kittytalents.KittyTileEntityTypes;
import kittytalents.client.entity.render.CollarRenderManager;
import kittytalents.client.entity.render.CatScreenOverlays;
import kittytalents.client.entity.render.CatRenderer;
import kittytalents.client.entity.render.KittyBeamRenderer;
import kittytalents.client.entity.render.layer.PackKittyRenderer;
import kittytalents.client.entity.render.layer.RescueCatRenderer;
import kittytalents.client.entity.render.layer.accessory.ArmorAccessoryRenderer;
import kittytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import kittytalents.client.screen.CatInventoriesScreen;
import kittytalents.client.screen.FoodBowlScreen;
import kittytalents.client.screen.PackKittyScreen;
import kittytalents.client.screen.TreatBagScreen;
import kittytalents.client.tileentity.renderer.CatBedRenderer;
import kittytalents.common.lib.Constants;
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
        MenuScreens.register(KittyContainerTypes.FOOD_BOWL.get(), FoodBowlScreen::new);
        MenuScreens.register(KittyContainerTypes.PACK_KITTY.get(), PackKittyScreen::new);
        MenuScreens.register(KittyContainerTypes.TREAT_BAG.get(), TreatBagScreen::new);
        MenuScreens.register(KittyContainerTypes.CAT_INVENTORIES.get(), CatInventoriesScreen::new);
    }

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CAT, kittytalents.client.entity.model.CatModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ARMOR, kittytalents.client.entity.model.CatModel::createArmorLayer);
        event.registerLayerDefinition(DOG_BACKPACK, kittytalents.client.entity.model.CatBackpackModel::createChestLayer);
        event.registerLayerDefinition(DOG_RESCUE_BOX, kittytalents.client.entity.model.CatRescueModel::createRescueBoxLayer);
        // TODO: RenderingRegistry.registerEntityRenderingHandler(KittyEntityTypes.DOG_BEAM.get(), manager -> new KittyBeamRenderer<>(manager, event.getMinecraftSupplier().get().getItemRenderer()));
    }

    public static void setupTileEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(KittyEntityTypes.CAT.get(), CatRenderer::new);
        event.registerEntityRenderer(KittyEntityTypes.DOG_BEAM.get(), KittyBeamRenderer::new);
        event.registerBlockEntityRenderer(KittyTileEntityTypes.CAT_BED.get(), CatBedRenderer::new);
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
