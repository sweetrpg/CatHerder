package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.util.EntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class NermalTalent extends TalentInstance {

    private int cooldown;

    public NermalTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public TalentInstance copy() {
        NermalTalent inst = new NermalTalent(this.getTalent(), this.level);
        inst.cooldown = this.cooldown;
        return inst;
    }

    @Override
    public void init(AbstractCatEntity catIn) {
        this.cooldown = catIn.tickCount;
    }

    @Override
    public void writeToNBT(AbstractCatEntity catIn, CompoundTag compound) {
        super.writeToNBT(catIn, compound);
        int timeLeft = this.cooldown - catIn.tickCount;
        compound.putInt("cooldown", timeLeft);
    }

    @Override
    public void readFromNBT(AbstractCatEntity catIn, CompoundTag compound) {
        super.readFromNBT(catIn, compound);
        this.cooldown = catIn.tickCount + compound.getInt("cooldown");
    }

    // Left in for backwards compatibility for versions <= 2.0.0.5
    @Override
    public void onRead(AbstractCatEntity catIn, CompoundTag compound) {
        if(compound.contains("charmercharge")) {
            this.cooldown = catIn.tickCount + compound.getInt("charmercharge");
        }
    }

    @Override
    public void livingTick(AbstractCatEntity catIn) {
        if(catIn.tickCount % 40 != 0) {
            return;
        }

        if(catIn.level.isClientSide || !catIn.isTame()) {
            return;
        }

        if(this.level() <= 0) {
            return;
        }
        int timeLeft = this.cooldown - catIn.tickCount;

        if(timeLeft <= 0) {
            LivingEntity owner = catIn.getOwner();

            // Cat doesn't have owner or is offline
            if(owner == null) {
                return;
            }

            LivingEntity villager = this.getClosestVisibleVillager(catIn, 5D);

            if(villager != null) {
                int rewardId = catIn.getRandom().nextInt(this.level()) + (this.level() >= 5 ? 1 : 0);

                if(rewardId == 0) {
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.1.line.1", catIn.getGenderPronoun()), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.1.line.2", catIn.getGenderSubject()), villager.getUUID());
                    villager.spawnAtLocation(Items.PORKCHOP, 2);
                }
                else if(rewardId == 1) {
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.2.line.1", catIn.getGenderTitle()), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.2.line.2", catIn.getGenderTitle()), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.2.line.3", catIn.getGenderTitle()), villager.getUUID());
                    villager.spawnAtLocation(Items.PORKCHOP, 5);
                }
                else if(rewardId == 2) {
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.3.line.1"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.3.line.2"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.3.line.3"), villager.getUUID());
                    villager.spawnAtLocation(Items.IRON_INGOT, 3);
                }
                else if(rewardId == 3) {
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.4.line.1"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.4.line.2"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.4.line.3"), villager.getUUID());
                    villager.spawnAtLocation(Items.GOLD_INGOT, 2);
                }
                else if(rewardId == 4) {
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.5.line.1"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.5.line.2"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.5.line.3"), villager.getUUID());
                    villager.spawnAtLocation(Items.DIAMOND, 1);
                }
                else if(rewardId == 5) {
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.6.line.1"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.6.line.2"), villager.getUUID());
                    owner.sendMessage(new TranslatableComponent("talent.catherder.nermal.msg.6.line.3"), villager.getUUID());
                    villager.spawnAtLocation(Items.APPLE, 1);
                    villager.spawnAtLocation(Blocks.CAKE, 1);
                    villager.spawnAtLocation(Items.SLIME_BALL, 3);
                    villager.spawnAtLocation(Items.PORKCHOP, 5);
                }

                this.cooldown = catIn.tickCount + (this.level() >= 5 ? 24000 : 48000);
            }
        }
    }

    public LivingEntity getClosestVisibleVillager(AbstractCatEntity catIn, double radiusIn) {
        List<AbstractVillager> list = catIn.level.getEntitiesOfClass(
                AbstractVillager.class,
                catIn.getBoundingBox().inflate(radiusIn, radiusIn, radiusIn),
                (village) -> village.hasLineOfSight(catIn)
        );

        return EntityUtil.getClosestTo(catIn, list);
    }
}
