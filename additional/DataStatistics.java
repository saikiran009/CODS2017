import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by nehaprabhugaonkar on 31/01/17.
 */
public class DataStatistics {

    public static void main(String[] args) throws IOException {
        String line;
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/taggedTweets.txt"));
        int english = 0;
        int hindi = 0;
        int cmh = 0;
        int cme = 0;
        int cmeq = 0;
        int cs = 0;
        int other = 0;
        while ((line = br.readLine()) != null) {
            String[] data = line.replaceAll("( )+", " ").split("\t");
            final String label = data[3];
            if(label.equals("ENGLISH")){
                english++;
            }
            if(label.equals("HINDI")){
                hindi++;
            }
            if(label.equals("CME")){
                cme++;
            }
            if(label.equals("CMH")){
               cmh++;
            }
            if(label.equals("CS")){
                cs++;
            }
            if(label.equals("OTHER")){
                other++;
            }
            if(label.equals("CMEQ")){
                cmeq++;
            }
        }
        System.out.println("ENGLISH \t " + english);
        System.out.println("HINDI \t " + hindi);
        System.out.println("CMH \t " + cmh);
        System.out.println("CME \t " + cme);
        System.out.println("CMEQ \t " + cmeq);
        System.out.println("OTHER \t " + other);
        System.out.println("CS \t " + cs);
        System.out.println(english+hindi+cme+cmh+other+cs+cmeq);

    }
}
