package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Die CSVFileReader-Klasse ermöglicht das Lesen von CSV-Dateien und das Extrahieren von Werten aus den Zeilen.
 */
public class CSVFileReader {

    /** Der Pfad zur CSV-Datei. */
    String path;

    /** Der Delimiter, der die CSV-Werte voneinander trennt. Standardmäßig ';'. */
    char delimiter = ';';

    /** Das Zeichen, das verwendet wird, um Werte in Anführungszeichen zu setzen. Standardmäßig '"'. */
    char doublequote = '"';

    /** Der FileReader für den Zugriff auf die CSV-Datei. */
    FileReader file;

    /** Der BufferedReader für effizientes Lesen der CSV-Datei. */
    BufferedReader bufferedReader;

    /**
     * Konstruktor für CSVFileReader mit benutzerdefinierten Einstellungen für Delimiter und Anführungszeichen.
     *
     * @param path      Der Pfad zur CSV-Datei.
     * @param delimiter Der Delimiter, der die CSV-Werte voneinander trennt.
     * @param doublequote Das Zeichen, das verwendet wird, um Werte in Anführungszeichen zu setzen.
     * @throws FileNotFoundException Wenn die CSV-Datei nicht gefunden wird.
     */
    public CSVFileReader(String path, char delimiter, char doublequote) throws FileNotFoundException {
        this.path = path;
        this.delimiter = delimiter;
        this.doublequote = doublequote;

        file = new FileReader(path);
        bufferedReader = new BufferedReader(file);
    }

    /**
     * Konstruktor für CSVFileReader mit benutzerdefiniertem Delimiter.
     *
     * @param path      Der Pfad zur CSV-Datei.
     * @param delimiter Der Delimiter, der die CSV-Werte voneinander trennt.
     * @throws FileNotFoundException Wenn die CSV-Datei nicht gefunden wird.
     */
    public CSVFileReader(String path, char delimiter) throws FileNotFoundException {
        this.path = path;
        this.delimiter = delimiter;

        file = new FileReader(path);
        bufferedReader = new BufferedReader(file);
    }

    /**
     * Konstruktor für CSVFileReader ohne benutzerdefinierte Einstellungen für Delimiter und Anführungszeichen.
     *
     * @param path Der Pfad zur CSV-Datei.
     * @throws FileNotFoundException Wenn die CSV-Datei nicht gefunden wird.
     */
    public CSVFileReader(String path) throws FileNotFoundException {
        this.path = path;

        file = new FileReader(path);
        bufferedReader = new BufferedReader(file);
    }

    /**
     * Liest die nächste Zeile aus der CSV-Datei und extrahiert die Werte in ein String-Array.
     *
     * @return Ein String-Array mit den extrahierten CSV-Werten der nächsten Zeile.
     * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt oder das Ende erreicht wurde.
     */
    public String[] next() throws IOException {
        if (bufferedReader == null) {
            throw new IOException("Last line has already been reached");
        } else {
            String line = bufferedReader.readLine();
            CSVReader csvReader = new CSVReader(delimiter, doublequote, false);
            return csvReader.split(line);
        }
    }
}
