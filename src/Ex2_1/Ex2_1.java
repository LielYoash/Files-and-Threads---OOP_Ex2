package Ex2_1;
import java.io.*;
import java.util.Random;
import java.lang.Thread;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Ex2_1 {

    /**
     * The following method creates n text files with a random number of lines
     * using a random number in order to insert a different amount of line in each file
     * then returning an array of strings-the names of the files.
     *
     * @param n     = The number of files to create.
     * @param seed  = Variable used to initialize the random number generator.
     * @param bound = Variable used to stop the random number generator.
     * @return String[]
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String fileName = "file_";
        String fileType = "txt";
        String[] names = new String[n];
        try {
            for (int i = 1; i <= n; i++) {
                FileOutputStream file = new FileOutputStream(fileName + i + "." + fileType);
                Random rand = new Random(i);
                int x = rand.nextInt(bound);
                for (int j = 1; j <= x; j++) {
                    file.write(("hello world\n").getBytes());
                }
                names[i - 1] = fileName + i + "." + fileType;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The following method receives an array of file names of String type, accesses those files
     * and counts the number of lines inside them
     *
     * @param fileNames= the String array of the name of the files
     * @return the sum of all the lines inside all the files.
     */
    public static int getNumOfLines(String[] fileNames) {
        int sum = 0;
        try {
            for (String name : fileNames) {
                FileInputStream file = new FileInputStream(name);
                for (int i = 0; i != -1; i = file.read())
                    if (i == '\n') {
                        sum += 1;
                    }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The following method receives an array of file names of String type, accesses those files
     * and counts the number of lines inside them using threads
     * in addition creating an anonymous Thread class called MyThread that extends Thread
     * in order to better handle our threads behavior
     *
     * @param fileNames = the String array of the name of the files
     * @return sum of all lines
     */
    public int getNumOfLinesThreads(String[] fileNames) {
        MyThread[] threads = new MyThread[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            MyThread thread = new MyThread(fileNames[i]);
            thread.start();
            threads[i] = thread;
        }
        for (MyThread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return MyThread.ctr.get();
    }

    public class MyThread extends Thread {
        static AtomicInteger ctr = new AtomicInteger(0);
        String s;


        public MyThread(String t1) {
            super();
            this.s = t1;
        }

        @Override
        public void run() {
            try {
                FileInputStream file = new FileInputStream(s);
                for (int i = 0; i != -1; i = file.read()) {
                    if (i == '\n') {
                        ctr.incrementAndGet();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The following method receives an array of file names of String type, accesses those files
     * and counts the number of lines inside them using Threadpool- creating new threadpool class and returning
     * the wanted value using the call method
     *
     * @param fileNames = the String array of the name of the files
     * @return sum of all lines
     */
    public int getNumOfLinesThreadPool(String[] fileNames) {
        ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);
        int sum = 0;
        LinkedBlockingQueue<Future<Integer>> futures = new LinkedBlockingQueue<>() {
        };
        for (int i = 0; i < fileNames.length; i++) {
            Callable<Integer> t = new ThreadPool(fileNames[i]);
            futures.add(pool.submit(t));
        }
        //Threadpool shutdown
        for (int i = 0; i < fileNames.length; i++) {
            try {
                sum += futures.poll().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();
        return sum;
    }


    public class ThreadPool implements Callable <Integer>{
        private String fileNames;
        private AtomicInteger ctr = new AtomicInteger(0);
        public ThreadPool(String name){
            this.fileNames =name;
        }
        public Integer call() throws Exception {
            String name= fileNames;
            try {
                FileInputStream file = new FileInputStream(fileNames);
                for (int i = 0; i != -1; i = file.read()) {
                    if (i == '\n') {
                        ctr.incrementAndGet();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return ctr.get();
        }
    }
}


