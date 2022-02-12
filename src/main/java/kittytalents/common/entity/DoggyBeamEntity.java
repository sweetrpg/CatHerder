package kittytalents.common.entity;

import com.google.common.base.Predicates;
import kittytalents.KittyEntityTypes;
import kittytalents.api.feature.EnumMode;
import kittytalents.common.lib.Constants;
import kittytalents.common.util.EntityUtil;
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

public class KittyBeamEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {

    public KittyBeamEntity(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public KittyBeamEntity(Level worldIn, LivingEntity livingEntityIn) {
        super(KittyEntityTypes.DOG_BEAM.get(), livingEntityIn, worldIn);
    }

    public KittyBeamEntity(PlayMessages.SpawnEntity packet, Level worldIn) {
        super(KittyEntityTypes.DOG_BEAM.get(), worldIn);
    }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entityHit = ((EntityHitResult) result).getEntity();

            Entity thrower = this.getOwner();

            if (thrower instanceof LivingEntity && entityHit instanceof LivingEntity) {
                LivingEntity livingThrower = (LivingEntity) thrower;
                LivingEntity livingEntity = (LivingEntity) entityHit;

                this.level.getEntitiesOfClass(DogEntity.class, this.getBoundingBox().inflate(64D, 16D, 64D)).stream()
                    .filter(Predicates.not(DogEntity::isInSittingPose))
                    .filter(d -> d.isMode(EnumMode.AGGRESIVE, EnumMode.TACTICAL, EnumMode.BERSERKER))
                    .filter(d -> d.canInteract(livingThrower))
                    .filter(d -> d != livingEntity && d.wantsToAttack(livingEntity, d.getOwner()))
                    .filter(d -> d.distanceTo(entityHit) < EntityUtil.getFollowRange(d))
                    .forEach(d -> d.setTarget(livingEntity));
            }

            for (int j = 0; j < 8; ++j) {
                this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

        if (!this.level.isClientSide) {
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
        if (ownerId != null) {
            buffer.writeUUID(ownerId);
        }
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        boolean hasThrower = buffer.readBoolean();
        if (hasThrower) {
            this.uuid = buffer.readUUID();
        }
    }

    @Override
    protected void defineSynchedData() {
        // TODO Auto-generated method stub

    }
}
