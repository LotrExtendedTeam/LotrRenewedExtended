package lotr.common.block;

import net.minecraft.state.BooleanProperty;

public interface IExtendedSwappableTexture {
	/**
	 * Value to determine if block should use vanilla-ish textures or lotr textures
	 * True means use lotr texture (eg. full grass slab)
	 * False means use vanilla-ish texture (eg. half vanilla grass block slab)
	 * */
    public static final BooleanProperty USELOTRTEXTURE = BooleanProperty.create("lotrtexture");

}
