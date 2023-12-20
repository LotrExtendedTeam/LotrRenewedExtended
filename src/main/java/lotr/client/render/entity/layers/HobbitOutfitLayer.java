package lotr.client.render.entity.layers;

import java.util.function.Predicate;
import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.ArmsStyleModelProvider;
import lotr.client.render.entity.model.HobbitModel;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.AbstractMannishEntity;
import lotr.common.entity.npc.HobbitEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class HobbitOutfitLayer<E extends HobbitEntity> extends NPCOutfitLayer<E, HobbitModel<E>> {
    public HobbitOutfitLayer(IEntityRenderer<E, HobbitModel<E>> renderer, RandomTextureVariants skins, EquipmentSlotType slot) {
        super(renderer, newHobbitOutfitModel(), skins, slot);
    }

    public HobbitOutfitLayer(IEntityRenderer<E, HobbitModel<E>> renderer, RandomTextureVariants skins, EquipmentSlotType slot, float prop) {
        super(renderer, newHobbitOutfitModel(), skins, slot, prop);
    }

    public HobbitOutfitLayer(IEntityRenderer<E, HobbitModel<E>> renderer, RandomTextureVariants skins, EquipmentSlotType slot, float prop, Predicate<E> gender) {
        super(renderer, newHobbitOutfitModel(), skins, slot, prop, gender);
    }

    private static final <E extends HobbitEntity> ArmsStyleModelProvider<E, HobbitModel<E>> newHobbitOutfitModel() {
        return (smallArms) -> {
            return new HobbitModel<>(0F, false, smallArms);
        };
    }
}
