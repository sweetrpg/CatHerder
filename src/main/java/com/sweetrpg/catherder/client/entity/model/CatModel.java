package com.sweetrpg.catherder.client.entity.model;

import com.google.common.collect.ImmutableList;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CatModel<T extends AbstractCatEntity> extends ColorableAgeableListModel<T> {

    public ModelPart head;
//    public ModelPart realHead; //
    public ModelPart body;
//    public ModelPart mane; //
//    public ModelPart belly;
    public ModelPart legBackRight;
    public ModelPart legBackLeft;
    public ModelPart legFrontRight;
    public ModelPart legFrontLeft;
    public ModelPart upperTail;
    public ModelPart lowerTail; //
//    public ModelPart realTail2; //
//    public ModelPart realTail3; //

    public CatModel(ModelPart box) {
        CatHerder.LOGGER.debug("Creating CatModel with box: {}", box);

//        this.realHead = this.head.getChild("real_head");
        this.body = box.getChild("body");
        this.head = box.getChild("head");
//        this.belly = box.getChild("belly");
        this.legBackRight = box.getChild("right_hind_leg");
        this.legBackLeft = box.getChild("left_hind_leg");
        this.legFrontRight = box.getChild("right_front_leg");
        this.legFrontLeft = box.getChild("left_front_leg");
        this.upperTail = box.getChild("upperTail");
        this.lowerTail = this.upperTail.getChild("lowerTail");
//        this.realTail2 = this.tail.getChild("real_tail_2");
//        this.realTail3 = this.tail.getChild("real_tail_bushy");
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayerInternal(CubeDeformation.NONE);
    }

    public static LayerDefinition createArmorLayer() {
        return createBodyLayerInternal(new CubeDeformation(0.4F, 0.4F, 0.4F));
    }

    private static LayerDefinition createBodyLayerInternal(CubeDeformation scale) {
        CatHerder.LOGGER.debug("Creating body layer with scale: {}", scale);

        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                                                                                      .addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, scale)
                                                                                      .texOffs(0, 24)
                                                                                      .addBox(-1.5F, -0.0156F, -4.0F, 3.0F, 2.0F, 2.0F, scale)
                                                                                      .texOffs(0, 10)
                                                                                      .addBox(-2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, scale)
                                                                                      .texOffs(6, 10)
                                                                                      .addBox(1.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, scale),
                                                               PartPose.offset(0.0F, 14.5F, -10.0F));

        PartDefinition body = /*root.addOrReplaceChild("body", CubeListBuilder.create(),
                                                               PartPose.offset(0.0F, 20.0F, 1.0F));*/

        /*PartDefinition belly = */ root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 0)
                                                                              .addBox(-2.0F, -8.0F, -3.0F, 4.0F, 16.0F, 6.0F, scale),
                                                      PartPose.offsetAndRotation(0.0F, 16.5F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition upperTail = root.addOrReplaceChild("upperTail", CubeListBuilder.create().texOffs(0, 15)
                                                                            .addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, scale),
                                                     PartPose.offsetAndRotation(0.0F, 14.5F, 7.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition lowerTail = upperTail.addOrReplaceChild("lowerTail", CubeListBuilder.create().texOffs(4, 15)
                                                                                     .addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, scale),
                                                         PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition backLegL = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(8, 13)
                                                                                         .addBox(-1.0F, 1.0F, -2.0F, 2.0F, 6.0F, 2.0F, scale),
                                                         PartPose.offset(1.1F, 17.0F, 7.0F));

        PartDefinition backLegR = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(8, 13)
                                                                                          .addBox(-1.0F, 1.0F, -2.0F, 2.0F, 6.0F, 2.0F, scale),
                                                         PartPose.offset(-1.1F, 17.0F, 7.0F));

        PartDefinition frontLegL = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(40, 0)
                                                                                           .addBox(-1.0F, .8F, -2.0F, 2.0F, 10.0F, 2.0F, scale),
                                                          PartPose.offset(1.2F, 13.0F, -5.0F));

        PartDefinition frontLegR = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(40, 0)
                                                                                            .addBox(-1.0F, .8F, -2.0F, 2.0F, 10.0F, 2.0F, scale),
                                                          PartPose.offset(-1.2F, 13.0F, -5.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
//        MeshDefinition var0 = new MeshDefinition();
//        PartDefinition var1 = var0.getRoot();
//        float var2 = 13.5F;
//        PartDefinition var3 = var1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
//        var3.addOrReplaceChild("real_head", CubeListBuilder.create()
//                                                           // Head
//                                                           .texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, scale)
//                                                           // Ears Normal
//                                                           .texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale).texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale)
//                                                           // Nose
//                                                           .texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F, scale)
//                                                           // Ears Boni
//                                                           .texOffs(52, 0).addBox(-3.0F, -3.0F, -1.5F, 1, 5, 3, scale).texOffs(52, 0).addBox(4.0F, -3.0F, -1.5F, 1, 5, 3, scale)
//                                                           // Ears Small
//                                                           .texOffs(18, 0).addBox(-2.8F, -3.5F, -1.0F, 2, 1, 2, scale).texOffs(18, 0).addBox(2.8F, -3.5F, -1.0F, 2, 1, 2, scale), PartPose.ZERO);
//        var1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, scale), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
//        var1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, scale), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
//        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale);
//        var1.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
//        var1.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
//        var1.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-2.5F, 16.0F, -4.0F));
//        var1.addOrReplaceChild("left_front_leg", var4, PartPose.offset(0.5F, 16.0F, -4.0F));
//        PartDefinition var5 = var1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
//        var5.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale), PartPose.ZERO);
//        var5.addOrReplaceChild("real_tail_2", CubeListBuilder.create().texOffs(45, 0).addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, scale), PartPose.offset(0.0F, -2.0F, 0.0F));
//        var5.addOrReplaceChild("real_tail_bushy", CubeListBuilder.create().texOffs(43, 19).addBox(-1.0F, 0F, -2F, 3, 10, 3, scale), PartPose.ZERO);
//        return LayerDefinition.create(var0, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.upperTail/*, this.belly*/);
    }

    @Override
    public void prepareMobModel(T cat, float limbSwing, float limbSwingAmount, float partialTickTime) {
        CatHerder.LOGGER.debug("Preparing mob model: {}, limbSwing {}, limgSwingAmount {}, partialTickTime {}", cat, limbSwing, limbSwingAmount, partialTickTime);

        this.lowerTail.xRot = cat.getWagAngle(limbSwing, limbSwingAmount, partialTickTime);

        if(cat.isInSittingPose()) {
            CatHerder.LOGGER.debug("Cat is sitting");

            if(cat.isLying()) {
                CatHerder.LOGGER.debug("Cat is lying (like a Persian rug)");

//                this.head.setPos(-1, 19.5F, -7);
//                this.body.setPos(0, 20, 2);
//                this.body.xRot = (float) Math.PI / 2F;
//                this.belly.setPos(-1, 20, -2);
//                this.belly.xRot = this.body.xRot;
//                this.tail.setPos(-1, 18, 8);
//                this.legBackRight.setPos(-4.5F, 23, 7);
//                this.legBackRight.xRot = -(float) Math.PI / 2F;
//                this.legBackLeft.setPos(2.5F, 23, 7);
//                this.legBackLeft.xRot = -(float) Math.PI / 2F;
//                this.legFrontRight.setPos(-4.5F, 23, -4);
//                this.legFrontRight.xRot = -(float) Math.PI / 2F;
//                this.legFrontLeft.setPos(2.5F, 23, -4);
//                this.legFrontLeft.xRot = -(float) Math.PI / 2F;
            }
            else {
                CatHerder.LOGGER.debug("Cat is sitting.");

                this.head.setPos(0F, 10.5F, -7.0F);
//                this.belly.setPos(-1.0F, 16.0F, -3.0F);
//                this.belly.xRot = ((float) Math.PI * 2F / 5F);
//                this.belly.yRot = 0.0F;
//                this.body.setPos(0.0F, 18.0F, 0.0F);
                this.body.xRot = ((float) Math.PI / 4F);
                this.upperTail.setPos(0F, 21.0F, 5.0F);
                this.upperTail.xRot = -5F;
                this.legBackRight.setPos(-1.1F, 24.0F, 4.0F);
                this.legBackRight.xRot = ((float) Math.PI * 3F / 2F);
                this.legBackLeft.setPos(1.1F, 24.0F, 4.0F);
                this.legBackLeft.xRot = ((float) Math.PI * 3F / 2F);
                this.legFrontRight.xRot = 6.011947F; // 5.811947F;
                this.legFrontRight.setPos(-1.2F, 15.0F, -4.0F);
                this.legFrontLeft.xRot = 6.011947F; // 5.811947F;
                this.legFrontLeft.setPos(1.2F, 15.0F, -4.0F);
//
                this.body.setPos(0, 18.5F, 0);
                this.legFrontRight.yRot = 0;
                this.legFrontLeft.yRot = 0;
            }
        }
        else {
            CatHerder.LOGGER.debug("Cat is standing/walking.");

            this.body.setPos(0.0F, 16.5F, 0.0F);
            this.body.xRot = ((float) Math.PI / 2F);
//            this.belly.setPos(-1.0F, 14.0F, -3.0F);
//            this.belly.xRot = this.body.xRot;
            this.upperTail.setPos(0.0F, 14.5F, 7.0F);
            this.legBackRight.setPos(-1.1F, 17.0F, 7.0F);
            this.legBackLeft.setPos(1.1F, 17.0F, 7.0F);
            this.legFrontRight.setPos(1.2F, 13.0F, -5.0F);
            this.legFrontLeft.setPos(-1.2F, 13.0F, -5.0F);
            this.legBackRight.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legBackLeft.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontRight.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontLeft.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legBackRight.xRot = Mth.cos(limbSwing * 0.6662F) * 0.9F * limbSwingAmount;
            this.legBackLeft.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.9F * limbSwingAmount;
            this.legFrontRight.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.9F * limbSwingAmount;
            this.legFrontLeft.xRot = Mth.cos(limbSwing * 0.6662F) * 0.9F * limbSwingAmount;
//
            this.body.setPos(0F, 16.5F, 0F);
            this.head.setPos(0, 14.5F, -10F);
            this.legFrontRight.yRot = 0.0F;
            this.legFrontLeft.yRot = 0.0F;
        }

//        this.realHead.zRot = cat.getInterestedAngle(partialTickTime) + cat.getShakeAngle(partialTickTime, 0.0F);
//        this.belly.zRot = cat.getShakeAngle(partialTickTime, -0.08F);
        this.body.zRot = cat.getShakeAngle(partialTickTime, -0.16F);
        this.lowerTail.zRot = cat.getShakeAngle(partialTickTime, -0.2F);
//        this.realTail2.zRot = cat.getShakeAngle(partialTickTime, -0.2F);
//        this.realTail3.zRot = cat.getShakeAngle(partialTickTime, -0.2F);
    }

    @Override
    public void setupAnim(T catIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        CatHerder.LOGGER.debug("Setup cat animation: {}, limbSwing {}, limbSwingAmount {}, ageInTicks {}, netHeadYaw {}, headPitch {}", catIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * (catIn.isInSittingPose() && catIn.isLying() ? 0.005F : (float) Math.PI / 180F);
        this.lowerTail.xRot = ageInTicks;
    }

    public void setVisible(boolean visible) {
        this.head.visible = visible;
        this.body.visible = visible;
        this.legBackRight.visible = visible;
        this.legBackLeft.visible = visible;
        this.legFrontRight.visible = visible;
        this.legFrontLeft.visible = visible;
        this.upperTail.visible = visible;
//        this.belly.visible = visible;
    }
}
