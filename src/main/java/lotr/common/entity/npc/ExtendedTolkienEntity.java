package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.HobbitSmokeGoal;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRItems;
import lotr.common.item.PouchItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class ExtendedTolkienEntity extends ManEntity implements ExtendedNPCEntitySettings {
	public ExtendedTolkienEntity(EntityType<ExtendedTolkienEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return ManEntity.regAttrs().add(Attributes.MAX_HEALTH, 1000.0).add(Attributes.ATTACK_SPEED, 20).add(Attributes.ARMOR, 20)
			.add(Attributes.ARMOR_TOUGHNESS, 20).add(Attributes.ATTACK_DAMAGE, 2);
	}

	@Override
	protected NPCNameGenerator getNameGenerator() {
		return NPCNameGenerators.NAMELESS_THING;
	}

	@Override
	protected Goal createAttackGoal() {
		return new NPCMeleeAttackGoal(this, 2.1D);
	}

	@Override
	protected void addNPCTargetingAI() {
		this.addNonAggressiveTargetingGoals();
	}

	@Override
	protected NPCFoodPool getEatPool() {
		return NPCFoodPools.BREE;
	}

	@Override
	protected NPCFoodPool getDrinkPool() {
		return NPCFoodPools.BREE_DRINK;
	}

	@Override
	protected void addConsumingGoals(int prio) {
		super.addConsumingGoals(prio);
		this.goalSelector.addGoal(prio, new HobbitSmokeGoal(this, 200));
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		if(player.getMainHandItem().getItem()==Items.BOOK) {
			player.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
			ItemStack small = new ItemStack(LOTRItems.SMALL_POUCH.get());
			ItemStack medium = new ItemStack(LOTRItems.MEDIUM_POUCH.get());
			PouchItem.setPouchDyedByColor(small, 7239042);
			PouchItem.setPouchDyedByColor(medium, 7239042);
			PouchItem.setPouchDyedByColor(small, 7239042);
			this.npcDropItem(small, 1, false, true);
			this.npcDropItem(medium, 1, false, true);
			this.npcDropItem(small, 1, false, true);
			ActionResultType.sidedSuccess(player.level.isClientSide());
		}
		return super.mobInteract(player, hand);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.RED_BOOK.get()));
		this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.SMOKING_PIPE.get()));
		this.personalInfo.setMale(true);
		return spawnData;
	}

	public static boolean spawn(World world, PlayerEntity player) {
		// only make a max of 16 attempts to spawn the traveling trader
		boolean spawned=false;
		for (int attempts = 0; attempts < 16; attempts++) {
			// pick a random angle and distance from the player
			float angle = world.random.nextFloat() * 360.0F;
			int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
			int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
			int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
			BlockPos pos = new BlockPos(x, y, z);

			// if the type is invalid try again but add a few attempts
			EntityType<?> type = ExtendedEntities.TOLKIEN.get();

			BlockPos plusOneY = new BlockPos(x, y + 1, z);
			if (y > 62 && !world.getBlockState(pos).isSuffocating(world, pos) && !world.getBlockState(plusOneY).isSuffocating(world, plusOneY)) {
				BlockPos spawnPos = pos.offset(new Vector3i(0.5D, 0, 0.5D));
				Entity tolkien = type.spawn((ServerWorld) world, null, null, spawnPos, SpawnReason.EVENT, true, true);
				if(tolkien!=null) {
					world.addFreshEntity(tolkien);
					player.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_you", tolkien.getName().copy().withStyle(TextFormatting.YELLOW)), Util.NIL_UUID);
					spawned=true;
				}
			}
			if(spawned) {
				break;
			}
		}
		return spawned;
	}
}
