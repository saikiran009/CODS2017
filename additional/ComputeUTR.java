import java.io.*;
import java.util.*;

/**
 * Created by nehaprabhugaonkar on 01/02/17.
 * To compute Unique Tweet Ratio (UTR) of a word
 */
public class ComputeUTR {
    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"));
        final FileWriter fw = new FileWriter("src/main/resources/RANKINGS/UTR-Ranking.csv");
        String word;
        TreeMap <String, Float> utrRankList = new TreeMap<String, Float>();
         while ((word = br.readLine()) != null) {
             System.out.print(word+"\t");
             float utrScore = getUTRScore(word);
             System.out.print("\t"+utrScore+"\n");
             utrRankList.put(word, utrScore);
             fw.write(word+"\t" + utrScore+"\n");
         }
        fw.close();
    }

    public static float getUTRScore(String word) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/taggedTweets.txt"));
        float utrValue = 0;
        String line;
        float t_hi = 0;
        float t_en = 0;
        float t_cmh = 0;
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
            float sum = t_hi + t_cmh;
            try {
                utrValue =  sum / t_en;
            }
            catch(Exception e){

            }
        }
        System.out.print(t_en + "\t" + t_hi + "\t" + t_cmh +"\t" ) ;

        return utrValue;
    }


}
