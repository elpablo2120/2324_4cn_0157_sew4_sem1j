package secondtry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class WordCount {
    private static int counter;
    enum State {
        INWORD {
            @Override
            State handleChar(char c, WordCount context) {
                if (Character.isLetter(c)) {
                    return INWORD; // Stay in INWORD state when encountering a letter
                } else if (Character.isWhitespace(c)) {
                    return NOWORD; // Transition to NOWORD state when encountering whitespace
                } else if (c == '<') {
                    return INTAG; // Transition to INTAG state when encountering '<'
                } else {
                    return NOWORD; // Transition to NOWORD state for other characters
                }
            }
        },
        NOWORD {
            @Override
            State handleChar(char c, WordCount context) {
                if (Character.isWhitespace(c)) {
                    return NOWORD; // Stay in NOWORD state when encountering whitespace
                } else if (Character.isLetter(c)) {
                    counter++;
                    return INWORD; // Transition to INWORD state when encountering the first letter
                } else if (c == '<') {
                    return INTAG; // Transition to INTAG state when encountering '<'
                } else {
                    return NOWORD; // Stay in NOWORD state for other characters
                }
            }
        },
        INTAG {
            @Override
            State handleChar(char c, WordCount context) {
                if (c == '>') {
                    return NOWORD; // Transition back to NOWORD state when encountering '>'
                } else if (c == '"') {
                    return INATTRIBUTE; // Transition to INATTRIBUTE state when encountering double quote within tag
                } else {
                    return INTAG; // Stay in INTAG state for characters within the HTML tag
                }
            }
        },
        INATTRIBUTE {
            @Override
            State handleChar(char c, WordCount context) {
                if (c == '"') {
                    return INTAG; // Transition back to INTAG state when encountering closing double quote
                } else if (c == '\\') {
                    return ESCAPE; // Transition to ESCAPE state when encountering escape character '\'
                } else {
                    return INATTRIBUTE; // Stay in INATTRIBUTE state for other characters within attribute
                }
            }
        },
        ESCAPE {
            @Override
            State handleChar(char c, WordCount context) {
                return INATTRIBUTE; // Transition back to INATTRIBUTE state after processing escape sequence
            }
        };

        abstract State handleChar(char c, WordCount context);
    }

    public static int count(String input) {
        return new WordCount().countWords(input);
    }

    public int countWords(String input) {
        counter = 0;
        Stack<State> stateStack = new Stack<>();
        stateStack.push(State.NOWORD);

        for (char c : input.toCharArray()) {
            State currentState = stateStack.peek();
            State newState = currentState.handleChar(c, this);

            if (newState == State.INTAG && currentState != State.INATTRIBUTE) {
                stateStack.push(State.INTAG);
            } else if (newState == State.INATTRIBUTE && currentState != State.INATTRIBUTE) {
                stateStack.push(State.INATTRIBUTE);
            } else if (newState == State.ESCAPE) {
                stateStack.push(State.ESCAPE);
            } else if (newState == State.NOWORD && currentState == State.INATTRIBUTE) {
                stateStack.pop(); // Pop out of INATTRIBUTE state when NOWORD is encountered
            }

            stateStack.push(newState);
        }

        return counter;
    }

    public int countWordsFile (String path) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WordCount.count(text);
    }


    public static void main(String[] args) {
        WordCount wordCount = new WordCount();
        System.out.println(wordCount.countWords(" eins<img alt=\"<bild>\" > zwei"));

        System.out.println(wordCount.countWordsFile("UE01_UE02/src/secondtry/crsto12.html"));

    }
}
