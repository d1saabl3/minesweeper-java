package cz.educanet.minesweeper.logic;
public class bombs {

    private boolean isABomb;
    private int typ;

    public int getType() {
        return typ;
    }

    public void setType(int type) {
        this.typ = type;
    }

    public boolean isBomb() {
        return isABomb;
    }

    public void setBomb(boolean bomb) {
        isABomb = bomb;
    }
}