package kittytalents;

import kittytalents.api.registry.Accessory;
import kittytalents.common.entity.accessory.*;
import kittytalents.common.lib.Constants;
import kittytalents.common.lib.Resources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class KittyAccessories {

    public static final DeferredRegister<Accessory> ACCESSORIES = DeferredRegister.create(Accessory.class, Constants.MOD_ID);

    public static final RegistryObject<DyeableAccessory> DYEABLE_COLLAR = register("dyeable_collar", () -> new DyeableAccessory(KittyAccessoryTypes.COLLAR, KittyItems.WOOL_COLLAR).setModelTexture(Resources.COLLAR_DEFAULT).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<Collar> GOLDEN_COLLAR = register("golden_collar", () -> new Collar(KittyItems.CREATIVE_COLLAR).setModelTexture(Resources.COLLAR_GOLDEN).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<Collar> SPOTTED_COLLAR = register("spotted_collar", () -> new Collar(KittyItems.SPOTTED_COLLAR).setModelTexture(Resources.COLLAR_SPOTTED).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<Collar> MULTICOLORED_COLLAR = register("multicolored_collar", () -> new Collar(KittyItems.MULTICOLOURED_COLLAR).setModelTexture(Resources.COLLAR_MULTICOLORED).setRenderer("DefaultAccessoryRenderer"));

    public static final RegistryObject<Clothing> GUARD_SUIT = register("guard_suit", () -> new Clothing(KittyItems.GUARD_SUIT).setModelTexture(Resources.GUARD_SUIT).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<Clothing> LEATHER_JACKET_CLOTHING = register("leather_jacket_clothing", () -> new Clothing(KittyItems.LEATHER_JACKET).setModelTexture(Resources.CLOTHING_LEATHER_JACKET).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<Glasses> SUNGLASSES = register("sunglasses", () -> new Glasses(KittyItems.SUNGLASSES).setModelTexture(Resources.GLASSES_SUNGLASSES).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<Clothing> CAPE = register("cape", () -> new Clothing(KittyItems.CAPE).setModelTexture(Resources.CAPE).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<DyeableAccessory> DYEABLE_CAPE = register("dyeable_cape", () -> new DyeableAccessory(KittyAccessoryTypes.CLOTHING, KittyItems.CAPE_COLOURED).setModelTexture(Resources.DYEABLE_CAPE).setRenderer("DefaultAccessoryRenderer"));
    public static final RegistryObject<Band> RADIO_BAND = register("radio_band", () -> new Band(KittyItems.RADIO_COLLAR).setModelTexture(Resources.RADIO_BAND).setRenderer("DefaultAccessoryRenderer"));

    public static final RegistryObject<Helmet> IRON_HELMET = registerHelmet("iron_helmet", () -> Items.IRON_HELMET, Resources.IRON_HELMET);
    public static final RegistryObject<Helmet> DIAMOND_HELMET = registerHelmet("diamond_helmet", () -> Items.DIAMOND_HELMET, Resources.DIAMOND_HELMET);
    public static final RegistryObject<Helmet> GOLDEN_HELMET = registerHelmet("golden_helmet", () -> Items.GOLDEN_HELMET, Resources.GOLDEN_HELMET);
    public static final RegistryObject<Helmet> CHAINMAIL_HELMET = registerHelmet("chainmail_helmet", () -> Items.CHAINMAIL_HELMET, Resources.CHAINMAIL_HELMET);
    public static final RegistryObject<Helmet> TURTLE_HELMET = registerHelmet("turtle_helmet", () -> Items.TURTLE_HELMET, Resources.TURTLE_HELMET);
    public static final RegistryObject<Helmet> NETHERITE_HELMET = registerHelmet("netherite_helmet", () -> Items.NETHERITE_HELMET, Resources.NETHERITE_HELMET);

    public static final RegistryObject<ArmourAccessory> IRON_BODY_PIECE = registerBodyPiece("iron_body_piece", () -> Items.IRON_CHESTPLATE, Resources.IRON_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> DIAMOND_BODY_PIECE = registerBodyPiece("diamond_body_piece", () -> Items.DIAMOND_CHESTPLATE, Resources.DIAMOND_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> GOLDEN_BODY_PIECE = registerBodyPiece("golden_body_piece", () -> Items.GOLDEN_CHESTPLATE, Resources.GOLDEN_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> CHAINMAIL_BODY_PIECE = registerBodyPiece("chainmail_body_piece", () -> Items.CHAINMAIL_CHESTPLATE, Resources.CHAINMAIL_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> NETHERITE_BODY_PIECE = registerBodyPiece("netherite_body_piece", () -> Items.NETHERITE_CHESTPLATE, Resources.NETHERITE_BODY_PIECE);

    public static final RegistryObject<ArmourAccessory> IRON_BOOTS = registerBoots("iron_boots", () -> Items.IRON_BOOTS, Resources.IRON_BOOTS);
    public static final RegistryObject<ArmourAccessory> DIAMOND_BOOTS = registerBoots("diamond_boots", () -> Items.DIAMOND_BOOTS, Resources.DIAMOND_BOOTS);
    public static final RegistryObject<ArmourAccessory> GOLDEN_BOOTS = registerBoots("golden_boots", () -> Items.GOLDEN_BOOTS, Resources.GOLDEN_BOOTS);
    public static final RegistryObject<ArmourAccessory> CHAINMAIL_BOOTS = registerBoots("chainmail_boots", () -> Items.CHAINMAIL_BOOTS, Resources.CHAINMAIL_BOOTS);
    public static final RegistryObject<ArmourAccessory> NETHERITE_BOOTS = registerBoots("netherite_boots", () -> Items.NETHERITE_BOOTS, Resources.NETHERITE_BOOTS);

    public static final RegistryObject<LeatherArmourAccessory> LEATHER_HELMET = register("leather_helmet", () -> new LeatherArmourAccessory(KittyAccessoryTypes.HEAD, Items.LEATHER_HELMET.delegate).setModelTexture(Resources.LEATHER_HELMET));
    public static final RegistryObject<LeatherArmourAccessory> LEATHER_BODY_PIECE = register("leather_body_piece", () -> new LeatherArmourAccessory(KittyAccessoryTypes.CLOTHING, Items.LEATHER_CHESTPLATE.delegate).setModelTexture(Resources.LEATHER_BODY_PIECE));
    public static final RegistryObject<LeatherArmourAccessory> LEATHER_BOOTS = register("leather_boots", () -> new LeatherArmourAccessory(KittyAccessoryTypes.FEET, Items.LEATHER_BOOTS.delegate).setModelTexture(Resources.LEATHER_BOOTS));

    private static RegistryObject<Helmet> registerHelmet(final String name, final Supplier<? extends ItemLike> itemIn, ResourceLocation modelLocation) {
        return ACCESSORIES.register(name, () -> new Helmet(itemIn).setModelTexture(modelLocation));
    }

    private static RegistryObject<ArmourAccessory> registerBoots(final String name, final Supplier<? extends ItemLike> itemIn, ResourceLocation modelLocation) {
        return ACCESSORIES.register(name, () -> new ArmourAccessory(KittyAccessoryTypes.FEET, itemIn).setModelTexture(modelLocation));
    }

    private static RegistryObject<ArmourAccessory> registerBodyPiece(final String name, final Supplier<? extends ItemLike> itemIn, ResourceLocation modelLocation) {
        return ACCESSORIES.register(name, () -> new ArmourAccessory(KittyAccessoryTypes.CLOTHING, itemIn).setModelTexture(modelLocation));
    }

    private static <T extends Accessory> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ACCESSORIES.register(name, sup);
    }
}
