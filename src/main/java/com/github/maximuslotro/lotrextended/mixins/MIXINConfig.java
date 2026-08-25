package com.github.maximuslotro.lotrextended.mixins;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;

public class MIXINConfig implements IMixinConfigPlugin {

	protected static TreeSet<String> injectedPatches = new TreeSet<>();
	protected static boolean isBrokenJavaFutureVersions = false;
	protected static int rejectedAOEMixins = 0;

	@Override
	public void onLoad(String mixinPackage) {
		ExtendedMixinLog.make();
		ExtendedMixinLog.info("Running Java "+getJavaVersion());
		if(getJavaVersion()>=9) {
			ExtendedMixinLog.info("Applying Java 9+ Patches to Renewed");
			isBrokenJavaFutureVersions=true;
		}
		MixinExtrasBootstrap.init();
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public List<String> getMixins() {
		if(isBrokenJavaFutureVersions) {
			ExtendedMixinLog.info("Added Java 9+ Patches to Renewed");
			/*
			List<String> mixins = new ArrayList<>();
			mixins.add("lotr.client.event.MixinClientWorldDimensionTypeHelperJavaPatch");
			mixins.add("lotr.client.event.MixinLOTRTickHandlerClientJavaPatch");
			mixins.add("lotr.client.render.model.MixinHandheldWrapperModelJavaPatch");
			mixins.add("lotr.common.block.MixinSignSetupHelperJavaPatch");
			mixins.add("lotr.common.event.MixinLOTREventHandlerJavaPatch");
			mixins.add("lotr.common.init.MixinLOTRDimensionsJavaPatch");
			mixins.add("lotr.common.world.gen.tree.MixinLOTRTrunkPlacersJavaPatch");
			return mixins;*/
		}
		return null;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	    //ExtendedMixinLog.debug("=== MY TARGETS ===");
	    for (String target : myTargets) {
	        ExtendedMixinLog.debug(target);
	    }

	    //ExtendedMixinLog.debug("=== OTHER TARGETS ===");
	    for (String target : otherTargets) {
	        ExtendedMixinLog.debug(target);
	    }
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return true;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
	{
		/* do nothing */
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
	{
		injectedPatches.add(mixinInfo.getName());
	}

	public static TreeSet<String> getInjectedPatches()
	{
		return injectedPatches;
	}

	public boolean classExists(String name) {
		try {
			if(Class.forName(name, false, this.getClass().getClassLoader()) != null){
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static int getJavaVersion() {
		String version = System.getProperty("java.version");
		if(version.startsWith("1.")) {
			version = version.substring(2, 3);
		} else {
			int dot = version.indexOf(".");
			if(dot != -1) { version = version.substring(0, dot); }
		} return Integer.parseInt(version);
	}

	public static boolean isBrokenJavaFutureVersions() {
		return isBrokenJavaFutureVersions;
	}
}