package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ElfEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedElfWandererModel<E extends ElfEntity> extends ElfModel<E> {

    private final ModelRenderer cloak;

    public ExtendedElfWandererModel(boolean smallArms) {
        this(0.0F, false, smallArms);
    }

    public ExtendedElfWandererModel(float f) {
        this(f, true, false);
    }

    public ExtendedElfWandererModel(float f, boolean isArmor, boolean smallArms) {
        super(f, isArmor, smallArms);
        if (!isArmor) {
            this.createLongHairModel(0.0F, f);
        }

        this.cloak = new ModelRenderer(this, 0, 0);
        this.cloak.setTexSize(64, 32);
        this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, f);
    }

    /*
     Hide elven wanderers long hair because it is not visible under the hood
     */
    @Override
    protected void createLongHairModel(float headRotationY, float f) {
        this.hat = new ModelRenderer(this, 56, 0);
    }

    @Override
    public void setupAnim(E entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(entity, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);

        if (entity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
            if (entity.isCrouching()) {
                this.cloak.z = 1.4F;
                this.cloak.y = 1.85F;
            } else {
                this.cloak.z = 0.0F;
                this.cloak.y = 0.0F;
            }
        } else if (entity.isCrouching()) {
            this.cloak.z = 0.3F;
            this.cloak.y = 0.8F;
        } else {
            this.cloak.z = -1.1F;
            this.cloak.y = -0.85F;
        }
    }

    public void renderCloak(E elf, MatrixStack p_228289_1_, IVertexBuilder p_228289_2_, int p_228289_3_, int p_228289_4_) {
        this.cloak.render(p_228289_1_, p_228289_2_, p_228289_3_, p_228289_4_);
    }

}
