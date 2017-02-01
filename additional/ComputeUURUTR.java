import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nehaprabhugaonkar on 24/01/17.
 * To compute Unique User Ratio (UUR) of a word
 * To compute Unique Tweet Ratio (UTR) of a word
 *
 */
public class ComputeUURUTR {
    public static String getUTRscore(String word) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/taggedTweets.txt"));
        String utrValue = "";
        String line;
        int t_hi = 0;
        int t_en = 0;
        int t_cmh = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains(" " + word + "/")) {
                if (line.contains("ENGLISH")) {
                    t_en++;
                }
                if (line.contains("HINDI")) {
                    t_hi++;
                }
                if (line.contains("CMH")) {
                    t_cmh++;
                }
            }
        }
        utrValue = t_en + "\t" + t_hi + "\t" + t_cmh + "\t";
        return utrValue;
    }

    public static String getUURscore(String word) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/taggedTweets.txt"));
        String uurValue = "";
        String line;
        int U_hi = 0;
        int U_cmh = 0;
        int U_en = 0;
        Set<Long> enUser = new HashSet<Long>();
        Set<Long> hiUser = new HashSet<Long>();
        Set<Long> cmhUser = new HashSet<Long>();
        while ((line = br.readLine()) != null) {

            String[] data = line.replaceAll("( )+", " ").split("\t");
            final Long userId = Long.valueOf(data[1]);
            if (line.contains(" " + word + "/")) {
                if (line.contains("ENGLISH")) {
                    enUser.add(userId);
                }
                if (line.contains("HINDI")) {
                    hiUser.add(userId);
                }
                if (line.contains("CMH")) {
                    cmhUser.add(userId);
                }
            }

        }
        if (enUser.size() > 0) {
            U_en = enUser.size();
        }
        if (hiUser.size() > 0) {
            U_hi = hiUser.size();
        }
        if (cmhUser.size() > 0) {
            U_cmh = cmhUser.size();
        }
        uurValue = U_en + "\t" + U_hi + "\t" + U_cmh + "\t";

        return uurValue;
    }


    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"));
        final FileWriter fw = new FileWriter("src/main/resources/UTR-UUR-Ranking.csv");
        String word;
        System.out.println("t_en" + "\t" + "t_hi" + "\t" + "t_cmh" + "\t" + "utr" + "\t" + "U_en" + "\t" + "U_hi" + "\t" + "U_cmh" + "\t" + "uur");
        fw.write("Word" + " \t" + "t_en" + "\t" + "t_hi" + "\t" + "t_cmh" + "\t" + "utr" + "\t" + "U_en" + "\t" + "U_hi" + "\t" + "U_cmh" + "\t" + "uur");
        while ((word = br.readLine()) != null) {
                String utrScore = getUTRscore(word);
                String uurScore = getUURscore(word);
                System.out.println("WORD :: " + word + "\t" + utrScore + " \t " + uurScore + " \t ");
                fw.write(word + "\t" + utrScore + " \t " + uurScore + "\n");

        }
        fw.close();
    }
}
