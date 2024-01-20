package com.sweetrpg.catherder.api.inferface;

import java.util.UUID;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import com.sweetrpg.catherder.api.feature.Gender;
import com.sweetrpg.catherder.api.feature.ICat;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractCatEntity extends TamableAnimal implements ICat {

    protected AbstractCatEntity(EntityType<? extends TamableAnimal> type, Level worldIn) {
        super(type, worldIn);
    }

    public void setAttributeModifier(Attribute attribute, UUID modifierUUID, BiFunction<AbstractCatEntity, UUID, AttributeModifier> modifierGenerator) {
        AttributeInstance attributeInst = this.getAttribute(attribute);

        AttributeModifier currentModifier = attributeInst.getModifier(modifierUUID);

        // Remove modifier if it exists
        if (currentModifier != null) {

            // Use UUID version as it is more efficient since
            // getModifier would need to be called again
            attributeInst.removeModifier(modifierUUID);
        }

        AttributeModifier newModifier = modifierGenerator.apply(this, modifierUUID);

        if (newModifier != null) {
            attributeInst.addTransientModifier(newModifier);
        }
    }

    public void removeAttributeModifier(Attribute attribute, UUID modifierUUID) {
        this.getAttribute(attribute).removeModifier(modifierUUID);
    }

    @Override
    public AbstractCatEntity getCat() {
        return this;
    }

    // Makes the method public
    @Override
    public float getSoundVolume() {
        return super.getSoundVolume();
    }

    @Override
    public void spawnTamingParticles(boolean play) {
        super.spawnTamingParticles(play);
    }

    public void consumeItemFromStack(@Nullable Entity entity, ItemStack stack) {
        if (entity instanceof Player) {
            // Review: super.usePlayerItem((Player) entity, stack);
            stack.shrink(1);
        }
        else {
            stack.shrink(1);
        }
    }

    public abstract Component getTranslationKey(Function<Gender, String> function);

    public Component getGenderPronoun() {
        return this.getTranslationKey(Gender::getUnlocalisedPronoun);
    }

    public Component getGenderSubject() {
        return this.getTranslationKey(Gender::getUnlocalisedSubject);
    }

    public Component getGenderTitle() {
        return this.getTranslationKey(Gender::getUnlocalizedTitle);
    }

    public Component getGenderTip() {
        return this.getTranslationKey(Gender::getUnlocalizedTip);
    }

    public Component getGenderName() {
        return this.getTranslationKey(Gender::getUnlocalizedName);
    }

    public void setNavigation(PathNavigation p) {
        if (this.navigation == p) return;
        this.navigation.stop();
        this.navigation = p;
    }

    //TODO try to replicate the bug and check if moveControl.haveWantedPosition using debug magic
    public void setMoveControl(MoveControl m) {
        breakMoveControl();

        this.moveControl = m;
    }

    public void breakMoveControl() {
        /*
         * Force the MoveControl To Reset :
         * this will set the dog's wanted Position to his current Position
         * which will cause the moveControl to halt movement and reset in the
         * next tick().
         * And then immediately update the moveControl by calling tick() so
         * that everything is resolved before anything else.
         */
        this.moveControl.setWantedPosition(
                this.getX(),
                this.getY(),
                this.getZ(), 1.0
        );
        this.moveControl.tick();

        //Also reset jump just to be sure.
        this.setJumping(false);

        //Also reset accelerations just to be sure.
        this.setSpeed(0.0F);
        this.setXxa(0.0F);
        this.setYya(0.0F);
        this.setZza(0.0F);
    }

    public abstract void resetNavigation();

    public abstract void resetMoveControl();

    public abstract boolean canSwimUnderwater();
}
