package kittytalents.api.registry;

import java.util.Optional;
import java.util.function.Supplier;

import kittytalents.api.KittyTalentsAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IRegistryDelegate;

public class TalentInstance implements kittytalents.api.inferface.ICatAlteration {

    protected final IRegistryDelegate<Talent> talentDelegate;

    protected int level;

    public TalentInstance(Talent talentIn, int levelIn) {
        this(talentIn.delegate, levelIn);
    }

    public TalentInstance(Talent talentIn) {
        this(talentIn.delegate, 1);
    }

    public TalentInstance(IRegistryDelegate<Talent> talentDelegateIn, int levelIn) {
        this.talentDelegate = talentDelegateIn;
        this.level = levelIn;
    }

    public Talent getTalent() {
        return this.talentDelegate.get();
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

    public boolean of(Talent talentIn) {
        return this.of(talentIn.delegate);
    }

    public boolean of(IRegistryDelegate<Talent> talentDelegateIn) {
        return talentDelegateIn.equals(this.talentDelegate);
    }

    public TalentInstance copy() {
        return this.talentDelegate.get().getDefault(this.level);
    }

    public void writeToNBT(kittytalents.api.inferface.AbstractCatEntity dogIn, CompoundTag compound) {
        compound.putInt("level", this.level());
    }

    public void readFromNBT(kittytalents.api.inferface.AbstractCatEntity dogIn, CompoundTag compound) {
        this.setLevel(compound.getInt("level"));
    }

    public void writeToBuf(FriendlyByteBuf buf) {
        buf.writeInt(this.level());
    }

    public void readFromBuf(FriendlyByteBuf buf) {
        this.setLevel(buf.readInt());
    }

    public final void writeInstance(kittytalents.api.inferface.AbstractCatEntity dogIn, CompoundTag compound) {
        ResourceLocation rl = this.talentDelegate.name();
        if (rl != null) {
            compound.putString("type", rl.toString());
        }

        this.writeToNBT(dogIn, compound);
    }

    public static Optional<TalentInstance> readInstance(kittytalents.api.inferface.AbstractCatEntity dogIn, CompoundTag compound) {
        ResourceLocation rl = ResourceLocation.tryParse(compound.getString("type"));
        if (KittyTalentsAPI.TALENTS.containsKey(rl)) {
            TalentInstance inst = KittyTalentsAPI.TALENTS.getValue(rl).getDefault();
            inst.readFromNBT(dogIn, compound);
            return Optional.of(inst);
        } else {
            KittyTalentsAPI.LOGGER.warn("Failed to load talent {}", rl);
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
        return String.format("%s [talent: %s, level: %d]", this.getClass().getSimpleName(), talentDelegate.name(), this.level);
    }

    /**
     * Called when ever this instance is first added to a cat, this is called when
     * the level is first set on the cat or when it is loaded from NBT and when the
     * talents are synced to the client
     *
     * @param dogIn The cat
     */
    public void init(kittytalents.api.inferface.AbstractCatEntity dogIn) {

    }

    /**
     * Called when the level of the cat changes
     * Is not called when the cat is loaded from NBT
     *
     * @param dogIn The cat
     */
    public void set(kittytalents.api.inferface.AbstractCatEntity cat, int levelBefore) {

    }

    public boolean hasRenderer() {
        return this.getTalent().hasRenderer();
    }
}
