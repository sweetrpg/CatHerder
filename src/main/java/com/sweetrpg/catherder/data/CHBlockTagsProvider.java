package com.sweetrpg.catherder.data;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CHBlockTagsProvider extends BlockTagsProvider {

    public CHBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CatHerderAPI.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "CatHerder Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerModTags();
        this.registerMinecraftTags();
        this.registerForgeTags();

        this.registerBlockMineables();
    }

    protected void registerBlockMineables() {

    }

    protected void registerMinecraftTags() {
        tag(net.minecraft.tags.BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.WILD_CATNIP.get());
        tag(net.minecraft.tags.BlockTags.CROPS)
                .add(ModBlocks.CATNIP_CROP.get());
    }

    protected void registerForgeTags() {

    }

    protected void registerModTags() {
        tag(ModTags.WILD_CROPS)
                .add(ModBlocks.WILD_CATNIP.get());
    }

}
