package lotr.common.structureV4.util;

import lotr.common.structureV4.StructureFile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class StructureSize{
	private int width;
	private int height;
	private int depth;
	public StructureSize(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	@Override
	public boolean equals(Object o) { 
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof StructureSize)) {
            return false;
        }
        StructureSize pos = (StructureSize) o;
		return (Integer.compare(pos.width, width)==0&&Integer.compare(pos.height, height)==0&&Integer.compare(pos.depth, depth)==0);
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + width;
        hash = 17 * hash + height;
        hash = 17 * hash + depth;
        return hash;
    }

	@Override
    public String toString() {
        return "[Width:"+width+", Height:"+height+", Depth:"+depth+"]";
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDepth() {
		return depth;
	}

	public StructureBoxValues makeStructureBox(ListNBT listNBT) {
		return new StructureBoxValues(listNBT);
	}

	public class StructureBoxValues{
		public int widthLowX = Integer.MAX_VALUE;
		public int widthHighX = Integer.MIN_VALUE;
		public int heightLowY = Integer.MAX_VALUE;
		public int heightHighY = Integer.MIN_VALUE;
		public int depthLowZ = Integer.MAX_VALUE;
		public int depthHighZ = Integer.MIN_VALUE;

		public StructureBoxValues(ListNBT listNBT) {
			for (int i = 0; i < listNBT.size(); i++) {
				CompoundNBT nbt = listNBT.getCompound(i);
				RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
				if(rpos.getX()<widthLowX) {
					widthLowX = rpos.getX();
				}
				if(rpos.getX()>widthHighX) {
					widthHighX = rpos.getX();
				}
				if(rpos.getY()<heightLowY) {
					heightLowY = rpos.getY();
				}
				if(rpos.getY()>heightHighY) {
					heightHighY = rpos.getY();
				}
				if(rpos.getZ()<depthLowZ) {
					depthLowZ = rpos.getZ();
				}
				if(rpos.getZ()>depthHighZ) {
					depthHighZ = rpos.getZ();
				}
			}
		}
	}
}
