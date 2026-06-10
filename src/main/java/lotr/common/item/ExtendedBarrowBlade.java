package lotr.common.item;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedDaggerItem;

import lotr.common.init.LOTRMaterial;
import net.minecraft.item.ItemGroup;

public class ExtendedBarrowBlade extends ExtendedDaggerItem {

    public ExtendedBarrowBlade(ItemGroup pCategory) {
        super(LOTRMaterial.ARNOR.asTool(), pCategory);
    }
}