package lotr.common.structure.util;

public class RelativePosition{
	private int X;
	private int Y;
	private int Z;
	public RelativePosition(int X, int Y, int Z) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}

	@Override
	public boolean equals(Object o) { 
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof RelativePosition)) {
            return false;
        }
        RelativePosition pos = (RelativePosition) o;
		return (Integer.compare(pos.X, X)==0&&Integer.compare(pos.Y, Y)==0&&Integer.compare(pos.Z, Z)==0);
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + X;
        hash = 17 * hash + Y;
        hash = 17 * hash + Z;
        return hash;
    }

	@Override
    public String toString() {
        return "[X:"+X+", Y:"+Y+", Z:"+Z+"]";
    }

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public int getZ() {
		return Z;
	}
}
