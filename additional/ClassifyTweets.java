import java.io.*;

/**
 * To classify tweets in one of the classes ENGLISH, HINDI, CME, CMH, CMEQ and CS
 * Created by nehaprabhugaonkar on 20/01/17.
 */
public class ClassifyTweets {

    public void classifyData(String inputFile, String outputFile) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(inputFile));
        final FileWriter fw = new FileWriter(outputFile);
        String tag = "OTHER";
        String line;
        int noOfTweets = 0;
        while ((line = br.readLine()) != null) {
            String[] data = line.replaceAll("([0-9]+:[0-9]+):", "").replaceAll("    ","\t").replaceAll("( )+", " ").split("\t");
            if (data.length > 3) {
                noOfTweets++;
                final Long tweetId = Long.valueOf(data[0]);
                final Long userId = Long.valueOf(data[1]);
                final String userName = data[2];
                final String tweetText = data[3];
                final String[] tweetContent = tweetText.split(" ");
                int englishCount = 0, hindiCount = 0, totalCount = 0;
                for (int i = 0; i < tweetContent.length; i++) {
                    if (tweetContent[i].contains(":EN")|| tweetContent[i].contains("/EN")) {
                        englishCount += 1;
                    } else if (tweetContent[i].contains(":HI") || tweetContent[i].contains("/HI")) {
                        hindiCount += 1;
                    }
                    totalCount += 1;
                }
                if (englishCount > 0 && (englishCount * 100 / totalCount) > 90) {
                    tag = "ENGLISH";
                } else if (hindiCount > 0 && (hindiCount * 100 / totalCount) > 90) {
                    tag = "HINDI";
                } else if (englishCount > 0 && (englishCount * 100 / totalCount) > 50) {
                    tag = "CME";
                } else if (hindiCount > 0 && (hindiCount * 100 / totalCount) > 50) {
                    tag = "CMH";
                } else if (hindiCount > 0 && englishCount > 0 && (hindiCount == englishCount)) {
                    tag = "CMEQ";
                } else {
                    if( getCodeSwitchFreq(tweetContent)==true) {
                        tag = "CS";
                    }
                }
                fw.write(tweetId + "\t" + userId + "\t" + userName + "\t" + tag + "\t" + tweetText + "\n");
               }

        }
        fw.close();
        System.out.println(noOfTweets);
    }

    private static boolean getCodeSwitchFreq(String[] tweetContent) {
        int firstCount=0, secondCount=0;
        boolean flag = false;
        String base="";
        for (int i = 0; i < tweetContent.length; i++) {
            String[] splitWord = tweetContent[i].split("/");
            if(splitWord.length<2)
                continue;
            String tag = splitWord[1];
            if(tag.contains("NE") || tag.contains("OTHER"))
                continue;
            if(base.isEmpty()) {
                base = tag;
                firstCount++;
            }
            else if( !base.equalsIgnoreCase(tag) && firstCount>1) {
                secondCount++;
            }
            else if(base.equalsIgnoreCase(tag)){
                firstCount++;
            }
        }
        if(secondCount>1)
            flag=true;
        return flag;
    }

    public static void main(String[] args) throws IOException {
        ClassifyTweets classifyTweets = new ClassifyTweets();
        String inputFile = "src/main/resources/remaining/twitter_dataset.txt";
        String outputFile =  "src/main/resources/taggedTweets.txt";
        classifyTweets.classifyData(inputFile, outputFile);
    }
}
