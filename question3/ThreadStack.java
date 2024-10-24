import java.util.Random;
import java.util.Stack;

public class ThreadStack {
    private final Stack<Integer> stack = new Stack<>();

    public synchronized void push(int value) throws InterruptedException {        
        while (stack.size() == 10) {
            System.out.println("stack is full");
            wait();
        }
        stack.push(value);
        System.out.println("added: " + value);
        notifyAll();
    }

    public synchronized int pop() throws InterruptedException {
        while (stack.isEmpty()) {
            System.out.println("Stack is empty");
            wait();
        }
        int value = stack.pop();
        System.out.println("removed: " + value);
        notifyAll();
        return value;
    }

    public static void main(String[] args) {
        ThreadStack synchronizedStack = new ThreadStack();

        Thread producer = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                try {
                    int value = random.nextInt(100) + 1;
                    synchronizedStack.push(value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    synchronizedStack.pop();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();

            System.out.println("thread finised");
            System.out.println("stack: " + synchronizedStack.stack);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
