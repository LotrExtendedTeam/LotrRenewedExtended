package com.github.maximuslotro.lotrrextended.common.world.spawning;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.math.BlockPos;

public class ExtendedMobDensityTracker {
	   private final List<ExtendedMobDensityTracker.DensityEntry> charges = Lists.newArrayList();

	   public void addCharge(BlockPos pPos, double pCharge) {
	      if (pCharge != 0.0D) {
	         this.charges.add(new ExtendedMobDensityTracker.DensityEntry(pPos, pCharge));
	      }

	   }

	   public double getPotentialEnergyChange(BlockPos pPos, double pCharge) {
	      if (pCharge == 0.0D) {
	         return 0.0D;
	      } else {
	         double d0 = 0.0D;

	         for(ExtendedMobDensityTracker.DensityEntry mobdensitytracker$densityentry : this.charges) {
	            d0 += mobdensitytracker$densityentry.getPotentialChange(pPos);
	         }

	         return d0 * pCharge;
	      }
	   }

	   static class DensityEntry {
	      private final BlockPos pos;
	      private final double charge;

	      public DensityEntry(BlockPos pPos, double pCharge) {
	         this.pos = pPos;
	         this.charge = pCharge;
	      }

	      public double getPotentialChange(BlockPos pPos) {
	         double d0 = this.pos.distSqr(pPos);
	         return d0 == 0.0D ? Double.POSITIVE_INFINITY : this.charge / Math.sqrt(d0);
	      }
	   }
	}