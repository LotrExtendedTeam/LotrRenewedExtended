package com.github.maximuslotro.lotroExtended.mixins;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MIXINConfig implements IMixinConfigPlugin
{

    protected static TreeSet<String> injectedPatches = new TreeSet<String>();

    @Override
    public void onLoad(String mixinPackage)
    {
        /* do nothing */
    }

    @Override
    public String getRefMapperConfig()
    {
        return null;
    }

    @Override
    public List<String> getMixins()
    {
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

}
