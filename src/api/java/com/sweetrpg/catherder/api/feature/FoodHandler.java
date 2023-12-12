package com.sweetrpg.catherder.api.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatFoodHandler;
import com.sweetrpg.catherder.api.inferface.ICatFoodPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class FoodHandler {

    private static final List<ICatFoodHandler> HANDLERS = Collections.synchronizedList(new ArrayList<>(4));
    private static final List<ICatFoodPredicate> DYN_PREDICATES = Collections.synchronizedList(new ArrayList<>(2));

    public static void registerHandler(ICatFoodHandler handler) {
        HANDLERS.add(handler);
    }

    public static void registerDynPredicate(ICatFoodPredicate handler) {
        DYN_PREDICATES.add(handler);
    }

    public static Optional<ICatFoodPredicate> isFood(ItemStack stackIn) {
        for (ICatFoodPredicate predicate : DYN_PREDICATES) {
            if (predicate.isFood(stackIn)) {
                return Optional.of(predicate);
            }
        }

        if (stackIn.getItem() instanceof ICatFoodHandler) {
            if (((ICatFoodHandler) stackIn.getItem()).isFood(stackIn)) {
                return Optional.of((ICatFoodHandler) stackIn.getItem());
            }
        }

        for (ICatFoodHandler handler : HANDLERS) {
            if (handler.isFood(stackIn)) {
                return Optional.of(handler);
            }
        }

        return Optional.empty();
    }

    public static Optional<ICatFoodHandler> getMatch(@Nullable AbstractCatEntity catIn, ItemStack stackIn, @Nullable Entity entityIn) {
        for (ICatFoodHandler handler : catIn.getFoodHandlers()) {
            if (handler.canConsume(catIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }

        if (stackIn.getItem() instanceof ICatFoodHandler) {
            if (((ICatFoodHandler) stackIn.getItem()).canConsume(catIn, stackIn, entityIn)) {
                return Optional.of((ICatFoodHandler) stackIn.getItem());
            }
        }

        for (ICatFoodHandler handler : HANDLERS) {
            if (handler.canConsume(catIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }

        return Optional.empty();
    }
}
