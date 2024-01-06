package com.sweetrpg.catherder.common.addon.itemphysic;

import com.google.common.collect.Lists;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModItems;
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
                                          ModItems.BREEDING_TREAT, ModItems.WILD_TREAT, ModItems.MASTER_TREAT,
                                          ModItems.SUPER_TREAT, ModItems.TRAINING_TREAT, ModItems.CAT_SHEARS,
//                                          CatItems.THROW_BONE,
                                          ModItems.WOOL_COLLAR, ModItems.TREAT_BAG,
                                          ModItems.CAT_TOY);

        ReflectionUtil.invokeStaticMethod(addMethod, burningItems,
                                          ModBlocks.CAT_TREE, ModItems.BREEDING_TREAT, ModItems.WILD_TREAT,
                                          ModItems.MASTER_TREAT, ModItems.SUPER_TREAT, ModItems.TRAINING_TREAT,
                                          ModItems.CAT_SHEARS,
//                                          CatItems.THROW_BONE,
                                          ModItems.RADAR,
                                          ModItems.WOOL_COLLAR, ModItems.TREAT_BAG /*, ModItems.CAT_TOY */);
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
