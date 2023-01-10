package Ex2_1;

public class Main {
    public static void main(String[] args) {
        String[] str = Ex2_1.createTextFiles(3, 1,12);
        Ex2_1 o = new Ex2_1();
        for (String name:str) {
            System.out.println(" "+name+",");
        }
        System.out.println("normal: "+Ex2_1.getNumOfLines(str));
        System.out.println("threads: "+o.getNumOfLinesThreads(str));
        System.out.println("thread-pool: "+o.getNumOfLinesThreadPool(str));
    }
}
