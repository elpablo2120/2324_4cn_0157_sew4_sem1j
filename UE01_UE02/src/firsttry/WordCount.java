package firsttry;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse WordCount enthält Methoden zum Zählen der Wörter in einem gegebenen String.
 *
 * @author Paul Waldecker
 * @version 1
 */
public class WordCount {

    /**
     * Die main-Methode ist der Einstiegspunkt des Programms und zeigt die Verwendung der count-Methode.
     *
     * @param args Die Kommandozeilenargumente (nicht verwendet in diesem Beispiel).
     */
    public static void main(String[] args) {
        System.out.println(count(" eins<img alt=\"<bild keinwort> keinwort"));
        String s1="<abc alt=\"<xyz> \">";
        s1 = s1.replaceAll("\"(.*?>|<.*?)\"", " ");
        System.out.println(s1);
    }

    /**
     * Die count-Methode zählt die Anzahl der Wörter in einem gegebenen String.
     *
     * @param s Der Eingabe-String, dessen Wörter gezählt werden sollen.
     * @return Die Anzahl der Wörter im Eingabe-String.
     */
    public static int count(String s) {
        System.out.println(s);
        // Entfernt führende und nachfolgende Leerzeichen
        s = s.trim();

        // Überprüft, ob der String leer ist
        if (s.isEmpty()) {
            return 0;
        }
        // Ersetzt "\\" durch Leerzeichen
        s = s.replaceAll("\\\\", " ");
        System.out.println(s);

        // Entfernt Inhalte in doppelten Anführungszeichen (ignoriert '>' und '<' innerhalb der Anführungszeichen)
        s = s.replaceAll("\"(.*?>|<.*?)\"", " ");
        System.out.println(s);

        // Entfernt Inhalte in einfachen Anführungszeichen (ignoriert '>' und '<' innerhalb der Anführungszeichen)
        s = s.replaceAll("\"<.*\"?", " ");
        System.out.println(s);

        // Entfernt Inhalte innerhalb von spitzen Klammern ('<' und '>')
        s = s.replaceAll("<(.*?)>", " ");
        System.out.println(s);

        // Entfernt Inhalte nach Leerzeichen oder '>' und vor '"*?>'
        s = s.replaceAll(" .*\"*?>", " ");
        System.out.println(s);

        // Entfernt Inhalte nach Leerzeichen oder '>' und vor '>'
        s = s.replaceAll(" .*>", " ");
        System.out.println(s);

        // Entfernt Inhalte innerhalb von spitzen Klammern ('<')
        s = s.replaceAll("<.*", " ");
        System.out.println(s);

        // Ersetzt alle Zeichen, die keine Buchstaben sind, durch Leerzeichen
        s = s.replaceAll("[^A-Za-z]+", " ");
        System.out.println(s);

        // Erstellt eine Liste von Wörtern aus dem bereinigten String
        ArrayList<String> words = new ArrayList<>(List.of(s.split("\\s")));

        // Filtert leere Wörter und zählt die verbleibenden Wörter
        return (int) words.stream().filter(w -> !w.isEmpty()).count();
    }
}
