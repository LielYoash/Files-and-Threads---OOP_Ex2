public class Main {
    public static void main(String[] args) {
        String[] str = Ex2_1.createTextFiles(3, 1,10);
        for (String name:str) {
            System.out.println(" "+name+",");
        }
        System.out.println(Ex2_1.getNumOfLines(str));
    }
}
