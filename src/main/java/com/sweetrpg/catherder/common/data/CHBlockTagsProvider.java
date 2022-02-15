package com.sweetrpg.catherder.common.data;

import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CHBlockTagsProvider extends BlockTagsProvider {

    public CHBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "CatHerder Block Tags";
    }

    @Override
    protected void addTags() {
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
    }

    protected void registerForgeTags() {

    }

    protected void registerModTags() {
        tag(ModTags.WILD_CROPS).add(
                ModBlocks.WILD_CATNIP.get());
    }

}
