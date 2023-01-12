package Ex2_1;

public class Main {
    public static void main(String[] args) {
        int rand = (int) (Math.random() * 101);
        int rand2 = (int) (Math.random() * 101);
        System.out.println("n= " + rand);
        String[] str = Ex2_1.createTextFiles(rand, rand2, 100);
        Ex2_1 o = new Ex2_1();
        for (String name : str) {
            System.out.println(" " + name + ",");
        }
        double totalFunc2 = 0, totalFunc3 = 0, totalFunc4 = 0;
        for (int i = 0; i < 1000; i++) {
            long startTimeFunc2 = System.currentTimeMillis();
            Ex2_1.getNumOfLines(str);
            long endTimeFunc2 = System.currentTimeMillis();
            totalFunc2 += (endTimeFunc2 - startTimeFunc2);

            long startTimeFunc3 = System.currentTimeMillis();
            o.getNumOfLinesThreads(str);
            long endTimeFunc3 = System.currentTimeMillis();
            totalFunc3 += (endTimeFunc3 - startTimeFunc3);

            long startTimeFunc4 = System.currentTimeMillis();
            o.getNumOfLinesThreadPool(str);
            long endTimeFunc4 = System.currentTimeMillis();
            totalFunc4 += (endTimeFunc4 - startTimeFunc4);
        }
        System.out.println("Normal total: " + totalFunc2 / 1000 + " millisecs");
        System.out.println("Threads total: " + totalFunc3 / 1000 + " millisecs");
        System.out.println("ThreadPool total: " + totalFunc4 / 1000 + " millisecs");

        // number of lines only
        System.out.println("Normal: " + Ex2_1.getNumOfLines(str));
        System.out.println("Threads: " + o.getNumOfLinesThreads(str));
        System.out.println("Thread-Pool: " + o.getNumOfLinesThreadPool(str));
    }


}
