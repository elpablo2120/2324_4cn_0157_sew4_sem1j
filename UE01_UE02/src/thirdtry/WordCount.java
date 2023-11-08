package thirdtry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordCount {
    private int wordCount = 0; // Change the variable name to "wordCount"

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

    public int countWordsFile(String path) {
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

        String filePath = "UE01_UE02/src/thirdtry/crsto12.html";
        int wordCountFile = wordCount.countWordsFile(filePath);
        System.out.println("Word count in file: " + wordCountFile);
    }
}
