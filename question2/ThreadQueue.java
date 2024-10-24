import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ThreadQueue {
    private final Queue<Integer> queue = new LinkedList<>();

    public void enqueue(int value) {
        synchronized (this) {
            queue.add(value);
        }
    }

    public int dequeue() throws Exception {
        synchronized (this) {
            if (isEmpty()) {
                throw new Exception("queue is empty");
            }
            int value = queue.remove();
            return value;
        }

    }

    public boolean isEmpty() {
        synchronized (this) {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        ThreadQueue synchronizedQueue = new ThreadQueue();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                synchronizedQueue.enqueue(i);
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                try {
                    int value = synchronizedQueue.dequeue();
                } catch (NoSuchElementException e) {
                    System.out.println("Queue is empty");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
            
            System.out.println("Thread Finied");
            System.out.println("Queue: " + synchronizedQueue.queue);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
