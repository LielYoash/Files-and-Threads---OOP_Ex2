import java.io.*;
import java.util.Random;

public class Ex2_1 {

    /**
     * The following method create n text files with a random number of lines
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
    public static int getNumOfLines(String[] fileNames) {
        int sum = 0;
        try {
            for (String name : fileNames) {
                FileInputStream file = new FileInputStream(name);
                for (int i = 0; i != -1; i = file.read())
                    sum += i == '\n' ? 1 : 0;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

//    public int getNumOfLinesThreads(String[] fileNames) {
//
//    }
//
//    public int getNumOfLinesThreadPool(String[] fileNames) {
//
//    }


    }