package com.game.battleship;

import java.util.ArrayList;


record Point(int rowIndex, int columnIndex) {
}

record Square(Point point, Data data) {
}

class Data {
    private String name;
    //    private String indicator;
    private boolean torched;
    private boolean occupied;

    public Data(String name) {
        this.name = name;
//        this.indicator = indicator;
        this.occupied = false;
        this.torched = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndicator(boolean isClear) {
        if (torched) {
            if (occupied) return "X";
            else return "M";
        } else {
            if (isClear && occupied) return "O";
            else return "~";
        }
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isTorched() {
        return torched;
    }

    public void setTorched(boolean torched) {
        this.torched = torched;
    }
}

class Model {
    ArrayList<ArrayList<Square>> data = new ArrayList<>();

    public Model() {
        for (int rowIndex = 0; rowIndex < 10; rowIndex++) {
            ArrayList<Square> newRow = new ArrayList<>();
            for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
                newRow.add(new Square(
                        new Point(rowIndex, columnIndex),
                        new Data("fog")));
            }
            data.add(newRow);
        }
    }

    public Square getSquare(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public Square getSquare(Point p) {
        return data.get(p.rowIndex()).get(p.columnIndex());
    }

    public ArrayList<Square> getSquares(Point p1, Point p2) {
        Point minPoint = new Point(Math.min(p1.rowIndex(), p2.rowIndex()),
                Math.min(p1.columnIndex(), p2.columnIndex()));

        Point maxPoint = new Point(Math.max(p1.rowIndex(), p2.rowIndex()),
                Math.max(p1.columnIndex(), p2.columnIndex()));

        ArrayList<Square> result = new ArrayList<>();

        for (int row = minPoint.rowIndex(); row <= maxPoint.rowIndex(); row++) {
            for (int column = minPoint.columnIndex(); column <= maxPoint.columnIndex(); column++) {
                result.add(getSquare(row, column));
            }
        }
        return result;
    }

    public ArrayList<Data> getSquaresData(Point p1, Point p2) {
        ArrayList<Square> squares = getSquares(p1, p2);
        ArrayList<Data> squaresData = new ArrayList<>();
        squares.forEach(s -> squaresData.add(s.data()));
        return squaresData;
    }

    public String getSquareIndicator(int rowIndex, int columnIndex, boolean isClear) {
        return getSquare(rowIndex, columnIndex).data().getIndicator(isClear);
    }


    public void locateSquares(Point p1, Point p2, String name) {
        ArrayList<Data> squaresData = getSquaresData(p1, p2);
        squaresData.forEach(target -> {
            target.setName(name);
            target.setOccupied(true);
        });
    }
}
