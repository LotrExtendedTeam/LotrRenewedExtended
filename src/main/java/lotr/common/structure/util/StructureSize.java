package lotr.common.structure.util;

public class StructureSize{
	private int length;
	private int height;
	private int width;
	public StructureSize(int length, int height, int width) {
		this.length = length;
		this.height = height;
		this.width = width;
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
		return (Integer.compare(pos.length, length)==0&&Integer.compare(pos.height, height)==0&&Integer.compare(pos.width, width)==0);
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + length;
        hash = 17 * hash + height;
        hash = 17 * hash + width;
        return hash;
    }

	@Override
    public String toString() {
        return "[Length:"+length+", Height:"+height+", Width:"+width+"]";
    }

	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
