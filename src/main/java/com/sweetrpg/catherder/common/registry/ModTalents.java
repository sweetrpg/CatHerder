package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.talent.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ModTalents {

    public static final DeferredRegister<Talent> TALENTS = DeferredRegister.create(Talent.class, CatHerderAPI.MOD_ID);

    public static final RegistryObject<Talent> BED_FINDER = registerInst("bed_finder", BedFinderTalent::new);
    public static final RegistryObject<Talent> CATLIKE_REFLEXES = registerInst("catlike_reflexes", CatlikeReflexesTalent::new);
    public static final RegistryObject<Talent> CHEETAH_SPEED = registerInst("cheetah_speed", CheetahSpeedTalent::new);
    public static final RegistryObject<Talent> CREEPER_SWEEPER = registerInst("creeper_sweeper", CreeperSweeperTalent::new);
    public static final RegistryObject<Talent> FISHER_CAT = registerInst("fisher_cat", FisherCatTalent::new);
    public static final RegistryObject<Talent> HAPPY_EATER = registerInst("happy_eater", HappyEaterTalent::new);
    public static final RegistryObject<Talent> HELL_BEAST = registerInst("hell_beast", HellBeastTalent::new);
    public static final RegistryObject<Talent> MOUNT = registerInst("mount", MountTalent::new);
    public static final RegistryObject<Talent> NERMAL = registerInst("nermal", NermalTalent::new);
    public static final RegistryObject<Talent> PACK_CAT = registerInst("pack_cat", PackCatTalent::new);
    public static final RegistryObject<Talent> PEST_FIGHTER = registerInst("pest_fighter", PestFighterTalent::new);
    public static final RegistryObject<Talent> POISON_FANG = registerInst("poison_fang", PoisonFangTalent::new);
    public static final RegistryObject<Talent> POUNCE = registerInst("pounce", PounceTalent::new);
    public static final RegistryObject<Talent> QUICK_HEALER = registerInst("quick_healer", QuickHealerTalent::new);
    public static final RegistryObject<Talent> RAZOR_SHARP_CLAWS = registerInst("razor_sharp_claws", RazorsharpClawsTalent::new);
    public static final RegistryObject<Talent> RESCUE_CAT = registerInst("rescue_cat", RescueCatTalent::new);
    public static final RegistryObject<Talent> SUPER_JUMP = registerInst("super_jump", SuperJumpTalent::new);
    public static final RegistryObject<Talent> TOMCAT = registerInst("tomcat", TomcatTalent::new);

    private static <T extends Talent> RegistryObject<Talent> registerInst(final String name, final BiFunction<Talent, Integer, TalentInstance> sup) {
        return register(name, () -> new Talent(sup));
    }

    private static <T extends Talent> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TALENTS.register(name, sup);
    }
}
