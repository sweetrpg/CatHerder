package kittytalents.common.addon.jei;

import kittytalents.KittyBlocks;
import kittytalents.api.registry.IBeddingMaterial;
import kittytalents.api.registry.ICasingMaterial;
import kittytalents.common.util.CatBedUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

@JeiPlugin
public class DTPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ModIds.JEI_ID, "kittytalents");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(KittyBlocks.CAT_BED.get().asItem(), (stack, ctx) -> {
            Pair<ICasingMaterial, IBeddingMaterial> materials = CatBedUtil.getMaterials(stack);

            String casingKey = materials.getLeft() != null
                    ? materials.getLeft().getRegistryName().toString()
                    : "kittytalents:casing_missing";

            String beddingKey = materials.getRight() != null
                    ? materials.getRight().getRegistryName().toString()
                    : "kittytalents:bedding_missing";

            return casingKey + "+" + beddingKey;
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CatBedRecipeMaker.createCatbedRecipes(), VanillaRecipeCategoryUid.CRAFTING);
    }
}
