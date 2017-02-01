package ai.cuddle.nlp.newsSourceFilter.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kavitaganeshan on 1/31/17.
 */
public class CodeSwitchedTweets {
    public static void main(String[] args) {
        String inputFile ="src/main/resources/CodeMixing/taggedTweets_120k.txt";
        try {
            final BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            List<String> words = getWords() ;
            Map<String, Integer> wordCodeSwitchTweetCount = new HashMap<>();
            int k=0;
            while ((line = br.readLine()) != null) {

                String[] data = line.replaceAll("( )+", " ").split("\t");
                if (data.length > 4) {
                    final String tweetText = data[4];
                    final String[] tweetContent = tweetText.split(" ");
                    int count = getCodeSwitchFreq(tweetContent);
                    if(count<1)
                        continue;
                    for (int i = 0; i < tweetContent.length; i++) {
                        String[] splitWord = tweetContent[i].split("/");
                        if(splitWord.length<2)
                            continue;
                        String word = splitWord[0].trim().toLowerCase();
                        if( words.contains(word) && count>2) {
//                            System.out.println("CODE SWITCH:" + word+"\t:" + count +"\t:" + tweetText);
                            int number = wordCodeSwitchTweetCount.containsKey(word) ? wordCodeSwitchTweetCount.get(word) : 0 ;
                            wordCodeSwitchTweetCount.put(word,++number);
                        }
                    }
                }
                k++;
            }
            System.out.println("TOTAL K:"+ k);
            for( String word: wordCodeSwitchTweetCount.keySet() )
                System.out.println("WORD :" + word+"\t:" + wordCodeSwitchTweetCount.get(word));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getCodeSwitchFreq(String[] tweetContent) {
        int count=0;
        String base="";
        for (int i = 0; i < tweetContent.length; i++) {
//            System.out.println("TWEET :" + tweetContent[i]);
            String[] splitWord = tweetContent[i].split("/");
            if(splitWord.length<2)
                continue;
            String tag = splitWord[1];
            if(tag.contains("NE") || tag.contains("OTHER"))
                continue;
            if(base.isEmpty())
                base=tag;
            else if( !base.equalsIgnoreCase(tag) )
                count++;
        }
        return count;
    }

    private static List<String> getWords() {
        String fileName = "src/main/resources/CodeMixing/230words.txt" ;
        List<String> words = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}
