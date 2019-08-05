package by.java_tutorial.week6.task4;

public class Ship {

    private static int idCounter = 0;
    private int id;
    private int capacity;
    private int cargo;
    private boolean toLoad;

    public Ship(int capacity, int cargo, boolean toLoad) {
        this.id = idCounter++;
        this.capacity = capacity;
        this.cargo = cargo;
        this.toLoad = toLoad;
    }

    public int getId() {
        return id;
    }

    public boolean isToLoad() {
        return toLoad;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", cargo=" + cargo +
                ", toLoad=" + toLoad +
                '}';
    }
}
