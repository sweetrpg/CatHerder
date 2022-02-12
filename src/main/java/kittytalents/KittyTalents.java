package kittytalents;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import kittytalents.common.lib.Constants;
import kittytalents.common.talent.*;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class KittyTalents {

    public static final DeferredRegister<Talent> TALENTS = DeferredRegister.create(Talent.class, Constants.MOD_ID);

    public static final RegistryObject<Talent> BED_FINDER = registerInst("bed_finder", BedFinderTalent::new);
    public static final RegistryObject<Talent> BLACK_PELT = registerInst("black_pelt", BlackPeltTalent::new);
    public static final RegistryObject<Talent> CREEPER_SWEEPER = registerInst("creeper_sweeper", CreeperSweeperTalent::new);
    public static final RegistryObject<Talent> KITTY_DASH = registerInst("kitty_dash", KittyDashTalent::new);
    public static final RegistryObject<Talent> FISHER_DOG = registerInst("fisher_dog", FisherDogTalent::new);
    public static final RegistryObject<Talent> GUARD_DOG = registerInst("guard_dog", GuardDogTalent::new);
    public static final RegistryObject<Talent> HAPPY_EATER = registerInst("happy_eater", HappyEaterTalent::new);
    public static final RegistryObject<Talent> HELL_HOUND = registerInst("hell_hound", HellHoundTalent::new);
    public static final RegistryObject<Talent> HUNTER_DOG = registerInst("hunter_dog", null);
    public static final RegistryObject<Talent> PACK_PUPPY = registerInst("pack_puppy", PackPuppyTalent::new);
    public static final RegistryObject<Talent> PEST_FIGHTER = registerInst("pest_fighter", PestFighterTalent::new);
    public static final RegistryObject<Talent> PILLOW_PAW = registerInst("pillow_paw", PillowPawTalent::new);
    public static final RegistryObject<Talent> POISON_FANG = registerInst("poison_fang", PoisonFangTalent::new);
    public static final RegistryObject<Talent> PUPPY_EYES = registerInst("puppy_eyes", PuppyEyesTalent::new);
    public static final RegistryObject<Talent> QUICK_HEALER = registerInst("quick_healer", QuickHealerTalent::new);
    //public static final RegistryObject<Talent> RANGED_ATTACKER = registerInst("ranged_attacker", RangedAttacker::new);
    public static final RegistryObject<Talent> RESCUE_DOG = registerInst("rescue_dog", RescueDogTalent::new);
    public static final RegistryObject<Talent> ROARING_GALE = registerInst("roaring_gale", RoaringGaleTalent::new);
    public static final RegistryObject<Talent> SHEPHERD_DOG = registerInst("shepherd_dog", ShepherdDogTalent::new);
    public static final RegistryObject<Talent> SWIMMER_DOG = registerInst("swimmer_dog", SwimmerDogTalent::new);
    public static final RegistryObject<Talent> WOLF_MOUNT = registerInst("wolf_mount", WolfMountTalent::new);
    public static final RegistryObject<Talent> KITTY_TORCH = registerInst("kitty_torch", KittyTorchTalent::new);

    private static <T extends Talent> RegistryObject<Talent> registerInst(final String name, final BiFunction<Talent, Integer, TalentInstance> sup) {
        return register(name, () -> new Talent(sup));
    }

    private static <T extends Talent> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TALENTS.register(name, sup);
    }
}
