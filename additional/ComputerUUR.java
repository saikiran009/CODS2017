import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by nehaprabhugaonkar on 01/02/17.
 * To compute Unique User Ratio (UUR) of a word
 */
public class ComputerUUR {
    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"));
        final FileWriter fw = new FileWriter("src/main/resources/RANKINGS/UUR-Ranking.csv");
        String word;
        TreeMap<String, Float> utrRankList = new TreeMap<String, Float>();
        while ((word = br.readLine()) != null) {
            System.out.print(word+"\t");
            float utrScore = getUURscore(word);
            System.out.print("\t"+utrScore+"\n");
            utrRankList.put(word, utrScore);
            fw.write(word+"\t" + utrScore+"\n");
        }
        fw.close();
    }

    public static float getUURscore(String word) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/taggedTweets.txt"));
        float uurValue = 0;
        String line;
        float U_hi = 0;
        float U_cmh = 0;
        float U_en = 0;
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

        float sum = U_hi + U_cmh;
        try {
            uurValue =  sum / U_en;
        }
        catch(Exception e){

        }
        return uurValue;
    }
}
