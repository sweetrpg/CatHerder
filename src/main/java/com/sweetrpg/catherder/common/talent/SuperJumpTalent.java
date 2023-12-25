package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;


public class SuperJumpTalent extends TalentInstance {

    private static final UUID SUPER_JUMP_BOOST_ID = UUID.fromString("1f002df0-9d35-49c6-a863-b8945caa4af4");

    public SuperJumpTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractCatEntity catIn) {
        catIn.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), SUPER_JUMP_BOOST_ID, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractCatEntity catIn, int level) {
        catIn.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), SUPER_JUMP_BOOST_ID, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(AbstractCatEntity catIn, UUID uuidIn) {
        if(this.level() >= 5) {
            return new AttributeModifier(uuidIn, "Super Jump", -0.065D, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    @Override
    public InteractionResult canTrample(AbstractCatEntity catIn, BlockState state, BlockPos pos, float fallDistance) {
        return this.level() >= 5 ? InteractionResult.FAIL : InteractionResult.PASS;
    }

    @Override
    public InteractionResult onLivingFall(AbstractCatEntity catIn, float distance, float damageMultiplier) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractCatEntity catIn, float distance) {
        if(this.level() > 0) {
            return InteractionResultHolder.success(distance - this.level() * 3);
        }

        return InteractionResultHolder.pass(0F);
    }
}
