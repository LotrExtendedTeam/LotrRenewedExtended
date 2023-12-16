package lotr.common.entity.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class HobbitBartenderEntity extends HobbitEntity {

    public HobbitBartenderEntity(EntityType<? extends HobbitEntity> type, World w) {
        super(type, w);
    }

    /*
    Trade pools brought in from 1.7.10 for reference later
    HOBBIT_BARTENDER_BUY = (new LOTRTradeEntries(TradeType.BUY, new LOTRTradeEntry[] {
        new LOTRTradeEntry(new ItemStack(Items.field_151083_be), 7), new LOTRTradeEntry(new ItemStack(Items.field_151157_am), 7), new LOTRTradeEntry(new ItemStack(LOTRMod.muttonCooked), 7), new LOTRTradeEntry(new ItemStack(Items.field_151077_bg), 6), new LOTRTradeEntry(new ItemStack(Items.field_151025_P), 5), new LOTRTradeEntry(new ItemStack(LOTRMod.pipeweed, 4), 4), new LOTRTradeEntry(new ItemStack(LOTRMod.hobbitPipe), 15), new LOTRTradeEntry(new ItemStack(LOTRMod.mug), 2), new LOTRTradeEntry(new ItemStack(LOTRMod.ceramicMug), 2), new LOTRTradeEntry(new ItemStack(Items.field_151168_bH, 2), 7),
                new LOTRTradeEntry(new ItemStack(Items.field_151101_aQ), 6), new LOTRTradeEntry(new ItemStack(LOTRMod.mugChocolate), 4), new LOTRTradeEntry(new ItemStack(LOTRMod.mugAppleJuice), 6), new LOTRTradeEntry(new ItemStack(LOTRMod.mugOrangeJuice), 6), new LOTRTradeEntry(new ItemStack(LOTRMod.gammon), 7), new LOTRTradeEntry(new ItemStack(LOTRMod.deerCooked), 7), new LOTRTradeEntry(new ItemStack(LOTRMod.rabbitCooked), 6), new LOTRTradeEntry(new ItemStack(LOTRMod.rabbitStew), 10), new LOTRTradeEntry(new ItemStack(Items.field_151009_A), 10), new LOTRTradeEntry(new ItemStack(LOTRMod.mushroomPie), 10),
                new LOTRTradeEntry(new ItemStack(LOTRMod.leekSoup), 10), new LOTRTradeEntry(new ItemStack(LOTRMod.plate), 4), new LOTRTradeEntry(new ItemStack(LOTRMod.ceramicPlate), 2), new LOTRTradeEntry(new ItemStack(LOTRMod.hobbitRing), 14), new LOTRTradeEntry(new ItemStack(LOTRMod.hobbitOven), 40), new LOTRTradeEntry(new ItemStack(LOTRMod.hobbitTable), 100), new LOTRTradeEntry(new ItemStack(LOTRMod.mugAle, 1, 9999), 8), new LOTRTradeEntry(new ItemStack(LOTRMod.mugCider, 1, 9999), 8), new LOTRTradeEntry(new ItemStack(LOTRMod.mugPerry, 1, 9999), 8), new LOTRTradeEntry(new ItemStack(LOTRMod.hobbitPancake), 10) })).setVessels(LOTRFoods.HOBBIT_DRINK);
    HOBBIT_BARTENDER_SELL = new LOTRTradeEntries(TradeType.SELL, new LOTRTradeEntry[] {
        new LOTRTradeEntry(new ItemStack(Items.field_151082_bd), 3), new LOTRTradeEntry(new ItemStack(Items.field_151147_al), 3), new LOTRTradeEntry(new ItemStack(LOTRMod.muttonRaw), 3), new LOTRTradeEntry(new ItemStack(Items.field_151076_bf), 3), new LOTRTradeEntry(new ItemStack(LOTRMod.rabbitRaw), 3), new LOTRTradeEntry(new ItemStack(LOTRMod.deerRaw), 3), new LOTRTradeEntry(new ItemStack(LOTRMod.clayMug), 1), new LOTRTradeEntry(new ItemStack(LOTRMod.clayPlate), 1), new LOTRTradeEntry(new ItemStack(LOTRMod.pipeweedLeaf), 1), new LOTRTradeEntry(new ItemStack(Items.field_151174_bG, 2), 1),
                new LOTRTradeEntry(new ItemStack(LOTRMod.leek, 2), 1), new LOTRTradeEntry(new ItemStack(Items.field_151115_aP), 2), new LOTRTradeEntry(new ItemStack(LOTRMod.salt), 10), new LOTRTradeEntry(new ItemStack(LOTRMod.clover, 1, 1), 40), new LOTRTradeEntry(new ItemStack(Items.field_151133_ar), 3), new LOTRTradeEntry(new ItemStack(Items.field_151131_as), 4), new LOTRTradeEntry(new ItemStack(Items.field_151044_h, 2, 32767), 1) });
    */

    public boolean canTradeWith(PlayerEntity player) {
        return isFriendlyAndAligned(player);
    }
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HobbitEntity.regAttrs();
    }

    // TODO: Add GUI from right-click
}
