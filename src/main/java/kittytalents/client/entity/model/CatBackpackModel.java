package kittytalents.client.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CatBackpackModel extends ListModel<kittytalents.common.entity.CatEntity> {

    public ModelPart rightChest;
    public ModelPart leftChest;

    public CatBackpackModel(ModelPart box) {
        this.rightChest = box.getChild("right_chest");
        this.leftChest = box.getChild("left_chest");
        // TODO
//        this.rightChest = new ModelPart(this, 52, 0);
//        this.rightChest.addBox(2.0F, -1F, 0F, 2, 7, 4, scaleFactor);
//        this.rightChest.setPos(0.0F, 14.0F, 2.0F);
//        this.leftChest = new ModelPart(this, 52, 0);
//        this.leftChest.addBox(-4.0F, -1F, 0F, 2, 7, 4, scaleFactor);
//        this.leftChest.setPos(0.0F, 14.0F, 2.0F);
    }

    public static LayerDefinition createChestLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();

        var1.addOrReplaceChild("right_chest", CubeListBuilder.create().texOffs(52, 0).addBox(2.0F, -1F, 0F, 2, 7, 4), PartPose.offset(0.0F, 14.0F, 2.0F));
        var1.addOrReplaceChild("left_chest", CubeListBuilder.create().texOffs(52, 0).addBox(-4.0F, -1F, 0F, 2F, 7F, 4F), PartPose.offset(0.0F, 14.0F, 2.0F));

        return LayerDefinition.create(var0, 64, 32);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.rightChest, this.leftChest);
    }

    @Override
    public void prepareMobModel(kittytalents.common.entity.CatEntity dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        if (dogIn.isInSittingPose()) {
            if (dogIn.isLying()) {
                this.rightChest.setPos(0.0F, 20.0F, 2.0F);
                this.rightChest.xRot = ((float)Math.PI / 2F);
                this.leftChest.setPos(0.0F, 20.0F, 2.0F);
                this.leftChest.xRot = ((float)Math.PI / 2F);
            } else {
                this.rightChest.setPos(0.0F, 18.0F, 0.0F);
                this.rightChest.xRot = ((float)Math.PI / 4F);
                this.leftChest.setPos(0.0F, 18.0F, 0.0F);
                this.leftChest.xRot = ((float)Math.PI / 4F);
            }

        }
        else {
            this.rightChest.setPos(0.0F, 14.0F, 2.0F);
            this.rightChest.xRot = ((float)Math.PI / 2F);
            this.leftChest.setPos(0.0F, 14.0F, 2.0F);
            this.leftChest.xRot = ((float)Math.PI / 2F);

        }

        this.rightChest.zRot = dogIn.getShakeAngle(partialTickTime, -0.16F);
        this.leftChest.zRot = this.rightChest.zRot;
    }

    @Override
    public void setupAnim(kittytalents.common.entity.CatEntity dogIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
