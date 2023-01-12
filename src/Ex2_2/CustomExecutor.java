package Ex2_2;

import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    private final PriorityBlockingQueue<TaskType> types = new PriorityBlockingQueue<>();
    private int maxPriority = TaskType.OTHER.getPriorityValue();

    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors() / 2, Runtime.getRuntime().availableProcessors() - 1, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    public <T> FutureTask<T> submit(Callable<T> operation, TaskType type) {
        Task<T> task = Task.createTask(operation, type);
        types.add(task.getType());
        findMaxPriority();
        FutureTask<T> futureTask = new FutureTask<>(task);
        task.setFutureTask(futureTask);
        execute(futureTask);
        return futureTask;
    }

    public <T> FutureTask<T> submit(Callable<T> operation) {
        if (operation instanceof Task<T> task) {
            return submit(task, task.getType());
        }
        return submit(operation, TaskType.OTHER);
    }

    public int getCurrentMax() {
        return maxPriority;
    }

    public void findMaxPriority() {
        if (types.peek() == null) {
            return;
        }
        this.maxPriority = types.peek().getPriorityValue();
    }

    public void gracefullyTerminate() {

        super.shutdown();
        try {
            super.awaitTermination(300, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if (maxPriority > 0 && maxPriority <= 3)
            types.poll();
        findMaxPriority();
    }
}