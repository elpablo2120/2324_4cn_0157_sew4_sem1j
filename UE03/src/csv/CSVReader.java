package csv;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Die CSVReader-Klasse ermöglicht das Parsen von CSV-Texten mit verschiedenen Optionen.
 */
public class CSVReader {

    public static void main(String[] args) {
        CSVReader test = new CSVReader(';', '"', false);

        System.out.println(Arrays.toString(test.split("a;\"a;b\";abc")));
        System.out.println(test.split("a;\"a;b\";abc").length);

    }

    /**
     * Der Standard-Delimiter für CSV (Semikolon).
     */
    char delimiter = ';';

    /**
     * Das Standard-Zeichen für den Anfang und das Ende eines Feldes in Anführungszeichen.
     */
    char doublequote = '"';

    /**
     * Gibt an, ob vor dem eigentlichen Wert Leerzeichen übersprungen werden sollen.
     */
    boolean skipInitialWhitespace = false;

    /**
     * Zählt die Anzahl der verarbeiteten Zeichen im CSV-Text.
     */
    int counter = 0;

    /**
     * Zählt die Anzahl der übersprungenen Leerzeichen vor einem Wert (nur wenn skipInitialWhitespace true ist).
     */
    int wsCounter = 0;

    /**
     * Die Liste, die die geparsten CSV-Werte enthält.
     */
    ArrayList<String> output = new ArrayList<>();

    /**
     * Erstellt einen CSVReader mit benutzerdefinierten Einstellungen.
     *
     * @param delimiter             Der Delimiter, der die CSV-Werte voneinander trennt.
     * @param doublequote           Das Zeichen, das verwendet wird, um Werte in Anführungszeichen zu setzen.
     * @param skipInitialWhitespace Gibt an, ob vor dem eigentlichen Wert Leerzeichen übersprungen werden sollen.
     */
    public CSVReader(char delimiter, char doublequote, boolean skipInitialWhitespace) {
        this.delimiter = delimiter;
        this.doublequote = doublequote;
        this.skipInitialWhitespace = skipInitialWhitespace;
    }

    /**
     * Die verschiedenen Zustände, die der CSVReader während des Parsens eines Textes annehmen kann.
     */
    enum State {
        /**
         * Anfangszustand, in dem noch kein Wert verarbeitet wurde.
         */
        NOVALUE {
            @Override
            State handleChar(char c, CSVReader context) {
                if (context.skipInitialWhitespace) {
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
        /**
         * Zustand repräsentiert einen Wert, der nicht in Anführungszeichen steht.
         */
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
        /**
         * Zustand repräsentiert einen Wert, der in Anführungszeichen steht.
         */
        QUOTEVALUE {
            @Override
            State handleChar(char c, CSVReader context) {
                if (context.skipInitialWhitespace) {
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
        /**
         * Zustand repräsentiert das Ende eines Wertes in Anführungszeichen.
         */
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

        /**
         * Abstrakte Methode, die von den einzelnen Zuständen implementiert werden muss, um das Verhalten des Zustands
         * zu definieren.
         *
         * @param c       Das nächste Zeichen im CSV-Text.
         * @param context Der CSVReader-Instanz, die den Zustand steuert.
         * @return Der nächste Zustand nach Verarbeitung des aktuellen Zeichens.
         */
        abstract State handleChar(char c, CSVReader context);
    }

    /**
     * Teilt den übergebenen CSV-Text in einzelne Werte auf.
     *
     * @param text Der zu parsende CSV-Text.
     * @return Ein Array, das die einzelnen Werte des CSV-Textes enthält.
     */
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