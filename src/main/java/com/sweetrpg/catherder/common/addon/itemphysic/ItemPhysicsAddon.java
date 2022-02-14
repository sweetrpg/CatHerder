package com.sweetrpg.catherder.common.addon.itemphysic;

import com.google.common.collect.Lists;
import com.sweetrpg.catherder.CatBlocks;
import com.sweetrpg.catherder.CatItems;
import com.sweetrpg.catherder.common.addon.Addon;
import com.sweetrpg.catherder.common.util.ReflectionUtil;

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
                                          CatItems.BREEDING_TREAT, CatItems.DIRE_TREAT, CatItems.MASTER_TREAT,
                                          CatItems.SUPER_TREAT, CatItems.TRAINING_TREAT, CatItems.COLLAR_SHEARS,
//                                          CatItems.THROW_BONE,
                                          CatItems.WOOL_COLLAR, CatItems.TREAT_BAG,
                                          CatItems.CAT_TOY);

        ReflectionUtil.invokeStaticMethod(addMethod, burningItems,
                                          CatBlocks.CAT_BED, CatItems.BREEDING_TREAT, CatItems.DIRE_TREAT,
                                          CatItems.MASTER_TREAT, CatItems.SUPER_TREAT, CatItems.TRAINING_TREAT,
                                          CatItems.COLLAR_SHEARS,
//                                          CatItems.THROW_BONE,
                                          CatItems.RADAR,
                                          CatItems.WOOL_COLLAR, CatItems.TREAT_BAG, CatItems.CAT_TOY);
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
