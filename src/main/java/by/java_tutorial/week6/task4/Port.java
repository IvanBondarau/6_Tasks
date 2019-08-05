package by.java_tutorial.week6.task4;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Port {

    private ExecutorService docks;
    private final AtomicInteger curCargo = new AtomicInteger(0);

    public Port() {

        docks = Executors.newFixedThreadPool(5);

    }

    public void add(Ship ship) {

        System.out.println("Port: new " + ship);
        docks.submit(new Dock(ship));

    }

    public static String getDockNum() {
        return "Dock " + Thread.currentThread().getName().substring(Thread.currentThread().getName().length() - 1)
                + ": ";
    }

    public class Dock extends Thread {

        private Ship ship;

        public Dock(Ship ship) {
            this.ship = ship;
        }

        public void run() {

            System.out.println(getDockNum() + "New ship accepted " + ship);

            if (ship.isToLoad()) {
                if (curCargo.get() < ship.getCapacity()) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(getDockNum() + "[Ship" + ship.getId() + "] queued - not enough cargo");

                    docks.submit(new Dock(this.ship));
                } else {

                    synchronized (curCargo) {
                        curCargo.set(curCargo.get() - ship.getCapacity());
                    }
                    try {
                        for (int it  = 0; it < ship.getCapacity(); it++) {
                            System.out.println(getDockNum() + "Loading...[" + it
                                    + "/" + ship.getCapacity() + "]");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ship.setCargo(ship.getCapacity());
                }

            } else {
                try {
                    for (int it = 0; it < ship.getCargo(); it++) {
                        System.out.println(getDockNum() + "Unloading...[" + it + "/" + ship.getCargo() + "]");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

               synchronized (curCargo) {
                    curCargo.set(curCargo.get() + ship.getCargo());
               }
                ship.setCargo(0);
            }
        }

    }


    public int currentCargo() {
        return curCargo.get();
    }

}
