package ai.cuddle.nlp.newsSourceFilter.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ConsolidateModel {
    public static void main(String[] args) {
        String inputFile = args[0];
        String CSM = "src/main/resources/CodeMixing/csmRank.txt" ;
        String tfidf = "src/main/resources/CodeMixing/tfidfRank.txt" ;
        String wca = "src/main/resources/CodeMixing/weightedClassAverage.txt" ;
        String fm = "src/main/resources/CodeMixing/fm.txt" ;
        String uur = "src/main/resources/CodeMixing/uur.txt" ;
        String utr = "src/main/resources/CodeMixing/utr.txt" ;
        String utrUur = "src/main/resources/CodeMixing/utr-uur.txt" ;
        String outputFile = "src/main/resources/CodeMixing/output.csv";

        Map<String,Integer> csmRank = readFile(CSM);
        Map<String,Integer> tfidfRank = readFile(tfidf);
        Map<String,Integer> wcaRank = readFile(wca);
        Map<String,Integer> fmRank = readFile(fm);
        Map<String,Integer> uurRank = readFile(uur);
        Map<String,Integer> utrRank = readFile(utr);
        Map<String,Integer> utrUurRank = readFile(utrUur);

        Map<String,Double> wordAvgVar = new HashMap<>() ;

        for( String eachWord : csmRank.keySet() ){
            int[] data = new int[7];
            data[0] = csmRank.get(eachWord);
            data[1] = tfidfRank.get(eachWord);
            data[2] = wcaRank.get(eachWord);
            data[3] = fmRank.get(eachWord);
            data[4] = uurRank.get(eachWord);
            data[5] = utrRank.get(eachWord);
            data[6] = utrUurRank.get(eachWord);
            double median = median(data);

            int[] var = new int[7];
            var[0] = (int) Math.abs(Math.round(median) - data[0]);
            var[1] = (int) Math.abs(Math.round(median) - data[1]);
            var[2]  = (int) Math.abs(Math.round(median) - data[2]);
            var[3]  = (int) Math.abs(Math.round(median) - data[3]);
            var[4]  = (int) Math.abs(Math.round(median) - data[4]);
            var[5]  = (int) Math.abs(Math.round(median) - data[5]);
            var[6]  = (int) Math.abs(Math.round(median) - data[6]);
            int ctr=0, sum =0;
            for(int i=0;i<var.length;i++)
            {
                if( var[i] < 20 ) {
                    ctr++;
                    sum = sum + var[i] ;
                }
            }
            double avgVar=  sum / ctr ;
            wordAvgVar.put(eachWord,avgVar) ;
        }
//        System.out.println(entriesSortedByValues(wordAvgVar));
        try{
            final FileWriter fw = new FileWriter(outputFile);
            for( String word : wordAvgVar.keySet() ) {
                fw.write(word+","+wordAvgVar.get(word)+"\n");
            }
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    static <K,V extends Comparable<? super V>>
    List<Map.Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

        List<Map.Entry<K,V>> sortedEntries = new ArrayList<Map.Entry<K,V>>(map.entrySet());

        Collections.sort(sortedEntries,
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                }
        );

        return sortedEntries;
    }

    public static double median(int[] data)
    {
        Arrays.sort(data);
        double result=0;
        int size=data.length;


        if(size%2==1)
        {
            result=data[((size-1)/2)+1];
        }
        else
        {
            int middle_pair_first_index =(size-1)/2;
            result=(data[middle_pair_first_index+1]+data[middle_pair_first_index])/2;
        }

        return result;
    }

    private static Map<String, Integer> readFile(String fileName) {
        Map<String, Integer> ret = new HashMap<>() ;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.toLowerCase().split("\t") ;
                ret.put(split[0].trim(), Integer.parseInt(split[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret ;
    }
}
