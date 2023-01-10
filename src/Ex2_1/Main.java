package Ex2_1;

public class Main {
    public static void main(String[] args) {
        int rand = (int) (Math.random()*101);
        String[] str = Ex2_1.createTextFiles(rand, 1, 100);
        Ex2_1 o = new Ex2_1();
        for (String name : str) {
            System.out.println(" " + name + ",");
        }
        double totalFunc2 = 0l, totalFunc3 = 0l, totalFunc4 = 0l;
        for (int i = 0; i < 1000; i++) {
            long startTimeFunc2 = System.currentTimeMillis();
//            System.out.println("Normal: " + Ex2_1.getNumOfLines(str));
            Ex2_1.getNumOfLines(str);
            long endTimeFunc2 = System.currentTimeMillis();
//            System.out.println("Normal took: " + (endTimeFunc2 - startTimeFunc2) + " milliseconds");
            totalFunc2 += (endTimeFunc2 - startTimeFunc2);

            long startTimeFunc3 = System.currentTimeMillis();
//            System.out.println("Threads: " + o.getNumOfLinesThreads(str));
            o.getNumOfLinesThreads(str);
            long endTimeFunc3 = System.currentTimeMillis();
//            System.out.println("Threads took: " + (endTimeFunc3 - startTimeFunc3) + " milliseconds");
            totalFunc3 += (endTimeFunc3 - startTimeFunc3);

            long startTimeFunc4 = System.currentTimeMillis();
//            System.out.println("Thread-Pool: " + o.getNumOfLinesThreadPool(str));
            o.getNumOfLinesThreadPool(str);
            long endTimeFunc4 = System.currentTimeMillis();
//            System.out.println("Thread-Pool took: " + (endTimeFunc4 - startTimeFunc4) + " milliseconds");
            totalFunc4 += (endTimeFunc4 - startTimeFunc4);
        }
        System.out.println("Normal total: "+ totalFunc2/1000+" millisecs");
        System.out.println("Threads total: "+ totalFunc3/1000+" millisecs");
        System.out.println("ThreadPool total: "+ totalFunc4/1000+" millisecs");


    }


}
