package com.github.maximuslotro.lotrrextended.common.init;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class ExtendedMaterials {
	public static final Material MOSS = new ExtendedMaterials.Builder(MaterialColor.PLANT).destroyOnPush().build();

	public static class Builder {
		private PushReaction pushReaction = PushReaction.NORMAL;
		private boolean blocksMotion = true;
		private boolean flammable;
		private boolean liquid;
		private boolean replaceable;
		private boolean solid = true;
		private final MaterialColor color;
		private boolean solidBlocking = true;

		public Builder(MaterialColor p_i48270_1_) {
			this.color = p_i48270_1_;
		}

		public ExtendedMaterials.Builder liquid() {
			this.liquid = true;
			return this;
		}

		public ExtendedMaterials.Builder nonSolid() {
			this.solid = false;
			return this;
		}

		public ExtendedMaterials.Builder noCollider() {
			this.blocksMotion = false;
			return this;
		}

		private ExtendedMaterials.Builder notSolidBlocking() {
			this.solidBlocking = false;
			return this;
		}

		protected ExtendedMaterials.Builder flammable() {
			this.flammable = true;
			return this;
		}

		public ExtendedMaterials.Builder replaceable() {
			this.replaceable = true;
			return this;
		}

		protected ExtendedMaterials.Builder destroyOnPush() {
			this.pushReaction = PushReaction.DESTROY;
			return this;
		}

		protected ExtendedMaterials.Builder notPushable() {
			this.pushReaction = PushReaction.BLOCK;
			return this;
		}

		public Material build() {
			return new Material(this.color, this.liquid, this.solid, this.blocksMotion, this.solidBlocking, this.flammable, this.replaceable, this.pushReaction);
		}
	}
}