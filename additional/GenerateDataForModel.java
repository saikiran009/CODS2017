import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by nehaprabhugaonkar on 25/01/17.
 */
public class GenerateDataForModel {

    public static String GenerateModelData(String word, String tag, int limit) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/taggedTweets.txt"));
        String line;
        String match = "";
        while ((line = br.readLine()) != null) {
            String[] data = line.replaceAll("( )+", " ").split("\t");
            final String label = data[3];
            final String tweetText = data[4];
            if (label.equals(tag)) {
                if (line.contains(" " + word + "/")) {

                    final String[] tweetContent = tweetText.split(" ");
                    int englishCount = 0;
                    for (int i = 0; i < tweetContent.length; i++) {
                        if (tweetContent[i].contains(":EN")) {
                            englishCount += 1;
                        }
                    }
                    if (englishCount == limit) {
                        System.out.println(word + "\t" + line);
                        match = word + "\t" + line + "\n";
                    }

                }
            }
        }
        return match;
    }

    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"));
        String word;
        final FileWriter fw = new FileWriter("src/main/resources/" + "HINDI-3.csv");

        while ((word = br.readLine()) != null) {
            String cmh = GenerateModelData(word, "HINDI", 3);
            fw.write(cmh);
        }

        fw.close();
    }
}
