package by.java_tutorial.week6.task4;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Port port = new Port();
        Random rnd = new Random();


        while (true) {
            System.out.println();
            System.out.println("Current port cargo: " + port.currentCargo());

            double chance = rnd.nextDouble();
            boolean toLoad = chance < 0.4d  ;

            if (toLoad) {
                int capacity = rnd.nextInt(10 ) + 1;
                port.add(new Ship(capacity, 0, true));
            } else {
                int capacity = rnd.nextInt(10 ) + 1;
                int cargo = rnd.nextInt(capacity) + 1;
                port.add(new Ship(capacity, cargo, false));
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
