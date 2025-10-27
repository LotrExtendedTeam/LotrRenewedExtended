package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ElfEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedCloakingElfModel<E extends ElfEntity> extends ElfModel<E> {

    private final ModelRenderer cape;

    public ExtendedCloakingElfModel(boolean smallArms) {
        this(0.0F, false, smallArms);
    }

    public ExtendedCloakingElfModel(float f) {
        this(f, true, false);
    }

    public ExtendedCloakingElfModel(float f, boolean isArmor, boolean smallArms) {
        super(f, isArmor, smallArms);
        if (!isArmor) {
            this.createLongHairModel(0.0F, f);
        }

        this.cape = new ModelRenderer(this, 0, 0);
        this.cape.setTexSize(64, 32);
        this.cape.addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, f);
    }

    @Override
    public void setupAnim(E entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(entity, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);

        if (entity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
            if (entity.isCrouching()) {
                this.cape.z = 1.4F;
                this.cape.y = 1.85F;
            } else {
                this.cape.z = 0.0F;
                this.cape.y = 0.0F;
            }
        } else if (entity.isCrouching()) {
            this.cape.z = 0.3F;
            this.cape.y = 0.8F;
        } else {
            this.cape.z = -1.1F;
            this.cape.y = -0.85F;
        }
    }

    public void renderCape(E elf, MatrixStack p_228289_1_, IVertexBuilder p_228289_2_, int p_228289_3_, int p_228289_4_) {
        this.cape.render(p_228289_1_, p_228289_2_, p_228289_3_, p_228289_4_);
    }

}
