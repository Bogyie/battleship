package com.game.battleship;


import java.util.ArrayList;
import java.util.stream.IntStream;


class Presenter {
    Model board = new Model();

    private ArrayList<Square> getNearLocation(Point p1, Point p2) {
        Point minPoint = new Point(Math.min(p1.rowIndex(), p2.rowIndex()),
                Math.min(p1.columnIndex(), p2.columnIndex()));

        Point maxPoint = new Point(Math.max(p1.rowIndex(), p2.rowIndex()),
                Math.max(p1.columnIndex(), p2.columnIndex()));

        Point minPaddedPoint = new Point(
                minPoint.rowIndex() > 0 ? minPoint.rowIndex() - 1 : 0,
                minPoint.columnIndex() > 0 ? minPoint.columnIndex() - 1 : 0);

        Point maxPaddedPoint = new Point(
                maxPoint.rowIndex() < 9 ? maxPoint.rowIndex() + 1 : 9,
                maxPoint.columnIndex() < 9 ? maxPoint.columnIndex() + 1 : 9);

        return board.getSquares(minPaddedPoint, maxPaddedPoint);
    }

    public String checkLocationEmpty(Point p1, Point p2, int size) {
        int minColumn = Math.min(p1.columnIndex(), p2.columnIndex());
        int maxColumn = Math.max(p1.columnIndex(), p2.columnIndex());
        int minRow = Math.min(p1.rowIndex(), p2.rowIndex());
        int maxRow = Math.max(p1.rowIndex(), p2.rowIndex());

        if (minColumn != maxColumn && minRow != maxRow) {
            return "WrongLocation";
        }

        if ((maxColumn - minColumn + maxRow - minRow + 1) != size) {
            return "WrongLength";
        }

        ArrayList<Square> nearLocation = getNearLocation(p1, p2);

        for (Square s : nearLocation) {
            if (s.data().isOccupied()) {
                return "TooClose";
            }
        }
        return "Success";
    }

    public boolean checkRealLocation(Point p) {
        if (p.columnIndex() < 0 | p.rowIndex() < 0) {
            return false;
        }
        return !(p.columnIndex() > 9 | p.rowIndex() > 9);
    }

    public void applyShipLocation(Point p1, Point p2, String name) {
        board.locateSquares(p1, p2, name);
    }

    public String dataTable(boolean isClear) {
        ArrayList<String> table = new ArrayList<>();
        table.add("  1 2 3 4 5 6 7 8 9 10");

        IntStream.range(0, 10).forEach(rowIndex -> {
            ArrayList<String> newRow = new ArrayList<>();

            newRow.add(Character.toString(65 + rowIndex));
            IntStream.range(0, 10).forEach(columnIndex ->
                    newRow.add(board.getSquareIndicator(rowIndex, columnIndex, isClear)));
            table.add(String.join(" ", newRow));
        });

        return String.join("\n", table);
    }

    public String attackPoint(Point p) {
        Data data = board.getSquare(p).data();
        data.setTorched(true);
        return data.getName();
    }

    public boolean allSink() {
        for (int rowIndex = 0; rowIndex < 10; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
                if (board.getSquareIndicator(rowIndex, columnIndex, true).equals("O")) {
                    return false;
                }
            }
        }
        return true;
    }
}
