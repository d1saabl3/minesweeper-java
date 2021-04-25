package cz.educanet.minesweeper.logic;

import java.util.Random;

public class Field {
    private int rows;
    private int columns;
    private bombs[][] playground;

    public static Field generetField(int rows, int columns) {
        Field myField = new Field();
        myField.rows = rows;
        myField.columns = columns;
        myField.playground = new bombs[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                bombs genBombs = new bombs();
                genBombs.setType(0);
                genBombs.setBomb(false);

                myField.playground[j][i] = genBombs;
            }
        }
        return myField.generateBombs(myField);
    }

    public bombs getCellPosition(int x, int y) {
        return this.playground[x][y];
    }

    public int getBombCount() {
        return 10;
    }

    public Field generateBombs(Field myField) {

        Random r = new Random();
        int counter = 0;

        while (counter != getBombCount()) {
            int x = r.nextInt(columns);
            int y = r.nextInt(rows);

            while (playground[x][y].isBomb()) {
                x = r.nextInt(columns);
                y = r.nextInt(rows);
            }
            playground[x][y].setBomb(true);

            counter++;
        }
        return myField;
    }

    public boolean isClicked(int x, int y) {
        return playground[x][y].isBomb();
    }

    public void setCellType(int x, int y, int type) {
        bombs set_a = playground[x][y];
        set_a.setType(type);

        playground[x][y] = set_a;
    }

    public int getCellType(int x, int y) {
        bombs get_a = playground[x][y];

        return get_a.getType();
    }
}