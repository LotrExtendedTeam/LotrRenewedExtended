package lotr.common.structure.util;

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
}
