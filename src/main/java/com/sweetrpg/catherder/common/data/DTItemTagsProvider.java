package com.sweetrpg.catherder.common.data;

import com.sweetrpg.catherder.CatItems;
import com.sweetrpg.catherder.CatTags;
import com.sweetrpg.catherder.common.lib.Constants;
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
        return "CatHerder Item Tags";
    }

    @Override
    public void addTags() {
        createTag(CatTags.BEG_ITEMS_TAMED, CatItems.BREEDING_TREAT,
//                  CatItems.THROW_STICK, CatItems.THROW_BONE,
                  Items.BONE.delegate);
        appendToTag(CatTags.TREATS);
        createTag(CatTags.BEG_ITEMS_UNTAMED, CatItems.TRAINING_TREAT, Items.BONE.delegate);
        createTag(CatTags.BREEDING_ITEMS, CatItems.BREEDING_TREAT);
        createTag(CatTags.PACK_CAT_BLACKLIST /*, CatItems.THROW_BONE, CatItems.THROW_BONE_WET, CatItems.THROW_STICK, CatItems.THROW_STICK_WET */);
        createTag(CatTags.TREATS, CatItems.TRAINING_TREAT, CatItems.SUPER_TREAT, CatItems.MASTER_TREAT, CatItems.DIRE_TREAT);
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
