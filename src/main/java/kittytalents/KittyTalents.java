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

//    public static final RegistryObject<Talent> BED_FINDER = registerInst("bed_finder", BedFinderTalent::new);
    public static final RegistryObject<Talent> BLACK_PELT = registerInst("black_pelt", BlackPeltTalent::new);
    public static final RegistryObject<Talent> CREEPER_SWEEPER = registerInst("creeper_sweeper", CreeperSweeperTalent::new);
    public static final RegistryObject<Talent> KITTY_DASH = registerInst("kitty_dash", KittyDashTalent::new);
    public static final RegistryObject<Talent> FISHER_CAT = registerInst("fisher_cat", FisherCatTalent::new);
    public static final RegistryObject<Talent> GUARD_CAT = registerInst("guard_cat", GuardCatTalent::new);
    public static final RegistryObject<Talent> HAPPY_EATER = registerInst("happy_eater", HappyEaterTalent::new);
    public static final RegistryObject<Talent> HELL_BEAST = registerInst("hell_beast", HellBeastTalent::new);
    public static final RegistryObject<Talent> HUNTER_CAT = registerInst("hunter_cat", null);
    public static final RegistryObject<Talent> PACK_KITTY = registerInst("pack_kitty", PackKittyTalent::new);
    public static final RegistryObject<Talent> PEST_FIGHTER = registerInst("pest_fighter", PestFighterTalent::new);
//    public static final RegistryObject<Talent> PILLOW_PAW = registerInst("pillow_paw", PillowPawTalent::new);
    public static final RegistryObject<Talent> POISON_FANG = registerInst("poison_fang", PoisonFangTalent::new);
    public static final RegistryObject<Talent> KITTY_EYES = registerInst("kitty_eyes", KittyEyesTalent::new);
    public static final RegistryObject<Talent> QUICK_HEALER = registerInst("quick_healer", QuickHealerTalent::new);
    //public static final RegistryObject<Talent> RANGED_ATTACKER = registerInst("ranged_attacker", RangedAttacker::new);
    public static final RegistryObject<Talent> RESCUE_CAT = registerInst("rescue_cat", RescueCatTalent::new);
    public static final RegistryObject<Talent> ROARING_GALE = registerInst("roaring_gale", RoaringGaleTalent::new);
//    public static final RegistryObject<Talent> SHEPHERD_DOG = registerInst("shepherd_cat", ShepherdCatTalent::new);
    public static final RegistryObject<Talent> SWIMMER_CAT = registerInst("swimmer_cat", SwimmerCatTalent::new);
    public static final RegistryObject<Talent> MOUNT = registerInst("mount", MountTalent::new);
    public static final RegistryObject<Talent> KITTY_TORCH = registerInst("kitty_torch", KittyTorchTalent::new);

    private static <T extends Talent> RegistryObject<Talent> registerInst(final String name, final BiFunction<Talent, Integer, TalentInstance> sup) {
        return register(name, () -> new Talent(sup));
    }

    private static <T extends Talent> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TALENTS.register(name, sup);
    }
}
