package com.sweetrpg.catherder.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.feature.CatLevel.Type;
import com.sweetrpg.catherder.api.feature.EnumMode;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.network.PacketHandler;
import com.sweetrpg.catherder.common.network.packet.data.*;
import com.sweetrpg.catherder.common.registry.ModAccessories;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CatInfoScreen extends Screen {

    public final CatEntity cat;
    public final Player player;
    public int textureIndex;
    private int currentPage = 0;
    private int maxPages = 1;
    private final List<AbstractWidget> talentWidgets = new ArrayList<>(16);
    private Button leftBtn, rightBtn;
    private final List<Talent> talentList;
//    private final List<ResourceLocation> customSkinList;

    public CatInfoScreen(CatEntity cat, Player player) {
        super(Component.translatable(Constants.TRANSLATION_KEY_GUI_CATINFO_TITLE));
        this.cat = cat;
        this.player = player;
        this.talentList = CatHerderAPI.TALENTS.get().getValues().stream().sorted(Comparator.comparing((t) -> I18n.get(t.getTranslationKey()))).collect(Collectors.toList());

//        this.customSkinList = CatTextureManager.INSTANCE.getAll();
//        this.textureIndex = this.customSkinList.indexOf(CatTextureManager.INSTANCE.getTextureLoc(cat.getSkinHash()));
//        this.textureIndex = this.textureIndex >= 0 ? this.textureIndex : 0;
    }

    public static void open(CatEntity cat) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new CatInfoScreen(cat, mc.player));
    }

    @Override
    public void init() {
        super.init();
//        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int topX = this.width / 2;
        int topY = this.height / 2;

        EditBox nameTextField = new EditBox(this.font, topX - 100, topY + 50, 200, 20, Component.translatable(Constants.TRANSLATION_KEY_GUI_CATINFO_ENTER_NAME));
        nameTextField.setResponder(text -> {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new CatNameData(CatInfoScreen.this.cat.getId(), text));
        });
        nameTextField.setFocused(false);
        nameTextField.setMaxLength(32);

        if(this.cat.hasCustomName()) {
            nameTextField.setValue(this.cat.getCustomName().getString());
        }

        this.addRenderableWidget(nameTextField);

        if(this.cat.isOwnedBy(this.player)) {
            Button obeyBtn = new Button.Builder(Component.literal(String.valueOf(this.cat.willObeyOthers())), (btn) -> {
                btn.setMessage(Component.literal(String.valueOf(!this.cat.willObeyOthers())));
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new CatObeyData(this.cat.getId(), !this.cat.willObeyOthers()));
            }).pos(this.width - 64, topY + 77).size(42, 20).build();

            this.addRenderableWidget(obeyBtn);
        }

        Button attackPlayerBtn = new Button.Builder(Component.literal(String.valueOf(this.cat.canPlayersAttack())), button -> {
            button.setMessage(Component.literal(String.valueOf(!this.cat.canPlayersAttack())));
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new FriendlyFireData(this.cat.getId(), !this.cat.canPlayersAttack()));
        }).pos(this.width - 64, topY - 5).size(42, 20).build();

        this.addRenderableWidget(attackPlayerBtn);

//        //if (ConfigHandler.CLIENT.USE_DT_TEXTURES.get()) {
//        Button addBtn = new Button(this.width - 42, topY + 30, 20, 20, Component.literal("+"), (btn) -> {
//            this.textureIndex += 1;
//            this.textureIndex %= this.customSkinList.size();
//            ResourceLocation rl = this.customSkinList.get(this.textureIndex);
//
//            this.setCatTexture(rl);
//        });
//        Button lessBtn = new Button(this.width - 64, topY + 30, 20, 20, Component.literal("-"), (btn) -> {
//            this.textureIndex += this.customSkinList.size() - 1;
//            this.textureIndex %= this.customSkinList.size();
//            ResourceLocation rl = this.customSkinList.get(this.textureIndex);
//            this.setCatTexture(rl);
//        });

//        this.addRenderableWidget(addBtn);
//        this.addRenderableWidget(lessBtn);
        //}

        Button modeBtn = new Button.Builder(Component.translatable(this.cat.getMode().getUnlocalisedName()), button -> {
            EnumMode mode = CatInfoScreen.this.cat.getMode().nextMode();

            if(mode == EnumMode.WANDERING && !CatInfoScreen.this.cat.getBowlPos().isPresent()) {
                button.setMessage(Component.translatable(mode.getUnlocalisedName()).withStyle(ChatFormatting.RED));
            }
            else {
                button.setMessage(Component.translatable(mode.getUnlocalisedName()));
            }

            PacketHandler.send(PacketDistributor.SERVER.noArg(), new CatModeData(CatInfoScreen.this.cat.getId(), mode));
        }).pos(topX + 40, topY + 25).size(60, 20).tooltip(this.getTooltipForMode(this.cat.getMode())).build();

        this.addRenderableWidget(modeBtn);

        // Talent level-up buttons
        int size = CatHerderAPI.TALENTS.get().getKeys().size();
        int perPage = Math.max(Mth.floor((this.height - 10) / (double) 21) - 2, 1);
        this.currentPage = 0;
        this.recalculatePage(perPage);

        if(perPage < size) {
            this.leftBtn = new Button.Builder(Component.literal("<"), (btn) -> {
                this.currentPage = Math.max(0, this.currentPage - 1);
                btn.active = this.currentPage > 0;
                this.rightBtn.active = true;
                this.recalculatePage(perPage);
            }).pos(25, perPage * 21 + 10).size(20, 20).tooltip(Tooltip.create(Component.translatable(Constants.TRANSLATION_KEY_GUI_PREVIOUS_PAGE).withStyle(ChatFormatting.ITALIC))).build();
            this.leftBtn.active = false;

            this.rightBtn = new Button.Builder(48, perPage * 21 + 10, 20, 20, Component.literal(">"), (btn) -> {
                this.currentPage = Math.min(this.maxPages - 1, this.currentPage + 1);
                btn.active = this.currentPage < this.maxPages - 1;
                this.leftBtn.active = true;
                this.recalculatePage(perPage);
            }).pos(48, perPage * 21 + 10).size(20, 20).tooltip(Tooltip.create(Component.translatable(Constants.TRANSLATION_KEY_GUI_NEXT_PAGE).withStyle(ChatFormatting.ITALIC))).build();

            this.addRenderableWidget(this.leftBtn);
            this.addRenderableWidget(this.rightBtn);
        }
    }

//    private void setCatTexture(ResourceLocation rl) {
//        if(ConfigHandler.SEND_SKIN) {
//            try {
//                byte[] data = CatTextureManager.INSTANCE.getResourceBytes(rl);
//                PacketHandler.send(PacketDistributor.SERVER.noArg(), new SendSkinData(this.cat.getId(), data));
//            }
//            catch(IOException e) {
//                CatHerder.LOGGER.error("Was unable to get resource data for {}, {}", rl, e);
//            }
//        }
//        else {
//            PacketHandler.send(PacketDistributor.SERVER.noArg(), new CatTextureData(this.cat.getId(), CatTextureManager.INSTANCE.getTextureHash(rl)));
//        }
//    }

    private void recalculatePage(int perPage) {
        this.talentWidgets.forEach(this::removeWidget);
        this.talentWidgets.clear();

        this.maxPages = Mth.ceil(this.talentList.size() / (double) perPage);

        for(int i = 0; i < perPage; ++i) {

            int index = this.currentPage * perPage + i;
            if(index >= this.talentList.size()) {break;}
            Talent talent = this.talentList.get(index);

            Button button = new Button.Builder(Component.literal("+"), (btn) -> {
                int level = CatInfoScreen.this.cat.getCatLevel(talent);
                if(level < talent.getMaxLevel() && CatInfoScreen.this.cat.canSpendPoints(talent.getLevelCost(level + 1))) {
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), new CatTalentData(CatInfoScreen.this.cat.getId(), talent));
                }

            }).pos(25, 10 + i * 21).size(20, 20).tooltip(getTooltipForTalent(talent, this.cat.getCatLevel(talent))).build(b -> TalentButton(b, talent));
            button.active = ConfigHandler.TALENT.getFlag(talent);

            this.talentWidgets.add(button);
            this.addRenderableWidget(button);
        }
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        //Background
        int topX = this.width / 2;
        int topY = this.height / 2;
        this.renderBackground(stack);

        // Background
        String health = Util.format1DP(this.cat.getHealth());
        String healthMax = Util.format1DP(this.cat.getMaxHealth());
        String speedValue = Util.format2DP(this.cat.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
        String armorValue = Util.format2DP(this.cat.getAttribute(Attributes.ARMOR).getValue());
        String ageValue = Util.format2DP(this.cat.getAge());
        String ageRel = I18n.get(this.cat.isBaby() ? Constants.TRANSLATION_KEY_GUI_BABY : Constants.TRANSLATION_KEY_GUI_ADULT);

        String ageString = ageValue + " " + ageRel;

        String tamedString = "";
        if(this.cat.isTame()) {
            if(this.cat.isOwnedBy(this.player)) {
                tamedString = I18n.get("catgui.owner.you");
            }
            else if(this.cat.getOwnersName().isPresent()) {
                tamedString = this.cat.getOwnersName().get().getString();
            }
        }

        //this.font.drawString(I18n.format("catgui.health") + healthState, this.width - 160, topY - 110, 0xFFFFFF);
        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_SPEED) + " " + speedValue, this.width - 160, topY - 100, 0xFFFFFF);
        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_OWNER) + " " + tamedString, this.width - 160, topY - 90, 0xFFFFFF);
        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_AGE) + " " + ageString, this.width - 160, topY - 80, 0xFFFFFF);
        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_ARMOR) + " " + armorValue, this.width - 160, topY - 70, 0xFFFFFF);
        if(ConfigHandler.SERVER.CAT_GENDER.get()) {
            this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_GENDER) + " " + I18n.get(this.cat.getGender().getUnlocalisedName()), this.width - 160, topY - 60, 0xFFFFFF);
        }

        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_NEW_NAME), topX - 100, topY + 38, 4210752);
        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_LEVEL) + " " + this.cat.getCatLevel().getLevel(Type.NORMAL), topX - 65, topY + 75, 0xFF10F9);
        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_LEVEL_DIRE) + " " + this.cat.getCatLevel().getLevel(Type.WILD), topX, topY + 75, 0xFF10F9);
        if(this.cat.getAccessory(ModAccessories.GOLDEN_COLLAR.get()).isPresent()) {
            this.font.draw(stack, ChatFormatting.GOLD + "Unlimited Points", topX - 38, topY + 89, 0xFFFFFF); //TODO translation
        }
        else {
            this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_POINTS_LEFT) + " " + this.cat.getSpendablePoints(), topX - 38, topY + 89, 0xFFFFFF);
        }
        // if (ConfigHandler.CLIENT.USE_DT_TEXTURES.get()) {
//        this.font.draw(stack, I18n.get("catgui.textureindex"), this.width - 80, topY + 20, 0xFFFFFF);
//        this.font.draw(stack, this.cat.getSkinHash().substring(0, Math.min(this.cat.getSkinHash().length(), 10)), this.width - 73, topY + 54, 0xFFFFFF);
        // }

        if(this.cat.isOwnedBy(this.player)) {
            this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_OBEY_OTHERS), this.width - 76, topY + 67, 0xFFFFFF);
        }

        this.font.draw(stack, I18n.get(Constants.TRANSLATION_KEY_GUI_FRIENDLY_FIRE), this.width - 76, topY - 15, 0xFFFFFF);

        this.renderables.forEach(widget -> {
            if(widget instanceof TalentButton talBut) {
                this.font.draw(stack, I18n.get(talBut.talent.getTranslationKey()), talBut.x + 25, talBut.y + 7, 0xFFFFFF);
            }
        });

        super.render(stack, mouseX, mouseY, partialTicks);
        //RenderHelper.disableStandardItemLighting(); // 1.14 enableGUIStandardItemLighting

//        for(Widget widget : this.renderables) {
//            if(widget instanceof AbstractWidget w && w.isHoveredOrFocused()) {
//                w.renderToolTip(stack, mouseX, mouseY);
//                break;
//            }
//        }

        // RenderHelper.enableStandardItemLighting();
    }

//    @Override
//    public void removed() {
//        super.removed();
//        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
//    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static class TalentButton extends Button {

        protected Talent talent;

        private TalentButton(Button.Builder builder, Talent talent) {
            super(builder);
            this.talent = talent;
        }

    }

    private Tooltip getTooltipForTalent(Talent talent, int level) {
        List<Component> list = Lists.newArrayList(Component.translatable(talent.getTranslationKey()).withStyle(ChatFormatting.GREEN));

        if (ConfigHandler.TALENT.getFlag(talent)) {
            list.add(Component.literal("Level: " + level));
            list.add(Component.literal("----------------------------").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable(talent.getInfoTranslationKey()));
        } else {
            list.add(Component.literal("Talent disabled").withStyle(ChatFormatting.RED));
        }

        return Tooltip.create(ComponentUtils.formatList(list, CommonComponents.NEW_LINE));
    }

    public Tooltip getTooltipForMode(EnumMode mode) {
        List<Component> list = new ArrayList<>();
        String str = I18n.get(cat.getMode().getUnlocalisedInfo());
        list.addAll(ScreenUtil.splitInto(str, 150, CatInfoScreen.this.font));
        if(mode == EnumMode.WANDERING) {

            if(CatInfoScreen.this.cat.getBowlPos().isPresent()) {
                double distance = CatInfoScreen.this.cat.blockPosition().distSqr(CatInfoScreen.this.cat.getBowlPos().get());

                if(distance > 256D) {
                    list.add(Component.translatable(Constants.TRANSLATION_KEY_CAT_MODE_DOCILE_DISTANCE, (int) Math.sqrt(distance)).withStyle(ChatFormatting.RED));
                }
                else {
                    list.add(Component.translatable(Constants.TRANSLATION_KEY_CAT_MODE_DOCILE_BOWL, (int) Math.sqrt(distance)).withStyle(ChatFormatting.GREEN));
                }
            }
            else {
                list.add(Component.translatable(Constants.TRANSLATION_KEY_CAT_MODE_DOCILE_NO_BOWL).withStyle(ChatFormatting.RED));
            }
        }

        return Tooltip.create(ComponentUtils.formatList(list, CommonComponents.NEW_LINE));
    }
}
