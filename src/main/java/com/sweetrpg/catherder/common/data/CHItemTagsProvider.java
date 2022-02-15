package com.sweetrpg.catherder.common.data;

import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.registry.ModTags;
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

public class CHItemTagsProvider extends ItemTagsProvider {

    public CHItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "CatHerder Item Tags";
    }

    @Override
    public void addTags() {
        createTag(ModTags.BEG_ITEMS_TAMED, ModItems.BREEDING_TREAT,
//                  CatItems.THROW_STICK, CatItems.THROW_BONE,
                  Items.BONE.delegate);
        appendToTag(ModTags.TREATS);
        createTag(ModTags.BEG_ITEMS_UNTAMED, ModItems.TRAINING_TREAT, Items.BONE.delegate);
        createTag(ModTags.BREEDING_ITEMS, ModItems.BREEDING_TREAT);
        createTag(ModTags.PACK_CAT_BLACKLIST /*, CatItems.THROW_BONE, CatItems.THROW_BONE_WET, CatItems.THROW_STICK, CatItems.THROW_STICK_WET */);
        createTag(ModTags.TREATS, ModItems.TRAINING_TREAT, ModItems.SUPER_TREAT, ModItems.MASTER_TREAT, ModItems.DIRE_TREAT);
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
