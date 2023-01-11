package Ex2_2;


import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class CustomExecutor {

    ThreadFactory threadFactory;
    int core = Runtime.getRuntime().availableProcessors();
    ExecutorService pool = Executors.newFixedThreadPool(core / 2, threadFactory);


    /**
     * Here
     */
    private PriorityBlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private int availableProcessors = Runtime.getRuntime().availableProcessors();

    public CustomExecutor() {
        for (int i = 0; i < availableProcessors - 1; i++) {
            Thread thread = new Thread(() -> runTasks());
            threads.add(thread);
            thread.start();
        }
    }

    public FutureTask submit(Task task) {
        return submit(task, TaskType.OTHER);
    }

    /**
     * TODO check what the return type should be
     */
    public FutureTask submit(Callable operation, TaskType type) {
        /** insert task to queue */
        Task task;
        if (operation instanceof Task) {
            task = (Task) operation;
        } else {
            task = Task.createTask(operation, type);
        }
        taskQueue.add(task);


        FutureTask futureTask = new FutureTask<>(task);
        task.setFutureTask(futureTask);
        return futureTask;
    }

    private void runTasks() {
        while (true) {
            Task t = this.taskQueue.poll();
            t.getFutureTask().run();

        }
    }

    public String getCurrentMax() {
        return "hi";
    }

    public void gracefullyTerminate() {
        if (taskQueue.isEmpty()) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        } else {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gracefullyTerminate();
        }
    }

    class ExecutorThread extends Thread {
        @Override
        public void run() {
            Task poll = CustomExecutor.this.taskQueue.poll();
            try {
                assert poll != null;
                poll.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}