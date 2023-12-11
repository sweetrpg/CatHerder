package com.sweetrpg.catherder.api.registry;

import java.util.Optional;
import java.util.function.Supplier;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatAlteration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TalentInstance implements ICatAlteration {

    protected final Talent talentDelegate;

    protected int level;

    public TalentInstance(Talent talentIn) {
        this(talentIn, 1);
    }

    public TalentInstance(Talent talentDelegateIn, int levelIn) {
        this.talentDelegate = talentDelegateIn;
        this.level = levelIn;
    }

    public Talent getTalent() {
        return this.talentDelegate;
    }

    public final int level() {
        return this.level;
    }

    public final void setLevel(int levelIn) {
        this.level = levelIn;
    }

    public boolean of(Supplier<Talent> talentIn) {
        return this.of(talentIn.get());
    }

    public boolean of(Talent talentDelegateIn) {
        return talentDelegateIn == this.talentDelegate;
    }

    public TalentInstance copy() {
        return this.talentDelegate.getDefault(this.level);
    }

    public void writeToNBT(AbstractCatEntity catIn, CompoundTag compound) {
        compound.putInt("level", this.level());
    }

    public void readFromNBT(AbstractCatEntity catIn, CompoundTag compound) {
        this.setLevel(compound.getInt("level"));
    }

    public void writeToBuf(FriendlyByteBuf buf) {
        buf.writeInt(this.level());
    }

    public void readFromBuf(FriendlyByteBuf buf) {
        this.setLevel(buf.readInt());
    }

    public final void writeInstance(AbstractCatEntity catIn, CompoundTag compound) {
        ResourceLocation rl = CatHerderAPI.TALENTS.get().getKey(this.talentDelegate);
        if (rl != null) {
            compound.putString("type", rl.toString());
        }

        this.writeToNBT(catIn, compound);
    }

    public static Optional<TalentInstance> readInstance(AbstractCatEntity catIn, CompoundTag compound) {
        ResourceLocation rl = ResourceLocation.tryParse(compound.getString("type"));
        if (CatHerderAPI.TALENTS.get().containsKey(rl)) {
            TalentInstance inst = CatHerderAPI.TALENTS.get().getValue(rl).getDefault();
            inst.readFromNBT(catIn, compound);
            return Optional.of(inst);
        } else {
            CatHerderAPI.LOGGER.warn("Failed to load talent {}", rl);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends TalentInstance> T cast(Class<T> type) {
        if (this.getClass().isAssignableFrom(type)) {
            return (T) this;
        } else {
            throw new RuntimeException("Could not cast " + this.getClass().getName() + " to " + type.getName());
        }
    }

    @Override
    public String toString() {
        return String.format("%s [talent: %s, level: %d]", this.getClass().getSimpleName(), CatHerderAPI.TALENTS.get().getKey(talentDelegate), this.level);
    }

    /**
     * Called when ever this instance is first added to a cat, this is called when
     * the level is first set on the cat or when it is loaded from NBT and when the
     * talents are synced to the client
     *
     * @param catIn The cat
     */
    public void init(AbstractCatEntity catIn) {

    }

    /**
     * Called when the level of the cat changes
     * Is not called when the cat is loaded from NBT
     *
     * @param cat The cat
     */
    public void set(AbstractCatEntity cat, int levelBefore) {

    }

    public boolean hasRenderer() {
        return this.getTalent().hasRenderer();
    }
}