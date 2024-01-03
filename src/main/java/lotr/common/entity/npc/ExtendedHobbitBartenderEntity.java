package lotr.common.entity.npc;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ai.goal.*;
import lotr.common.init.ExtendedContainers;
import lotr.common.init.ExtendedSoundEvents;
import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.HashSet;
import java.util.UUID;

/**
 * @author RichardB122
 */
public class ExtendedHobbitBartenderEntity extends HobbitEntity implements ExtendedTraderEntity {

    HashSet<UUID> playersTradingWith = new HashSet<>();

    public ExtendedHobbitBartenderEntity(EntityType<? extends HobbitEntity> type, World w) {
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

    // Remove, drink, smoke and eat AIs
    protected void addNPCAI() {
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.initialiseAttackGoals(this.getAttackGoalsHolder());
        this.addNPCTargetingAI();

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<OrcEntity>(this, OrcEntity.class, 12.0F, 1.5, 1.8));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.6));
        this.goalSelector.addGoal(9, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(10, new ExtendedTraderTalkToCurrentGoal(this, this));
        this.goalSelector.addGoal(11, new ExtendedTraderFriendlyNPCConversationGoal(this, this, 0.001F));
        this.goalSelector.addGoal(12, new WatchSunriseSunsetGoal(this, 0.01F));
        this.goalSelector.addGoal(13, new ExtendedTraderWaterAvoidingRandomWalkingGoal(this, this, 1.1));
        this.goalSelector.addGoal(15, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.05F));
        this.goalSelector.addGoal(15, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.05F));
        this.goalSelector.addGoal(16, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(17, new LookRandomlyGoal(this));
    }

    public boolean canTradeWith(PlayerEntity player) {
        return isFriendlyAndAligned(player);
    }
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HobbitEntity.regAttrs();
    }

    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (canTradeWith(player)) {
            return ExtendedTraderHelper.openTraderGui(this, player, playersTradingWith);
        } else {
            player.sendMessage(new StringTextComponent("Your alignment is too low"), player.getUUID());
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void playTradeSound() {
        ExtendedTraderHelper.playTraderSound(level, getOnPos());
    }

    @Override
    public boolean isInteractingWithPlayer() {
        return ExtendedTraderHelper.isInteractingWithPlayer(playersTradingWith);
    }

    @Override
    public void removePlayerFromTrading(PlayerEntity player) {
        playersTradingWith.remove(player.getUUID());
    }

}
