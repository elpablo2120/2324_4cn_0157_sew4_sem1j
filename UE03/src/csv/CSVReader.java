package csv;

import java.util.ArrayList;

public class CSVReader {

    private ArrayList<String> fields;
    private int index;

    enum State {
        CHAR {
            @Override
            State handleChar(char c, CSVReader context) {
                if (c == ',') {
                    context.index++;
                    context.fields.add("");
                    return DELIMITER;
                }
                context.fields.set(context.index, context.fields.get(context.index) + c);
                return CHAR;
            }
        },

        DELIMITER {
            @Override
            State handleChar(char c, CSVReader context) {
                if (c == ',') {
                    context.index++;
                    context.fields.add("");
                    return DELIMITER;
                }
                context.fields.set(context.index, context.fields.get(context.index) + c);
                return CHAR;
            }
        };

        abstract State handleChar(char c, CSVReader context);
    }

    public ArrayList<String> split(String input) throws Exception {
        index = 0;
        fields = new ArrayList<>();
        fields.add("");
        State state = State.CHAR;
        for (int i = 0; i < input.length(); i++) {
            state = state.handleChar(input.charAt(i), this);
        }
        return fields;
    }

    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();
        try {
            ArrayList<String> result = csvReader.split("ok,,test");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
