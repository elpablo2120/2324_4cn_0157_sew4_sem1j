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
                } else if (c == '"') {
                    return OPENFELDBEGRENZER;
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
                } else if (c == '"') {
                    return OPENFELDBEGRENZER;
                } else if (Character.isWhitespace(c)) {
                    return DELIMITER;
                }
                context.fields.set(context.index, context.fields.get(context.index) + c);
                return CHAR;
            }
        },
        OPENFELDBEGRENZER {
            @Override
            State handleChar(char c, CSVReader context) {
                if (c == '"') {
                    return CHAR;
                }
                context.fields.set(context.index, context.fields.get(context.index) + c);
                return INFELDBEGRENZER;
            }
        },
        INFELDBEGRENZER {
            @Override
            State handleChar(char c, CSVReader context) {
                if (c == '"') {
                    return CLOSEFELDBEGRENZER;
                }
                context.fields.set(context.index, context.fields.get(context.index) + c);
                return INFELDBEGRENZER;
            }
        },
        CLOSEFELDBEGRENZER {
            @Override
            State handleChar(char c, CSVReader context) {
                if (',' != c) {
                    throw new IllegalArgumentException();
                }
                context.index++;
                context.fields.add("");
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
        if(state == State.INFELDBEGRENZER){
            throw  new IllegalArgumentException();
        }
        return fields;
    }

    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();
        try {
            ArrayList<String> result = csvReader.split("\"ok\",\"ok\"\"ok\",ok");
            System.out.println("ERGEBNIS: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
