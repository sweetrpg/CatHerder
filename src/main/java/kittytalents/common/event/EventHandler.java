package kittytalents.common.event;

import kittytalents.KittyEntityTypes;
import kittytalents.KittyItems;
import kittytalents.common.config.ConfigHandler;
import kittytalents.common.talent.HunterCatTalent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void rightClickEntity(final PlayerInteractEvent.EntityInteract event) {

        Level world = event.getWorld();

        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        if (target.getType() == EntityType.CAT && target instanceof TamableAnimal && stack.getItem() == KittyItems.TRAINING_TREAT.get()) {
            event.setCanceled(true);

            TamableAnimal vanillaCat = (TamableAnimal) target;

            Player player = event.getPlayer();

            if (vanillaCat.isAlive() && vanillaCat.isTame() && vanillaCat.isOwnedBy(player)) {

                if (!world.isClientSide) {
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    kittytalents.common.entity.CatEntity cat = KittyEntityTypes.CAT.get().create(world);
                    cat.tame(player);
                    cat.setHealth(cat.getMaxHealth());
                    cat.setOrderedToSit(false);
                    cat.setAge(vanillaCat.getAge());
                    cat.absMoveTo(vanillaCat.getX(), vanillaCat.getY(), vanillaCat.getZ(), vanillaCat.getYRot(), vanillaCat.getXRot());

                    world.addFreshEntity(cat);

                    vanillaCat.discard();
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
            } else {
                event.setCancellationResult(InteractionResult.FAIL);
            }
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof AbstractSkeleton) {
            AbstractSkeleton skeleton = (AbstractSkeleton) entity;
            skeleton.goalSelector.addGoal(3, new AvoidEntityGoal<>(skeleton, kittytalents.common.entity.CatEntity.class, 6.0F, 1.0D, 1.2D)); // Same goal as in AbstractSkeletonEntity
        }
    }

    @SubscribeEvent
    public void playerLoggedIn(final PlayerLoggedInEvent event) {
        if (ConfigHandler.SERVER.STARTING_ITEMS.get()) {

            Player player = event.getPlayer();

            CompoundTag tag = player.getPersistentData();

            if (!tag.contains(Player.PERSISTED_NBT_TAG)) {
                tag.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
            }

            CompoundTag persistTag = tag.getCompound(Player.PERSISTED_NBT_TAG);

            if (!persistTag.getBoolean("gotDTStartingItems")) {
                persistTag.putBoolean("gotDTStartingItems", true);

                player.getInventory().add(new ItemStack(KittyItems.KITTY_CHARM.get()));
                player.getInventory().add(new ItemStack(KittyItems.WHISTLE.get()));
            }
        }
    }

    @SubscribeEvent
    public void onLootDrop(final LootingLevelEvent event) {
        HunterCatTalent.onLootDrop(event);
    }
}
