package firsttry;

import java.util.Arrays;
import java.util.List;

public class WordCount {
    public static void main(String[] args) {
        System.out.println(count(" eins<img alt=\"<bild>\" keinwort> zwei"));
    }
    public static int count(String input){
        String trimmed = input.trim().replaceAll("<[^>]*>", " ").replaceAll("<[^>]*", " ");
        System.out.println(trimmed);
        if (trimmed.isBlank()){
            return 0;
        } else{
            String[] splited = trimmed.split(" ");
            List<String> a = Arrays.stream(splited)
                    .map(x -> x.replaceAll("[^a-zA-Z]+", " "))
                    .flatMap(x -> Arrays.stream(x.split(" ")))
                    .filter(x -> !x.isBlank())
                    .toList();
            return a.size();
        }
    }
}
