package com.sweetrpg.catherder.data;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CHItemTagsProvider extends ItemTagsProvider {

    public CHItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providers, TagsProvider<Block> tagProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, providers, tagProvider.contentsGetter(), CatHerderAPI.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "CatHerder Item Tags";
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        createTag(ModTags.BEG_ITEMS_TAMED, ModItems.BREEDING_TREAT,
                  ModItems.CAT_TOY,
                () -> Items.STRING);
        appendToTag(ModTags.TREATS);
        createTag(ModTags.BEG_ITEMS_UNTAMED, ModItems.TRAINING_TREAT, () -> Items.STRING);
        createTag(ModTags.BREEDING_ITEMS, ModItems.BREEDING_TREAT);
        createTag(ModTags.PACK_CAT_BLACKLIST, ModItems.CAT_TOY);
        createTag(ModTags.TREATS, ModItems.TRAINING_TREAT, ModItems.SUPER_TREAT, ModItems.MASTER_TREAT,
                ModItems.WILD_TREAT);
        createTag(ModTags.MEAT, () -> Items.BEEF, () -> Items.COOKED_BEEF,
                () -> Items.CHICKEN, () -> Items.COOKED_CHICKEN,
                () -> Items.PORKCHOP,() ->  Items.COOKED_PORKCHOP,
                () -> Items.MUTTON, () -> Items.COOKED_MUTTON,
                () -> Items.COOKED_RABBIT, () -> Items.RABBIT);
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
