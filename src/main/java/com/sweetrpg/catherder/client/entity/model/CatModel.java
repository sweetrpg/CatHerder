package com.sweetrpg.catherder.client.entity.model;

import com.google.common.collect.ImmutableList;
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
    public ModelPart belly;
    public ModelPart legBackRight;
    public ModelPart legBackLeft;
    public ModelPart legFrontRight;
    public ModelPart legFrontLeft;
    public ModelPart tail;
    public ModelPart realTail; //
//    public ModelPart realTail2; //
//    public ModelPart realTail3; //

    public CatModel(ModelPart box) {
//        this.realHead = this.head.getChild("real_head");
        this.body = box.getChild("body");
        this.head = this.body.getChild("head");
        this.belly = this.body.getChild("belly");
        this.legBackRight = this.body.getChild("right_hind_leg");
        this.legBackLeft = this.body.getChild("left_hind_leg");
        this.legFrontRight = this.body.getChild("right_front_leg");
        this.legFrontLeft = this.body.getChild("left_front_leg");
        this.tail = this.body.getChild("tail");
        this.realTail = this.tail.getChild("real_tail");
//        this.realTail2 = this.tail.getChild("real_tail_2");
//        this.realTail3 = this.tail.getChild("real_tail_bushy");
        // TODO
//        float f1 = 13.5F;
//
//        // COORDS
//        // x is left/right of the cat
//        // y is back and forward
//
//        //Head
//        this.head = new ModelPart(this, 0, 0);
//        this.head.addBox(-2.0F, -3.0F, -2.0F, 6, 6, 4, scaleFactor);
//        this.head.setPos(-1.0F, f1, -7.0F);
//
//        //Body
//        this.body = new ModelPart(this, 18, 14);
//        this.body.addBox(-3.0F, -2.0F, -3.0F, 6, 9, 6, scaleFactor);
//        this.body.setPos(0.0F, 14.0F, 2.0F);
//
//        //Mane
//        this.mane = new ModelPart(this, 21, 0);
//        this.mane.addBox(-3.0F, -3.0F, -3.0F, 8, 6, 7, scaleFactor);
//        this.mane.setPos(-1.0F, 14.0F, 2.0F);
//
//        //Limbs
//        this.legBackRight = new ModelPart(this, 0, 18);
//        this.legBackRight.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, scaleFactor);
//        this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
//        this.legBackLeft = new ModelPart(this, 0, 18);
//        this.legBackLeft.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, scaleFactor);
//        this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
//        this.legFrontRight = new ModelPart(this, 0, 18);
//        this.legFrontRight.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, scaleFactor);
//        this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
//        this.legFrontLeft = new ModelPart(this, 0, 18);
//        this.legFrontLeft.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, scaleFactor);
//        this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
//
//        //Tail1
//        this.tail = new ModelPart(this, 9, 18);
//        this.tail.addBox(-0.5F, 0.0F, -1.0F, 2, 8, 2, scaleFactor);
//        this.tail.setPos(-0.5F, 12.0F, 8.0F);
//
//        //Tail2
//        this.tail.texOffs(45, 0).addBox(0.0F, 0.0F, 0.0F, 2, 3, 1).setPos(90.0F, 0.0F, 0.0F);
//
//        //Tail3
//        this.tail.texOffs(43, 19).addBox(-1.0F, 0F, -2F, 3, 10, 3).setPos(-1.0F, 12.0F, 8.0F);
//
//        //HeadMain EarsNormal
//        this.head.texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2, 2, 1, scaleFactor);
//        this.head.texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2, 2, 1, scaleFactor);
//
//        //HeadMain EarsBoni
//        this.head.texOffs(52, 0).addBox(-3.0F, -3.0F, -1.5F, 1, 5, 3, scaleFactor);
//        this.head.texOffs(52, 0).addBox(4.0F, -3.0F, -1.5F, 1, 5, 3, scaleFactor);
//
//        //HeadMain EarsSmall
//        this.head.texOffs(18, 0).addBox(-2.8F, -3.5F, -1.0F, 2, 1, 2, scaleFactor);
//        this.head.texOffs(18, 0).addBox(2.8F, -3.5F, -1.0F, 2, 1, 2, scaleFactor);
//
//        //HeadMain Nose
//        this.head.texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3, 3, 4, scaleFactor);
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayerInternal(CubeDeformation.NONE);
    }

    public static LayerDefinition createArmorLayer() {
        return createBodyLayerInternal(new CubeDeformation(0.4F, 0.4F, 0.4F));
    }

    private static LayerDefinition createBodyLayerInternal(CubeDeformation scale) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(),
                                                               PartPose.offset(0.0F, 17.0F, 1.0F));

        PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(20, 0)
                                                                              .addBox(-2.0F, -8.0F, -3.0F, 4.0F, 16.0F, 6.0F, scale),
                                                      PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                                                                            .addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, scale)
                                                                            .texOffs(0, 24)
                                                                            .addBox(-1.5F, -0.0156F, -4.0F, 3.0F, 2.0F, 2.0F, scale)
                                                                            .texOffs(0, 10)
                                                                            .addBox(-2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, scale)
                                                                            .texOffs(6, 10)
                                                                            .addBox(1.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, scale),
                                                     PartPose.offset(0.0F, -2.0F, -10.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15)
                                                                            .addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, scale),
                                                     PartPose.offsetAndRotation(0.0F, -2.0F, 7.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition realTail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(4, 15)
                                                                                     .addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, scale),
                                                         PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition backLegL = body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(8, 13)
                                                                                         .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, scale),
                                                         PartPose.offset(1.1F, 1.0F, 6.0F));

        PartDefinition backLegR = body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(8, 13)
                                                                                          .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, scale),
                                                         PartPose.offset(-1.1F, 1.0F, 6.0F));

        PartDefinition frontLegL = body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(40, 0)
                                                                                           .addBox(-1.0F, -0.2F, -1.0F, 2.0F, 10.0F, 2.0F, scale),
                                                          PartPose.offset(1.2F, -3.0F, -5.0F));

        PartDefinition frontLegR = body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(40, 0)
                                                                                            .addBox(-1.0F, -0.2F, -1.0F, 2.0F, 10.0F, 2.0F, scale),
                                                          PartPose.offset(-1.2F, -3.0F, -5.0F));

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
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.tail, this.belly);
    }

    @Override
    public void prepareMobModel(T cat, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.realTail.xRot = cat.getWagAngle(limbSwing, limbSwingAmount, partialTickTime);

        if(cat.isInSittingPose()) {
            if(cat.isLying()) {
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
//
////                this.body.setRotationPoint(0.0F, 14.0F, 0.0F);
////                this.body.rotateAngleX = ((float)Math.PI / 2F);
////                this.mane.setRotationPoint(-1.0F, 19.0F, -3.0F);
////                this.mane.rotateAngleX = this.body.rotateAngleX;
////                this.head.setRotationPoint(-1.0F, 17.0F, -7.0F);
////
////                this.tail.setRotationPoint(-0.5F, 17.0F, 8.0F); // +4.0D
////                this.legBackRight.setRotationPoint(-4.5F, 20.0F, 7.0F);
////                this.legBackLeft.setRotationPoint(2.5F, 20.0F, 7.0F);
////                this.legFrontRight.setRotationPoint(-3.0F, 22.0F, -3.0F);
////                this.legFrontLeft.setRotationPoint(1.0F, 22.0F, -3.0F);
////
////                this.legBackRight.rotateAngleX = -(float)Math.PI / 2.6F;
////                this.legBackLeft.rotateAngleX = -(float)Math.PI / 2.6F;
////
////                this.legFrontRight.rotateAngleX = -(float)Math.PI / 2;
////                this.legFrontRight.rotateAngleY = (float)Math.PI / 10;
////                this.legFrontLeft.rotateAngleX = -(float)Math.PI / 2;
////                this.legFrontLeft.rotateAngleY = -(float)Math.PI / 10;
            }
            else if(cat.isLying()) {
//                this.body.setPos(0.0F, 19.0F, 2.0F);
//                this.body.xRot = ((float) Math.PI / 2F);
//                this.belly.setPos(-1.0F, 19.0F, -3.0F);
//                this.belly.xRot = this.body.xRot;
//                this.head.setPos(-1.0F, 17.0F, -7.0F);
//
//                this.tail.setPos(-0.5F, 17.0F, 8.0F); // +4.0D
//                this.legBackRight.setPos(-4.5F, 20.0F, 7.0F);
//                this.legBackLeft.setPos(2.5F, 20.0F, 7.0F);
//                this.legFrontRight.setPos(-3.0F, 22.0F, -3.0F);
//                this.legFrontLeft.setPos(1.0F, 22.0F, -3.0F);
//
//                this.legBackRight.xRot = -(float) Math.PI / 2.6F;
//                this.legBackLeft.xRot = -(float) Math.PI / 2.6F;
//
//                this.legFrontRight.xRot = -(float) Math.PI / 2;
//                this.legFrontRight.yRot = (float) Math.PI / 10;
//                this.legFrontLeft.xRot = -(float) Math.PI / 2;
//                this.legFrontLeft.yRot = -(float) Math.PI / 10;
            }
            else {
//                this.head.setPos(-1.0F, 13.5F, -7.0F);
//                this.belly.setPos(-1.0F, 16.0F, -3.0F);
//                this.belly.xRot = ((float) Math.PI * 2F / 5F);
//                this.belly.yRot = 0.0F;
//                this.body.setPos(0.0F, 18.0F, 0.0F);
//                this.body.xRot = ((float) Math.PI / 4F);
//                this.tail.setPos(-0.5F, 21.0F, 6.0F);
//                this.legBackRight.setPos(-2.5F, 22.0F, 2.0F);
//                this.legBackRight.xRot = ((float) Math.PI * 3F / 2F);
//                this.legBackLeft.setPos(0.5F, 22.0F, 2.0F);
//                this.legBackLeft.xRot = ((float) Math.PI * 3F / 2F);
//                this.legFrontRight.xRot = 5.811947F;
//                this.legFrontRight.setPos(-2.49F, 17.0F, -4.0F);
//                this.legFrontLeft.xRot = 5.811947F;
//                this.legFrontLeft.setPos(0.51F, 17.0F, -4.0F);
//
//                this.head.setPos(-1.0F, 13.5F, -7.0F);
//                this.legFrontRight.yRot = 0;
//                this.legFrontLeft.yRot = 0;
            }
        }
        else {
//            this.body.setPos(0.0F, 14.0F, 2.0F);
//            this.body.xRot = ((float) Math.PI / 2F);
//            this.belly.setPos(-1.0F, 14.0F, -3.0F);
//            this.belly.xRot = this.body.xRot;
//            this.tail.setPos(-0.5F, 12.0F, 8.0F);
//            this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
//            this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
//            this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
//            this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
            this.legBackRight.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legBackLeft.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontRight.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontLeft.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//
//            this.head.setPos(-1.0F, 13.5F, -7.0F);
            this.legFrontRight.yRot = 0.0F;
            this.legFrontLeft.yRot = 0.0F;
        }

//        this.realHead.zRot = cat.getInterestedAngle(partialTickTime) + cat.getShakeAngle(partialTickTime, 0.0F);
        this.belly.zRot = cat.getShakeAngle(partialTickTime, -0.08F);
        this.body.zRot = cat.getShakeAngle(partialTickTime, -0.16F);
//        this.realTail.zRot = cat.getShakeAngle(partialTickTime, -0.2F);
//        this.realTail2.zRot = cat.getShakeAngle(partialTickTime, -0.2F);
//        this.realTail3.zRot = cat.getShakeAngle(partialTickTime, -0.2F);
    }

    @Override
    public void setupAnim(T catIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * (catIn.isInSittingPose() && catIn.isLying() ? 0.005F : (float) Math.PI / 180F);
        this.realTail.xRot = ageInTicks;
    }

    public void setVisible(boolean visible) {
        this.head.visible = visible;
        this.body.visible = visible;
        this.legBackRight.visible = visible;
        this.legBackLeft.visible = visible;
        this.legFrontRight.visible = visible;
        this.legFrontLeft.visible = visible;
        this.tail.visible = visible;
        this.belly.visible = visible;
    }
}
