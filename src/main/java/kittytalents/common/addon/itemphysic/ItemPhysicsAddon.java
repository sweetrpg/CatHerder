package kittytalents.common.addon.itemphysic;

import com.google.common.collect.Lists;
import kittytalents.KittyBlocks;
import kittytalents.KittyItems;
import kittytalents.common.addon.Addon;
import kittytalents.common.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.Collection;

public class ItemPhysicsAddon implements Addon {

    private static final String className = "team.creative.itemphysic.api.ItemPhysicAPI";

    private static final String methodName = "addSortingObjects";
    private static final Class<?>[] paramTypes = new Class[] {String.class, Object[].class};

    private static final String swimmingItems = "swimmingItems";
    private static final String burningItems = "burningItems";
    private static final String undestroyableItems = "undestroyableItems";
    private static final String ignitingItems = "ignitingItems";

    @Override
    public void exec() {
        Class<?> API_CLASS = ReflectionUtil.getClass(className);

        Method addMethod = ReflectionUtil.getMethod(API_CLASS, methodName, paramTypes);

        ReflectionUtil.invokeStaticMethod(addMethod, swimmingItems,
                KittyItems.BREEDING_BONE, KittyItems.DIRE_TREAT, KittyItems.MASTER_TREAT,
                KittyItems.SUPER_TREAT, KittyItems.TRAINING_TREAT, KittyItems.COLLAR_SHEARS,
                KittyItems.THROW_BONE, KittyItems.WOOL_COLLAR, KittyItems.TREAT_BAG,
                KittyItems.CHEW_STICK);

        ReflectionUtil.invokeStaticMethod(addMethod, burningItems,
                KittyBlocks.CAT_BED, KittyItems.BREEDING_BONE, KittyItems.DIRE_TREAT,
                KittyItems.MASTER_TREAT, KittyItems.SUPER_TREAT,  KittyItems.TRAINING_TREAT,
                KittyItems.COLLAR_SHEARS, KittyItems.THROW_BONE, KittyItems.RADAR,
                KittyItems.WOOL_COLLAR, KittyItems.TREAT_BAG, KittyItems.CHEW_STICK);
    }

    @Override
    public Collection<String> getMods() {
        return Lists.newArrayList("itemphysic");
    }

    @Override
    public String getName() {
        return "Item Physics Addon";
    }
}
