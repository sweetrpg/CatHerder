package kittytalents.api.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class FoodHandler {

    private static final List<kittytalents.api.inferface.ICatFoodHandler> HANDLERS = Collections.synchronizedList(new ArrayList<>(4));
    private static final List<kittytalents.api.inferface.ICatFoodPredicate> DYN_PREDICATES = Collections.synchronizedList(new ArrayList<>(2));

    public static void registerHandler(kittytalents.api.inferface.ICatFoodHandler handler) {
        HANDLERS.add(handler);
    }

    public static void registerDynPredicate(kittytalents.api.inferface.ICatFoodPredicate handler) {
        DYN_PREDICATES.add(handler);
    }

    public static Optional<kittytalents.api.inferface.ICatFoodPredicate> isFood(ItemStack stackIn) {
        for (kittytalents.api.inferface.ICatFoodPredicate predicate : DYN_PREDICATES) {
            if (predicate.isFood(stackIn)) {
                return Optional.of(predicate);
            }
        }

        if (stackIn.getItem() instanceof kittytalents.api.inferface.ICatFoodHandler) {
            if (((kittytalents.api.inferface.ICatFoodHandler) stackIn.getItem()).isFood(stackIn)) {
                return Optional.of((kittytalents.api.inferface.ICatFoodHandler) stackIn.getItem());
            }
        }

        for (kittytalents.api.inferface.ICatFoodHandler handler : HANDLERS) {
            if (handler.isFood(stackIn)) {
                return Optional.of(handler);
            }
        }

        return Optional.empty();
    }

    public static Optional<kittytalents.api.inferface.ICatFoodHandler> getMatch(@Nullable kittytalents.api.inferface.AbstractCatEntity catIn, ItemStack stackIn, @Nullable Entity entityIn) {
        for (kittytalents.api.inferface.ICatFoodHandler handler : catIn.getFoodHandlers()) {
            if (handler.canConsume(catIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }

        if (stackIn.getItem() instanceof kittytalents.api.inferface.ICatFoodHandler) {
            if (((kittytalents.api.inferface.ICatFoodHandler) stackIn.getItem()).canConsume(catIn, stackIn, entityIn)) {
                return Optional.of((kittytalents.api.inferface.ICatFoodHandler) stackIn.getItem());
            }
        }

        for (kittytalents.api.inferface.ICatFoodHandler handler : HANDLERS) {
            if (handler.canConsume(catIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }

        return Optional.empty();
    }
}
