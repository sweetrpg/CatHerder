package kittytalents.client.block.model;

import kittytalents.api.registry.IBeddingMaterial;
import kittytalents.api.registry.ICasingMaterial;
import kittytalents.common.util.CatBedUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class CatBedItemOverride extends ItemOverrides {

    @Override
    public BakedModel resolve(BakedModel modelOriginal, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity livingEntity, int p_173469_) {
        if (modelOriginal instanceof CatBedModel) {
            Pair<ICasingMaterial, IBeddingMaterial> materials = CatBedUtil.getMaterials(stack);
            return ((CatBedModel) modelOriginal).getModelVariant(materials.getLeft(), materials.getRight(), Direction.NORTH);
        }

        return modelOriginal;
    }
}
