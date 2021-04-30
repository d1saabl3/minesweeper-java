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

    public Field secondReveal(int x, int y, Field superMuzesFungovat) {
        playground = superMuzesFungovat;
        playground.getCellPosition(x, y).setType(1);

        if (getAdjacentBombCount(x, y) == 0) {
            boolean topRight = (x != columnsCount - 1) && (y != 0);
            boolean topLeft = (x != 0) && (y != 0);
            boolean bottomRight = (x != columnsCount - 1) && (y != rowsCount - 1);
            boolean bottomLeft = (x != 0) && (y != rowsCount - 1);

            if ((topRight || topLeft) && !isBombOnPosition(x, y - 1) && getField(x, y - 1) == 0) { // Nahore
                playground.getCellPosition(x, y - 1).setType(1);
                secondReveal(x, y - 1, superMuzesFungovat);
                clearCells--;
            }
            if (topRight && !isBombOnPosition(x + 1, y - 1) && getField(x + 1, y - 1) == 0) { // Nahore Vpravo
                playground.getCellPosition(x + 1, y - 1).setType(1);
                secondReveal(x + 1, y - 1, superMuzesFungovat);
                clearCells--;
            }
            if (topLeft && !isBombOnPosition(x - 1, y - 1) && getField(x - 1, y - 1) == 0) { // Nahore vlevo
                playground.getCellPosition(x - 1, y - 1).setType(1);
                secondReveal(x - 1, y - 1, superMuzesFungovat);
                clearCells--;
            }
            if ((bottomRight || bottomLeft) && !isBombOnPosition(x, y + 1) && getField(x, y + 1) == 0) { // Dole
                playground.getCellPosition(x, y + 1).setType(1);
                secondReveal(x, y + 1, superMuzesFungovat);
                clearCells--;
            }
            if (bottomRight && !isBombOnPosition(x + 1, y + 1) && getField(x + 1, y + 1) == 0) { //Dole Vpravo
                playground.getCellPosition(x + 1, y + 1).setType(1);
                secondReveal(x + 1, y + 1, superMuzesFungovat);
                clearCells--;
            }
            if (bottomLeft && !isBombOnPosition(x - 1, y + 1) && getField(x - 1, y + 1) == 0) { // Dole Vlevo
                playground.getCellPosition(x - 1, y + 1).setType(1);
                secondReveal(x - 1, y + 1, superMuzesFungovat);
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

        try {if ((topRight || topLeft) && isBombOnPosition(x, y - 1)) { // Uprostred nahore
            bombs++;
        }}catch (Exception e){
            System.out.println("UN error");
        }
        try {if ((topRight || bottomRight) && isBombOnPosition(x + 1, y)) { // Uprostred Vpravo
            bombs++;
        }}catch (Exception e) {
            System.out.println("UVP error");
        }
        try {if ((bottomRight || bottomLeft) && isBombOnPosition(x, y + 1)) { // Uprostred Dole
            bombs++;
        }}catch (Exception e) {
            System.out.println("UD error");
        }
        try {if ((bottomLeft || topLeft) && isBombOnPosition(x - 1, y)) { // Uprostred vlevo
            bombs++;
        }}catch (Exception e){
            System.out.println("UVL error");
        }
        try {if (topRight && isBombOnPosition(x + 1, y - 1)) { // Nahore Vpravo
            bombs++;
        }}catch (Exception e){
            System.out.println("NVP error");
        }
        try {if (topLeft && isBombOnPosition(x - 1, y - 1)) { // Nahore vlevo
            bombs++;
        }}catch (Exception e) {
            System.out.println("NVL error");
        }
        try {if (bottomRight && isBombOnPosition(x + 1, y + 1)) { //Dole Vpravo
            bombs++;
        }}catch (Exception e){
            System.out.println("DVP error");
        }
        try {if (bottomLeft && isBombOnPosition(x - 1, y + 1)) { // Dole Vlevo
            bombs++;
        }}catch (Exception e){
            System.out.println("DVL error");
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