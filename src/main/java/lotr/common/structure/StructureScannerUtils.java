package lotr.common.structure;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.ISeedReader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class StructureScannerUtils {
	public static CompoundNBT parseSignInfo(ISeedReader world, BlockPos blockPos) {
		try {
			TileEntity tileentity = world.getBlockEntity(blockPos);
			if (tileentity instanceof SignTileEntity) {
				ITextComponent[] imessage = getText((SignTileEntity) tileentity);
				String[] signText = getFormatted(imessage);
				if (signText[0].startsWith("id.")) {
					String[] message=(signText[0]).split("\\.");
					if(message[1].equals("entity")) {
						CompoundNBT type = new CompoundNBT();
						type.putString("entity", signText[1]);
						if(signText[3].startsWith("spawn.")) {
							String[] message1=(signText[3]).split("\\.");
							type.putString("spawn", message1[1]);
							return type;
						}
						ExtendedLog.error("FOUND INVALID SPAWN SIGN IN SCAN: "+blockPos.toString());
					}
					else if(message[1].equals("sign")&&message[2].equals("text")){
						CompoundNBT type = new CompoundNBT();
						type.putInt("signText", Integer.parseInt(message[3]));
						return type;
					}
				}else {
					ExtendedLog.error("FOUND EMPTY SIGN IN SCAN: "+blockPos.toString());
				}
			}
			else {
				ExtendedLog.error("INVALID SIGN ENTITY FOUND: "+blockPos.toString()+", NOT INSTANCEOF: "+SignTileEntity.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ITextComponent[] getText(SignTileEntity sign)
    {
        return ObfuscationReflectionHelper.getPrivateValue(SignTileEntity.class, sign,
                "field_145915_a");
    }

	private static String[] getFormatted(ITextComponent[] text)
    {
        String[] out = new String[text.length];
        for (int i = 0; i < text.length; i++) {
        	out[i] = text[i].getString();
        	//out[i] = text[i].getString().replace('\u00a7', '&');
        }
        return out;
    }
}
