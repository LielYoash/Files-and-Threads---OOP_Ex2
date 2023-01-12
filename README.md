# OBJECT ORIENTED PROGRAMMING - FILES AND THREADS

## Description
The following project is divided into two parts: 
- in part A - we will explore the differences between programs that use Threads, Threadpool and just run a specific set of commands. this will be done by 4 different methods - 
1. ```createTextFiles```
2. ```getNumOfLines```
3. ```getNumOfLinesThreads```
4. ```getNumOfFilesThreadPool```

- in Part B - here we used the concept of the Task type and added three different priorities in order to complete tasks by their priority and not by the time they entered the Queue.
also, we've created a CustomExecutor class that uses ThreadPoolExecutor implementation and improves it by adding the ability to use the new Task type we've created, which allows it to implement ThreadPool that considers the priority of the task and not by the priority of the current thread.



## Part A
as mentioned above we've created 4 different methods in this part:
- The first method called ```createTextFiles``` is a method made to create several text files and write a different number of lines in each one, at the end of the method it returns a String array populated with the names of all the files created.
- The second method called ```getNumOfLines``` receives the String array from the first method and returns the combined sum of the lines inside all the files created.
- The third method called ```getNumOfLinesThreads``` receives the String array from the first method and while using threads returns the combined sum of the lines inside all the files created.
- The fourth method called ```getNumOfFilesThreadPool``` receives the String array from the first method and while implementing threadpool return the combined sum of the lines inside all the files created.

We have also created a ```Main``` class to make sure all the methods listed above are working properly, inside this class we call each method and measure the time each of them worked then we ran those methods inside a loop up to 1000 times and divided each case by 1000 to get the average case and the most optimal results.

## Findings
We've tested the program ten different times with ten different numbers of files and lines:

- 10 files

![10](screenShots/10.png)

- 100 files

![100](screenShots/100.png)

- 200 files

![200](screenShots/200.png)

- 34 files

![34](screenShots/34.png)

- 78 files

![78](screenShots/78.png)

- 67 files

![67](screenShots/67.png)

- 42 files

![42](screenShots/42.png)

- 7 files

![7](screenShots/7.png)

- 16 files

![16](screenShots/16.png)

- 51 files

![51](screenShots/51.png)

From those results, we can see that the real competition is between the third method - ```getNumOfLinesThreads```, and the fourth method - ```getNumOfFilesThreadPool```. at the lower number of files, we can see that the ThreadPool is faster than normal Threads, but at the 50 files marker, we can see a power shift towards the method that uses Threads, while in each case the method that uses a specific set of command is left behind.

## Summary
From the result, we've received surprisingly it is faster to use Threads over Threadpool when the number of files is very large, yet it is faster to use ThreadPool when trying to run a small number of files.

## Part B
Here we will create two new types that extend the functionality of java's Concurrency Framework:
1. A generic task with a Type that returns a result and may throw an exception.
   Each task has a priority used for scheduling inferred from the integer value of the task Type.
2. A custom thread pool class that defines a method for submitting a generic task as described in
   section 1 to a priority queue, and a method for submitting a generic task created by a
   Callable<V> and a Type, passed as arguments.
### task 
a generic task with a type. each task has one of three priority types: ```COMPUTATIONAL```, ```IO``` and ```OTHER```. 

This class contains several methods: 
- ```private Task(Callable<T> callable, TaskType type)``` - Constructor.
- ``` public TaskType getType()``` - Returns the type of the task.
- ```public static <V> Task<V> createTask(Callable<V> task, TaskType type)```- Creates a task.
- ```public T call() throws Exception``` - Returns a callable object.
- ```public int compareTo(Task other)``` - Compares between two task types.
- ```public void setFutureTask(FutureTask<T> futureTask)``` - Sets a futureTask for the submit method.

### CustomExecutor
a Custom ThreadPool built of the existing java implementation of ThreadPoolExecutor while adding the ability to prioritize the tasks entering the ThreadPool.
it contains the following methods:
- ```public CustomExecutor()```- Constructor/
- ```public <T> FutureTask<T> submit(Callable<T> operation, TaskType type)```- Method that submits tasks into a priority Blocking Queue and has 2 variable fields.
- ```public <T> FutureTask<T> submit(Callable<T> operation)```- Method that submits tasks into a priority Blocking Queue and has 1 variable field.
- ```public int getCurrentMax()``` - Method that returns the highest priority in the queue at a given moment.
- ```public void findMaxPriority()``` - Method that assists getCurrentMax.
- ```public void gracefullyTerminate()``` - Method that terminates all the threads once the program is finished working.
- ```protected void beforeExecute(Thread t, Runnable r)``` - Methode that helps update the maxPriority value once the threads start working.

#### Hopefully, you found our project helpful
#### have a nice day!
