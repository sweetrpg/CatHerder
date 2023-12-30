package com.sweetrpg.catherder.data;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.function.Supplier;

public class CHItemTagsProvider extends ItemTagsProvider {

    public CHItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, CatHerderAPI.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "CatHerder Item Tags";
    }

    @Override
    public void addTags() {
        createTag(ModTags.BEG_ITEMS_TAMED, ModItems.BREEDING_TREAT,
                  ModItems.CAT_TOY,
                  Items.STRING.delegate);
        appendToTag(ModTags.TREATS);
        createTag(ModTags.BEG_ITEMS_UNTAMED, ModItems.TRAINING_TREAT, Items.STRING.delegate);
        createTag(ModTags.BREEDING_ITEMS, ModItems.BREEDING_TREAT);
        createTag(ModTags.PACK_CAT_BLACKLIST, ModItems.CAT_TOY);
        createTag(ModTags.TREATS, ModItems.TRAINING_TREAT, ModItems.SUPER_TREAT, ModItems.MASTER_TREAT,
                ModItems.WILD_TREAT);
        createTag(ModTags.MEAT, Items.BEEF.delegate, Items.COOKED_BEEF.delegate,
                  Items.CHICKEN.delegate, Items.COOKED_CHICKEN.delegate,
                  Items.PORKCHOP.delegate, Items.COOKED_PORKCHOP.delegate,
                  Items.MUTTON.delegate, Items.COOKED_MUTTON.delegate,
                  Items.COOKED_RABBIT.delegate, Items.RABBIT.delegate);
        createTag(ModTags.CAT_TREES, ModBlocks.CAT_TREE);
//        createTag(ModTags.PET_DOORS, ModBlocks.PET_DOOR);
        createTag(ModTags.TOYS, ModItems.CAT_TOY, ModItems.YARN);
    }

    @SafeVarargs
    private final void createTag(TagKey<Item> tag, Supplier<? extends ItemLike>... items) {
        tag(tag).add(Arrays.stream(items).map(Supplier::get).map(ItemLike::asItem).toArray(Item[]::new));
    }

    @SafeVarargs
    private final void appendToTag(TagKey<Item> tag, TagKey<Item>... toAppend) {
        tag(tag).addTags(toAppend);
    }
}
