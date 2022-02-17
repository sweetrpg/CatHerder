package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.storage.CatLocationData;
import com.sweetrpg.catherder.common.storage.CatLocationStorage;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class RadarItem extends Item {

    public RadarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
//        if (worldIn.isRemote) {
//            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> RadarScreen.open(playerIn));
//        }

        if (!worldIn.isClientSide) {
            if (playerIn.isShiftKeyDown()) {
                CatLocationStorage locationManager = CatLocationStorage.get(worldIn);
                for (UUID uuid : locationManager.getAllUUID()) {
                    playerIn.sendMessage(new TextComponent(locationManager.getData(uuid).toString()), Util.NIL_UUID);
                }
                return new InteractionResultHolder<>(InteractionResult.FAIL, playerIn.getItemInHand(handIn));
            }

            ResourceKey<Level> dimCurr = playerIn.level.dimension();

            playerIn.sendMessage(new TextComponent(""), Util.NIL_UUID);

            CatLocationStorage locationManager = CatLocationStorage.get(worldIn);
            List<CatLocationData> ownCats = locationManager.getCats(playerIn, dimCurr).collect(Collectors.toList());

            if (ownCats.isEmpty()) {
                playerIn.sendMessage(new TranslatableComponent("catradar.errornull", dimCurr.location()), Util.NIL_UUID);
            } else {
                boolean flag = false;

                for (CatLocationData loc : ownCats) {
                    if (loc.shouldDisplay(worldIn, playerIn, handIn)) {
                        flag = true;

                        String translateStr = RadarItem.getDirectionTranslationKey(loc, playerIn);
                        int distance = Mth.ceil(loc.getPos() != null ? loc.getPos().distanceTo(playerIn.position()) : -1);

                        playerIn.sendMessage(new TranslatableComponent(translateStr, loc.getName(worldIn), distance), Util.NIL_UUID);
                    }
                }

                if (!flag) {
                    playerIn.sendMessage(new TranslatableComponent("catradar.errornoradio"), Util.NIL_UUID);
                }
            }

            List<ResourceKey<Level>> otherCats = new ArrayList<>();
            List<ResourceKey<Level>> noCats = new ArrayList<>();
            for (ResourceKey<Level> worldkey : worldIn.getServer().levelKeys()) {
                if (worldkey.equals(worldIn.dimension()))  continue;
                ownCats = locationManager.getCats(playerIn, worldkey).collect(Collectors.toList()); // Check if radio collar is on

                (ownCats.size() > 0 ? otherCats : noCats).add(worldkey);
            }

            if (otherCats.size() > 0) {
                playerIn.sendMessage(new TranslatableComponent("catradar.notindim", otherCats.stream().map(ResourceKey::location).map(Objects::toString).collect(Collectors.joining(", "))), Util.NIL_UUID);
            }

            if (noCats.size() > 0 && stack.getItem() == ModItems.CREATIVE_RADAR.get()) {
                playerIn.sendMessage(new TranslatableComponent("catradar.errornull", noCats.stream().map(ResourceKey::location).map(Objects::toString).collect(Collectors.joining(", "))), Util.NIL_UUID);
            }
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.FAIL, stack);
    }

    public static String getDirectionTranslationKey(CatLocationData loc, Entity entity) {
        if (loc.getPos() == null) {
            return "catradar.unknown";
        }
        Vec3 diff = loc.getPos().add(entity.position().reverse());
        double angle = Mth.atan2(diff.x(), diff.z());

        if (angle < -Math.PI + Math.PI / 8) {
            return "catradar.north";
        } else if (angle < -Math.PI + 3 * Math.PI / 8) {
            return "catradar.north.west";
        } else if (angle < -Math.PI + 5 * Math.PI / 8) {
            return "catradar.west";
        } else if (angle < -Math.PI + 7 * Math.PI / 8) {
            return "catradar.south.west";
        } else if (angle < -Math.PI + 9 * Math.PI / 8) {
            return "catradar.south";
        } else if (angle < -Math.PI + 11 * Math.PI / 8) {
            return "catradar.south.east";
        } else if (angle < -Math.PI + 13 * Math.PI / 8) {
            return "catradar.east";
        } else if (angle < -Math.PI + 15 * Math.PI / 8) {
            return "catradar.north.east";
        } else {
            return "catradar.north";
        }
    }
}
