package kittytalents.common.talent;

import kittytalents.KittyAttributes;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class MountTalent extends TalentInstance {

    private static final UUID CAT_MOUNT_JUMP = UUID.fromString("7f338124-f223-4630-8515-70ee0bfbc653");

    public MountTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(kittytalents.api.inferface.AbstractCatEntity catIn) {
        catIn.setAttributeModifier(KittyAttributes.JUMP_POWER.get(), CAT_MOUNT_JUMP, this::createSpeedModifier);
    }

    @Override
    public void set(kittytalents.api.inferface.AbstractCatEntity catIn, int level) {
        catIn.setAttributeModifier(KittyAttributes.JUMP_POWER.get(), CAT_MOUNT_JUMP, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(kittytalents.api.inferface.AbstractCatEntity catIn, UUID uuidIn) {
        if (this.level() > 0) {
            double speed = 0.06D * this.level();

            if (this.level() >= 5) {
                speed += 0.04D;
            }

            return new AttributeModifier(uuidIn, "Wolf Mount", speed, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (stack.isEmpty()) { // Held item
            if (catIn.canInteract(playerIn) && this.level() > 0) { // Cat
                if (playerIn.getVehicle() == null && !playerIn.isOnGround()) { // Player
                    if (!catIn.level.isClientSide) {
                        catIn.setOrderedToSit(false);
                        playerIn.setYRot(catIn.getYRot());
                        playerIn.setXRot(catIn.getXRot());
                        playerIn.startRiding(catIn);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void livingTick(kittytalents.api.inferface.AbstractCatEntity cat) {
        if (cat.isVehicle() && cat.getCatHunger() < 1) {
            cat.getControllingPassenger().sendMessage(new TranslatableComponent("talent.kittytalents.mount.exhausted", cat.getName()), cat.getUUID());

            cat.ejectPassengers();
        }
    }

    @Override
    public InteractionResultHolder<Integer> hungerTick(kittytalents.api.inferface.AbstractCatEntity catIn, int hungerTick) {
        if (catIn.isControlledByLocalInstance()) {
            hungerTick += this.level() < 5 ? 3 : 1;
            return InteractionResultHolder.success(hungerTick);
        }

        return InteractionResultHolder.pass(hungerTick);
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(kittytalents.api.inferface.AbstractCatEntity catIn, float distance) {
        if (this.level() >= 5) {
            return InteractionResultHolder.success(distance - 1F);
        }

        return InteractionResultHolder.pass(0F);
    }

    @Override
    public InteractionResult hitByEntity(kittytalents.api.inferface.AbstractCatEntity catIn, Entity entity) {
        // If the attacking entity is riding block
        return catIn.isPassengerOfSameVehicle(entity) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}
