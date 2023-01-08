public class Main {
    public static void main(String[] args) {
        String[] str = Ex2_1.createTextFiles(5, 1,1000);
        for (String name:str) {
            System.out.println(" "+name+",");
        }
    }
}
