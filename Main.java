package com.game.battleship;


import java.util.Scanner;


public class Main {
    static View user1 = new View();
    static View user2 = new View();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Player 1, place your ships on the game field");
        user1.show(true);
        user1.initShipLocation();

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        System.out.println("Player 2, place your ships on the game field");
        user2.show(true);
        user2.initShipLocation();

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        while (true) {
            // user 1
            user2.show(false);
            System.out.println("---------------------");
            user1.show(true);
            System.out.println("Player 1, it's your turn:");

            if (user1.attack(user2)) break;
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();

            // user 2
            user1.show(false);
            System.out.println("---------------------");
            user2.show(true);
            System.out.println("Player 2, it's your turn:");

            if (user2.attack(user1)) break;
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}