package com.game.battleship;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


class Ship {
    int size;
    String indicator;
    int health;

    public Ship(int size, String indicator) {
        this.size = size;
        this.health = size;
        this.indicator = indicator;
    }
}

class ShipInfo {
    HashMap<String, Ship> shipData = new HashMap<>();
    ArrayList<String> shipList = new ArrayList<>();

    public ShipInfo() {
        shipData.put("Aircraft Carrier", new Ship(5, "O"));
        shipData.put("Battleship", new Ship(4, "O"));
        shipData.put("Submarine", new Ship(3, "O"));
        shipData.put("Cruiser", new Ship(3, "O"));
        shipData.put("Destroyer", new Ship(2, "O"));

        shipList.add("Aircraft Carrier");
        shipList.add("Battleship");
        shipList.add("Submarine");
        shipList.add("Cruiser");
        shipList.add("Destroyer");
    }
}

class View {
    Presenter presenter = new Presenter();
    ShipInfo shipInfo = new ShipInfo();
    Scanner scanner = new Scanner(System.in);

    public Point stringToPoint(String s) { // A1 -> Point(0, 0)
        return new Point(s.charAt(0) - 65, Integer.parseInt(s.substring(1)) - 1);
    }

    public void show(boolean isClear) {
        System.out.println(presenter.dataTable(isClear));
    }

    public void setShipLocation(String name) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n", name, shipInfo.shipData.get(name).size);
        boolean isDone = false;

        while (!isDone) {
            Point p1 = stringToPoint(scanner.next());
            Point p2 = stringToPoint(scanner.next());

            switch (presenter.checkLocationEmpty(p1, p2, shipInfo.shipData.get(name).size)) {
                case "WrongLocation":
                    System.out.println("Error! Wrong ship location! Try again:");
                    break;
                case "WrongLength":
                    System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
                    break;
                case "TooClose":
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    break;
                case "Success":
                    isDone = true;
                    presenter.applyShipLocation(p1, p2, name);
                    break;
            }
        }
    }

    public void initShipLocation() {
        for (String shipName : shipInfo.shipList) {
            setShipLocation(shipName);
            show(true);
        }
    }

    public boolean attack(View user) {
        System.out.println("Take a shot!");
        Point p = stringToPoint(scanner.next());

        while (!presenter.checkRealLocation(p)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            p = stringToPoint(scanner.next());
        }

        String name = user.presenter.attackPoint(p);
        if (shipInfo.shipData.containsKey(name)) {
            System.out.println("You hit a ship!");
            shipInfo.shipData.get(name).health -= 1;
            if (shipInfo.shipData.get(name).health == 0) {
                System.out.println("You sank a ship!");
            }
        } else {
            System.out.println("You missed!");
        }

        return user.presenter.allSink();
    }

}
