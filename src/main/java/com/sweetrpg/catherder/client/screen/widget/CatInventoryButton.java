package com.sweetrpg.catherder.client.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.lib.Resources;
import com.sweetrpg.catherder.common.talent.PackCatTalent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.List;

public class CatInventoryButton extends Button {

    private final Screen parent;
    private final int baseX;

    private final Tooltip TOOLTIP_ACTIVE =
            Tooltip.create(Component.translatable("container.catherder.cat_inventories.link"));

    private final Tooltip TOOLTIP_NO_ACTIVE =
            Tooltip.create(Component.translatable("container.catherder.cat_inventories.link")
                    .withStyle(ChatFormatting.RED));

    public CatInventoryButton(int x, int y, Screen parentIn, OnPress onPress) {
        super(x, y, 13, 10, CommonComponents.EMPTY, onPress, Button.DEFAULT_NARRATION);
        this.baseX = x;
        this.parent = parentIn;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if(this.parent instanceof CreativeModeInventoryScreen t) {
            this.visible = t.isInventoryOpen();
            this.active = this.visible;
        }

        if(this.parent instanceof InventoryScreen) {
            RecipeBookComponent recipeBook = ((InventoryScreen) this.parent).getRecipeBookComponent();
            if(recipeBook.isVisible()) {
                this.setX(this.baseX + 77);
            }
            else {
                this.setX(this.baseX);
            }
        }

        if(this.visible) {
            Minecraft mc = Minecraft.getInstance();
            List<CatEntity> cats = mc.level.getEntitiesOfClass(CatEntity.class, mc.player.getBoundingBox().inflate(12D, 12D, 12D),
                    (cat) -> cat.canInteract(mc.player) && PackCatTalent.hasInventory(cat)
            );
            this.active = !cats.isEmpty();
            if(this.active) {
                this.setTooltip(TOOLTIP_ACTIVE);
            }
            else {
                this.setTooltip(TOOLTIP_NO_ACTIVE);
            }
        }

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderWidget(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.setShaderTexture(0, Resources.SMALL_WIDGETS);
        Minecraft mc = Minecraft.getInstance();
        int i = this.getTextureY();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.blit(stack, this.getX(), this.getY(), 0, 36 + i * 10, this.width, this.height);
//        this.renderBg(stack, mc, mouseX, mouseY);
    }

    private int getTextureY() {
        int i = 1;
        if(!this.active) {
            i = 0;
        }
        else if(this.isHoveredOrFocused()) {
            i = 2;
        }

        return i;
    }
}
