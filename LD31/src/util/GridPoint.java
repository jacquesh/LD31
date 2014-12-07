package util;

public class GridPoint implements Comparable
{
    public int x;
    public int y;
    public float priority;

    public GridPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
        priority = (float)Math.random();
    }

    public int compareTo(Object other)
    {
        if(!(other instanceof GridPoint))
            return -1;
        return (int)Math.signum(this.priority - ((GridPoint)other).priority);
    }

    public boolean equals(Object other)
    {
        if(!(other instanceof GridPoint))
            return false;

        GridPoint otherGP = (GridPoint)other;
        return otherGP.x==this.x && otherGP.y==this.y;
    }
}
