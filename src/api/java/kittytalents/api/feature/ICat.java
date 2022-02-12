package kittytalents.api.feature;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import kittytalents.api.registry.AccessoryInstance;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.world.entity.LivingEntity;

// TODO: Add javadoc
public interface ICat {

    public kittytalents.api.inferface.AbstractCatEntity getDog();

    public void untame();

    public boolean canInteract(LivingEntity playerIn);

    public EnumMode getMode();

    public DogLevel getDogLevel();
    public void increaseLevel(DogLevel.Type typeIn);

    /**
     * Convenience method to get the level of a talent
     * @param talentGetter A getter function, typically a {@link RegistryObject<Talent>} would be provided
     * @return The level of the talent
     */
    default int getDogLevel(Supplier<? extends Talent> talentGetter) {
        return this.getDogLevel(talentGetter.get());
    }

    /**
     * Returns the level of the given talent
     * @param talentIn The {@link Talent}
     * @return The level of the talent
     */
    public int getDogLevel(Talent talentIn);

    default Optional<TalentInstance> getTalent(Supplier<? extends Talent> talentGetter) {
        return this.getTalent(talentGetter.get());
    }

    public Optional<TalentInstance> getTalent(Talent talentIn);

    public int getDogSize();
    public void setDogSize(int size);

    public float getMaxHunger();
    public float getDogHunger();
    public void addHunger(float add);
    public void setDogHunger(float hunger);

    public boolean addAccessory(AccessoryInstance inst);
    public List<AccessoryInstance> getAccessories();
    public List<AccessoryInstance> removeAccessories();
    public void markAccessoriesDirty();

    public float getWagAngle(float limbSwing, float limbSwingAmount, float partialTickTime);
    public float getShakeAngle(float partialTicks, float offset);
    public float getInterestedAngle(float partialTicks);

    public boolean isLying();

    public List<kittytalents.api.inferface.ICatFoodHandler> getFoodHandlers();

    @Deprecated
    public <T> void setData(DataKey<T> key, T value);
    /**
     * Tries to put the object in the map, does nothing if the key already exists
     */
    @Deprecated
    public <T> void setDataIfEmpty(DataKey<T> key, T value);
    @Deprecated
    public <T> T getData(DataKey<T> key);
    @Deprecated
    public <T> T getDataOrGet(DataKey<T> key, Supplier<T> other);
    @Deprecated
    public <T> T getDataOrDefault(DataKey<T> key, T other);
    @Deprecated
    public <T> boolean hasData(DataKey<T> key);
}
