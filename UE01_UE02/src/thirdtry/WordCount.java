package thirdtry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordCount {
    public static int count(String input) {
        int wordCount = 0;
        State currentState = State.NOWORD;
        StringBuilder currentWord = new StringBuilder();

        for (char c : input.toCharArray()) {
            currentState = currentState.handleChar(c);

            if (currentState == State.INWORD) {
                currentWord.append(c);
            } else if (currentState == State.NOWORD && currentWord.length() > 0) {
                currentWord.setLength(0); // Clear the current word
                wordCount++;
            }
        }

        return wordCount;
    }

    private enum State {
        INWORD, NOWORD, INTAG, INATTRIBUTE, ESCAPE;

        State handleChar(char c) {
            switch (this) {
                case INWORD:
                    if (Character.isLetter(c)) {
                        return INWORD;
                    } else if (Character.isWhitespace(c)) {
                        return NOWORD;
                    } else if (c == '<') {
                        return INTAG;
                    } else {
                        return NOWORD;
                    }
                case NOWORD:
                    if (Character.isWhitespace(c)) {
                        return NOWORD;
                    } else if (Character.isLetter(c)) {
                        return INWORD;
                    } else if (c == '<') {
                        return INTAG;
                    } else {
                        return NOWORD;
                    }
                case INTAG:
                    if (c == '>') {
                        return NOWORD;
                    } else if (c == '"') {
                        return INATTRIBUTE;
                    } else {
                        return INTAG;
                    }
                case INATTRIBUTE:
                    if (c == '"') {
                        return INTAG;
                    } else if (c == '\\') {
                        return ESCAPE;
                    } else {
                        return INATTRIBUTE;
                    }
                case ESCAPE:
                    return INATTRIBUTE;
                default:
                    return this;
            }
        }
    }

    public int countWordsFile(String path) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thirdtry.WordCount.count(text);
    }

    public static void main(String[] args) {
        thirdtry.WordCount wordCount = new thirdtry.WordCount();

        String filePath = "UE01_UE02/src/thirdtry/crsto12.html";
        int wordCountFile = wordCount.countWordsFile(filePath);
        System.out.println("Word count in file: " + wordCountFile);
    }
}
