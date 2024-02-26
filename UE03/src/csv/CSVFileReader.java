package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Die CSVFileReader-Klasse ermöglicht das Lesen einer CSV-Datei und die Verarbeitung der Daten als Array.
 */
public class CSVFileReader {

    /** Der Pfad zur CSV-Datei. */
    String path;

    /** Das Trennzeichen, das in der CSV-Datei verwendet wird. */
    char delimiter = ';';

    /** Das Zeichen, das in der CSV-Datei verwendet wird, um Felder zu begrenzen. */
    char doublequote = '"';

    /** Der FileReader, der die CSV-Datei einliest. */
    FileReader file;

    /** Der BufferedReader, der die CSV-Datei ausliest. */
    BufferedReader bufferedReader;

    /**
     * Konstruktor für CSVFileReader, der eine CSV-Datei liest und die Daten als Array speichert.
     *
     * @param path Der Pfad zur CSV-Datei.
     * @param delimiter Das Trennzeichen, das in der CSV-Datei verwendet wird.
     * @param doublequote Das Zeichen, das in der CSV-Datei verwendet wird, um Felder zu begrenzen.
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
     * Konstruktor für CSVFileReader mit benutzerdefiniertem Trennzeichen, der eine CSV-Datei liest und die Daten als
     * Array speichert.
     *
     * @param path Der Pfad zur CSV-Datei.
     * @param delimiter Das Trennzeichen, das in der CSV-Datei verwendet wird.
     * @throws FileNotFoundException Wenn die CSV-Datei nicht gefunden wird.
     */
    public CSVFileReader(String path, char delimiter) throws FileNotFoundException {
        this.path = path;
        this.delimiter = delimiter;

        file = new FileReader(path);
        bufferedReader = new BufferedReader(file);
    }

    /**
     * Konstruktor für CSVFileReader, der eine CSV-Datei liest und die Daten als Array speichert.
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
     * Liest die nächste Zeile der CSV-Datei und gibt die Daten als Array zurück.
     *
     * @return Die Daten der nächsten Zeile der CSV-Datei als Array.
     * @throws IOException Wenn die letzte Zeile der CSV-Datei bereits erreicht wurde.
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
