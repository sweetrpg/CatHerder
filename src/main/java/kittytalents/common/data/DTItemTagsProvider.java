package kittytalents.common.data;

import kittytalents.KittyItems;
import kittytalents.KittyTags;
import kittytalents.common.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.function.Supplier;

public class DTItemTagsProvider extends ItemTagsProvider {

    public DTItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "KittyTalents Item Tags";
    }

    @Override
    public void addTags() {
        createTag(KittyTags.BEG_ITEMS_TAMED, KittyItems.BREEDING_BONE, KittyItems.THROW_STICK, KittyItems.THROW_BONE, Items.BONE.delegate);
        appendToTag(KittyTags.TREATS);
        createTag(KittyTags.BEG_ITEMS_UNTAMED, KittyItems.TRAINING_TREAT, Items.BONE.delegate);
        createTag(KittyTags.BREEDING_ITEMS, KittyItems.BREEDING_BONE);
        createTag(KittyTags.PACK_PUPPY_BLACKLIST, KittyItems.THROW_BONE, KittyItems.THROW_BONE_WET, KittyItems.THROW_STICK, KittyItems.THROW_STICK_WET);
        createTag(KittyTags.TREATS, KittyItems.TRAINING_TREAT, KittyItems.SUPER_TREAT, KittyItems.MASTER_TREAT, KittyItems.DIRE_TREAT);
    }

    @SafeVarargs
    private final void createTag(Tag.Named<Item> tag, Supplier<? extends ItemLike>... items) {
        tag(tag).add(Arrays.stream(items).map(Supplier::get).map(ItemLike::asItem).toArray(Item[]::new));
    }

    @SafeVarargs
    private final void appendToTag(Tag.Named<Item> tag, Tag.Named<Item>... toAppend) {
        tag(tag).addTags(toAppend);
    }
}
