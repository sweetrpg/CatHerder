package kittytalents.common.data;

import kittytalents.common.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DTBlockTagsProvider extends BlockTagsProvider {

    public DTBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "KittyTalents Block Tags";
    }

    @Override
    protected void addTags() {

    }
}
