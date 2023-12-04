package thirdtry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class provides functionality to count words in a given input string or file.
 * It uses a finite state machine to handle different states while iterating through the input characters.
 *
 * @author Paul Waldecker
 * @version 3
 */
public class WordCount {
    private int wordCount = 0;

    /**
     * Counts the number of words in the given input string.
     *
     * @param input The input string to count words from.
     * @return The number of words in the input string.
     */
    public static int count(String input) {
        WordCount context = new WordCount();
        State state = State.NOWORD;

        for (char c : input.toCharArray()) {
            state = state.handleChar(c, context);
        }

        return context.wordCount;
    }

    private enum State {
        INWORD {
            @Override
            State handleChar(char c, WordCount context) {
                if (Character.isLetter(c)) {
                    return INWORD;
                } else if (Character.isWhitespace(c)) {
                    return NOWORD;
                } else if (c == '<') {
                    return INTAG;
                } else {
                    return NOWORD;
                }
            }
        },
        NOWORD {
            @Override
            State handleChar(char c, WordCount context) {
                if (Character.isWhitespace(c)) {
                    return NOWORD;
                } else if (Character.isLetter(c)) {
                    context.wordCount++;
                    return INWORD;
                } else if (c == '<') {
                    return INTAG;
                } else {
                    return NOWORD;
                }
            }
        },
        INTAG {
            @Override
            State handleChar(char c, WordCount context) {
                if (c == '>') {
                    return NOWORD;
                } else if (c == '"') {
                    return INATTRIBUTE;
                } else {
                    return INTAG;
                }
            }
        },
        INATTRIBUTE {
            @Override
            State handleChar(char c, WordCount context) {
                if (c == '"') {
                    return INTAG;
                } else if (c == '\\') {
                    return ESCAPE;
                } else {
                    return INATTRIBUTE;
                }
            }
        },
        ESCAPE {
            @Override
            State handleChar(char c, WordCount context) {
                return INATTRIBUTE;
            }
        };

        abstract State handleChar(char c, WordCount context);
    }

    /**
     * Counts the number of words in the text content of a file specified by the given path.
     *
     * @param path The path to the file.
     * @return The number of words in the file.
     */
    public int countWordsFile(String path) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WordCount.count(text);
    }

    /**
     * The main method demonstrates the usage of the WordCount class by counting words in a file.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        WordCount wordCount = new WordCount();

        String filePath = "UE01_UE02/src/thirdtry/crsto12.html";
        int wordCountFile = wordCount.countWordsFile(filePath);
        System.out.println("Word count in file: " + wordCountFile);
    }
}
