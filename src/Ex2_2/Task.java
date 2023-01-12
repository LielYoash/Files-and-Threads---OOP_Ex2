package Ex2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<T> implements Callable<T>, Comparable<Task>{
    private final TaskType type;
    private final Callable<T> action;
    private FutureTask<T> futureTask;

    private Task(Callable<T> callable, TaskType type) {
        this.type = type;
        this.action = callable;
    }

    public TaskType getType() {
        return type;
    }

    public static <V> Task<V> createTask(Callable<V> task, TaskType type){
        return new Task<V>(task, type);
    }

    @Override
    public T call() throws Exception {

        return  action.call();
    }
    @Override
    public int compareTo(Task other) {
        if(this.type.getPriorityValue() > other.type.getPriorityValue()){
            return 1;
        }
        else if(this.type.getPriorityValue() < other.type.getPriorityValue()){
            return -1;
        }
        else {
            return 0;
        }
    }

    public void setFutureTask(FutureTask<T> futureTask) {
        this.futureTask = futureTask;
    }
}