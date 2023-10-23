package csv;

import java.util.Arrays;

public class CSVReader {

    enum State{
        VALUE {
            @Override
            State handleChar(char c, CSVReader context) {
                return null;
            }
        },
        SEPERATOR {
            @Override
            State handleChar(char c, CSVReader context) {
                return null;
            }
        };
        abstract State handleChar(char c, CSVReader context);

    }

    public static String[] split (String input){
        String[] erg;
        erg = new String[]{"abc"};
        return erg;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(split("1;2;3;4;5;6")));
    }
}
