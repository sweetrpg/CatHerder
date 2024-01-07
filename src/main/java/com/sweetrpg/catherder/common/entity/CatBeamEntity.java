package com.sweetrpg.catherder.common.entity;

import com.google.common.base.Predicates;
import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.util.EntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import java.util.UUID;

public class CatBeamEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {

    public CatBeamEntity(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public CatBeamEntity(Level worldIn, LivingEntity livingEntityIn) {
        super(ModEntityTypes.CAT_BEAM.get(), livingEntityIn, worldIn);
    }

    public CatBeamEntity(PlayMessages.SpawnEntity packet, Level worldIn) {
        super(ModEntityTypes.CAT_BEAM.get(), worldIn);
    }

    @Override
    protected void onHit(HitResult result) {
        if(result.getType() == HitResult.Type.ENTITY) {
            Entity entityHit = ((EntityHitResult) result).getEntity();

            Entity thrower = this.getOwner();

            if(thrower instanceof LivingEntity && entityHit instanceof LivingEntity) {
                LivingEntity livingThrower = (LivingEntity) thrower;
                LivingEntity livingEntity = (LivingEntity) entityHit;

                this.level.getEntitiesOfClass(CatEntity.class, this.getBoundingBox().inflate(64D, 16D, 64D)).stream()
                        .filter(Predicates.not(CatEntity::isInSittingPose))
                        .filter(d -> d.isMode(Mode.ATTACK, Mode.TACTICAL))
                        .filter(d -> d.canInteract(livingThrower))
                        .filter(d -> d != livingEntity && d.wantsToAttack(livingEntity, d.getOwner()))
                        .filter(d -> d.distanceTo(entityHit) < EntityUtil.getFollowRange(d))
                        .forEach(d -> d.setTarget(livingEntity));
            }

            for(int j = 0; j < 8; ++j) {
                this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

        if(!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, Constants.EntityState.DEATH);
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        UUID ownerId = this.uuid;
        buffer.writeBoolean(ownerId != null);
        if(ownerId != null) {
            buffer.writeUUID(ownerId);
        }
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        boolean hasThrower = buffer.readBoolean();
        if(hasThrower) {
            this.uuid = buffer.readUUID();
        }
    }

    @Override
    protected void defineSynchedData() {
        // TODO Auto-generated method stub

    }
}
