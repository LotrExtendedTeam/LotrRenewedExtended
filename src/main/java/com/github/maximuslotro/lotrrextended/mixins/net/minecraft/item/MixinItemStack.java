package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.item;

import com.google.gson.JsonParseException;
import lotr.common.util.LOTREnchantUtils;
import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@Mixin(ItemStack.class)
public class MixinItemStack {

    @Shadow
    public CompoundNBT getTagElement(String pKey) {
        return null;
    }

    @Shadow
    public Item getItem() {
        return null;
    }

    @Shadow(remap = true)
    @Nullable
    public CompoundNBT getTag() {
        return null;
    }

    /*
    @Shadow
    private CompoundNBT tag;
     */

    @Overwrite(remap = true)
    public ITextComponent getHoverName() {
        CompoundNBT compoundnbt = this.getTagElement("display");
        if (compoundnbt != null && compoundnbt.contains("Name", 8)) {
            try {
                ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(compoundnbt.getString("Name"));
                if (itextcomponent != null) {
                    return itextcomponent;
                }

                compoundnbt.remove("Name");
            } catch (JsonParseException jsonparseexception) {
                compoundnbt.remove("Name");
            }
        }

        System.out.println("Mixin is running!!!!!!!");
        /*if (this.getTag() != null && this.getTag().contains("LOTREnchantments", 9)) {
            assert this.getTag() != null;
            System.out.println("Assert passed!!!!!!!");
            ListNBT listnbt = this.getTag().getList("LOTREnchantments", 10);
            if (!listnbt.isEmpty()) {
                System.out.println("Not empty!!!!!!!");
                ItemStack cStack = (ItemStack) (Object) this;

                List<LOTREnchantment> enchants = LOTREnchantUtils.getEnchantList(cStack);
                ITextComponent newName = new StringTextComponent("");
                for (LOTREnchantment enchant : enchants) {
                    newName = newName.copy().append(new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase())
                            .append(" ").withStyle(style -> style.withItalic(false)));
                }
                System.out.println("Made it to return statement!!!!!!!");
                newName = newName.copy().append(baseName.copy().withStyle(style -> style.withItalic(false)));
                return newName;
            } else {
                System.out.println("Supposedly empty!!!!");
                System.out.println(listnbt);
                System.out.println(Arrays.toString(listnbt.toArray()));
            }
        }*/
        ItemStack cStack = (ItemStack) (Object) this;
        List<LOTREnchantment> enchants = LOTREnchantUtils.getEnchantList(cStack);
        if (!enchants.isEmpty()) {
            System.out.println("Not empty!!!!!!!");
            ITextComponent baseName = getItem().getName(cStack);
            ITextComponent newName = new StringTextComponent("");
            for (LOTREnchantment enchant : enchants) {
                newName = newName.copy().append(new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase())
                        .append(" ").withStyle(style -> style.withItalic(false)));
            }
            System.out.println("Made it to return statement!!!!!!!");
            newName = newName.copy().append(baseName.copy().withStyle(style -> style.withItalic(false)));
            return newName;
        } else {
            System.out.println("Supposedly empty!!!!");
        }

        return this.getItem().getName(cStack);
    }

}
