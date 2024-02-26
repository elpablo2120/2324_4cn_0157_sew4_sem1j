package csv;

import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader {

    public static void main(String[] args) {
        CSVReader test = new CSVReader(';','"', false);

        System.out.println(Arrays.toString(test.split(";")));
        System.out.println(test.split(";").length);

    }

    char delimiter = ';';

    char doublequote = '"';

    boolean skipInitialWhitespace = false;

    int counter = 0;

    int wsCounter = 0;

    ArrayList<String> output = new ArrayList<>();

    public CSVReader(char delimiter, char doublequote, boolean skipInitialWhitespace) {
        this.delimiter = delimiter;
        this.doublequote = doublequote;
        this.skipInitialWhitespace = skipInitialWhitespace;
    }

    enum State {
        NOVALUE {
            @Override
            State handleChar(char c, CSVReader context) {
                if(context.skipInitialWhitespace) {
                    if (c == context.doublequote) {
                        context.counter++;
                        context.wsCounter = 0;
                        context.output.add("");
                        return QUOTEVALUE;
                    } else if (c == context.delimiter) {
                        context.counter++;
                        context.output.add(String.valueOf(c));
                        return VALUE;
                    } else if (Character.isWhitespace(c)) {
                        context.wsCounter++;
                        return this;
                    } else {
                        context.counter++;
                        context.output.add(" ".repeat(context.wsCounter) + c);
                        context.wsCounter = 0;
                        return VALUE;
                    }
                } else {
                    if (c == context.doublequote) {
                        context.counter++;
                        context.output.add("");
                        return QUOTEVALUE;
                    } else if (c == context.delimiter) {
                        context.counter++;
                        context.output.add("");
                        return this;
                    } else {
                        context.counter++;
                        context.output.add(String.valueOf(c));
                        return VALUE;
                    }
                }
            }
        },

        VALUE {
            @Override
            State handleChar(char c, CSVReader context) {
                if (c == context.doublequote) {
                    context.output.set(context.counter - 1, "");
                    return QUOTEVALUE;
                } else if (c != context.delimiter) {
                    context.output.set(context.counter - 1, context.output.get(context.counter - 1) + c);
                    return this;
                } else {
                    return NOVALUE;
                }

            }
        },

        QUOTEVALUE {
            @Override
            State handleChar(char c, CSVReader context) {
                if(context.skipInitialWhitespace) {
                    if (c == context.delimiter) {
                        return NOVALUE;
                    } else {
                        context.output.set(context.counter - 1, context.output.get(context.counter - 1) + c);
                        return this;
                    }
                } else {
                    if (c != context.doublequote) {
                        context.output.set(context.counter - 1, context.output.get(context.counter - 1) + c);
                        return this;
                    } else {
                        return QOUTEEXIT;
                    }
                }
            }
        },

        QOUTEEXIT {
            @Override
            State handleChar(char c, CSVReader context) {
                if (c == context.delimiter) {
                    return NOVALUE;
                } else if (c == ' ') {
                    return this;
                } else if (c == context.doublequote) {
                    context.output.set(context.counter - 1, context.output.get(context.counter - 1) + "\"\"");
                    return QUOTEVALUE;
                } else {
                    throw new IllegalArgumentException("Values between closing double-quote and delimiter are not allowed!");
                }
            }
        };
        abstract State handleChar(char c, CSVReader context);
    }

    public String[] split(String text) {
        State state = State.NOVALUE;
        counter = 0;
        output.clear();
        for (char c : text.toCharArray()) {
            state = state.handleChar(c, this);
        }
        if (state == State.QUOTEVALUE && !skipInitialWhitespace) {
            throw new IllegalArgumentException("Double-quotes need to be closed");
        }
        return output.toArray(String[]::new);
    }

}