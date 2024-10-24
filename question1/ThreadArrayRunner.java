import java.util.ArrayList;
import java.util.List;

class TheadArrayRunner {
    private static List<Integer> list = new ArrayList<Integer>();

    public static void main(String[] args) {
        Thread addThread = new Thread(() -> addElement());
        Thread removeThread = new Thread(() -> removeElement());

        addThread.start();
        removeThread.start();

        try {
            addThread.join();
            removeThread.join();
        } catch (InterruptedException e) {
            System.out.println("an error occured");
            System.out.println(e.getMessage());
        }
        System.out.println("program ended");
    }

    private static void addElement() {
        for (int i = 0; i < 1000; i++) {
            list.add(i);
            System.out.println("Adding: " + i);
        }
    }

    private static void removeElement() {
        for (int i = 0; i < 1000; i++) {
            int removed = list.remove(0);
            System.out.println("Removed: " + removed);
        }
    }

}