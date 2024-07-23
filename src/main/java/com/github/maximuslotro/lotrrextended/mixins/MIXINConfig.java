package com.github.maximuslotro.lotrrextended.mixins;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MIXINConfig implements IMixinConfigPlugin {

    protected static TreeSet<String> injectedPatches = new TreeSet<String>();
    protected static boolean isBrokenJavaFutureVersions = false;

    @Override
    public void onLoad(String mixinPackage)
    {
    	ExtendedMixinLog.info("Running Java "+getJavaVersion());
    	if(getJavaVersion()>=9) {
    		ExtendedMixinLog.info("Applying Java 9+ Patches to Renewed");
    		isBrokenJavaFutureVersions=true;
    	}
    }

    @Override
    public String getRefMapperConfig()
    {
        return null;
    }

    @Override
    public List<String> getMixins()
    {
    	if(!isBrokenJavaFutureVersions) {
    		ExtendedMixinLog.info("Added Java 9+ Patches to Renewed");
//    		List<String> mixins = new ArrayList<>();
//    		mixins.add("lotr.client.event.MixinClientWorldDimensionTypeHelperJavaPatch");
//    		mixins.add("lotr.client.event.MixinLOTRTickHandlerClientJavaPatch");
//    		mixins.add("lotr.client.render.model.MixinHandheldWrapperModeljavaPatch");
//    		mixins.add("lotr.common.block.MixinSignSetupHelperJavaPatch");
//    		mixins.add("lotr.common.event.MixinLOTREventHandlerJavaPatch");
//    		mixins.add("lotr.common.init.MixinLOTRDimensionsJavaPatch");
//    		mixins.add("lotr.common.world.gen.tree.MixinLOTRTrunkPlacersJavaPatch");
//    		return mixins;
    	}
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets)
    {
        /* do nothing */
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
//    	if(targetClassName.equals("craftedMods.jeiLotr.JEIPlugin")){
//    		if(!classExists("craftedMods.jeiLotr.JEIPlugin")) {
//    			System.out.println("NOT ADDING MIXIN: "+targetClassName+", "+mixinClassName);
//    		}
//    	}
    	System.out.println("ADDING MIXIN: "+targetClassName+", "+mixinClassName);
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
}
