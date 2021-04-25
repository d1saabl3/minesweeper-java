package cz.educanet.minesweeper.logic;

public class Minesweeper {

    private Field playground;
    private int rowsCount;
    private int columnsCount;
    
    private boolean bombClicked; 
    private int clearCells; 

    public Minesweeper(int rows, int columns) {
        this.rowsCount = rows;
        this.columnsCount = columns;

        this.playground = Field.generetField(rows, columns);
        clearCells = (rows * columns) - playground.getBombCount();
    }

    /**
     * 0 - Hidden
     * 1 - Visible
     * 2 - Flag
     * 3 - Question mark
     */
    public int getField(int x, int y) {
        return playground.getCellType(x, y);
    }

    public void toggleFieldState(int x, int y) { 
        if (getField(x, y) == 0) { 
            playground.setCellType(x, y, 2);
        } 
        else if (getField(x, y) == 2) { 
            playground.setCellType(x, y, 3);
        } 
        else if (getField(x, y) == 3) { 
            playground.setCellType(x, y, 0);
        }
    }

    public Field secondReveal(int x, int y, Field input) {
        Field playground = input;
        playground.getCellPosition(x, y).setType(1);

        if (getAdjacentBombCount(x, y) == 0) {
            boolean topRight = (x != columnsCount - 1) && (y != 0);
            boolean topLeft = (x != 0) && (y != 0);
            boolean bottomRight = (x != columnsCount - 1) && (y != rowsCount - 1);
            boolean bottomLeft = (x != 0) && (y != rowsCount - 1);

            if (topRight && !isBombOnPosition(x + 1, y - 1) && getField(x + 1, y - 1) == 0) { // Nahore Vpravo
                playground.getCellPosition(x + 1, y - 1).setType(1);
                secondReveal(x + 1, y - 1, input);
                clearCells--;
            }
            if (topLeft && !isBombOnPosition(x - 1, y - 1) && getField(x - 1, y - 1) == 0) { // Nahore vlevo
                playground.getCellPosition(x - 1, y - 1).setType(1);
                secondReveal(x - 1, y - 1, input);
                clearCells--;
            }
            if (bottomRight && !isBombOnPosition(x + 1, y + 1) && getField(x + 1, y + 1) == 0) { //Dole Vpravo
                playground.getCellPosition(x + 1, y + 1).setType(1);
                secondReveal(x + 1, y + 1, input);
                clearCells--;
            }
            if (bottomLeft && !isBombOnPosition(x - 1, y + 1) && getField(x - 1, y + 1) == 0) { // Dole Vlevo
                playground.getCellPosition(x - 1, y + 1).setType(1);
                secondReveal(x - 1, y + 1, input);
                clearCells--;
            }

        }
        return playground;
    }

    public void reveal(int x, int y) {
        if (playground.isClicked(x, y)) {
            bombClicked = true;
        } else {
            playground = secondReveal(x, y, playground);
            clearCells--;
        }
    }

    public int getAdjacentBombCount(int x, int y) {
        int bombs = 0;

        boolean topRight = (x != columnsCount - 1) && (y != 0);
        boolean topLeft = (x != 0) && (y != 0);
        boolean bottomRight = (x != columnsCount - 1) && (y != rowsCount - 1);
        boolean bottomLeft = (x != 0) && (y != rowsCount - 1);

        if (topRight && isBombOnPosition(x + 1, y - 1)) { // Nahore Vpravo
            bombs++;
        }
        if (topLeft && isBombOnPosition(x - 1, y - 1)) { // Nahore vlevo
            bombs++;
        }
        if (bottomRight && isBombOnPosition(x + 1, y + 1)) { //Dole Vpravo
            bombs++;
        }
        if (bottomLeft && isBombOnPosition(x - 1, y + 1)) { // Dole Vlevo
            bombs++;
        }
        return bombs;
    }


    public boolean isBombOnPosition(int x, int y) {
        return playground.getCellPosition(x, y).isBomb();
    }

    public boolean didWin() {
        return clearCells == 0;
    }

    public boolean didLoose() {
        return bombClicked;
    }

    public int getRows() {
        return rowsCount;
    }

    public int getColumns() {
        return columnsCount;
    }

}