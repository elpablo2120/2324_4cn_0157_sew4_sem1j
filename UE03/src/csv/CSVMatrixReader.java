package csv;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class CSVMatrixReader {

    /** Der Pfad zur CSV-Datei. */
    String Path;

    /** Die Anzahl der Zeilen in der Matrix. */
    int matrixRows = 0;

    /** Die quadratische Matrix, die die CSV-Daten enthält. */
    public ArrayList<String[]> matrix = new ArrayList<>();

    /**
     * Konstruktor für CSVMatrixReader, der eine CSV-Datei liest und die Daten als quadratische Matrix speichert.
     *
     * @param Path Der Pfad zur CSV-Datei.
     * @throws FileNotFoundException Wenn die CSV-Datei nicht gefunden wird.
     */
    public CSVMatrixReader(String Path) throws FileNotFoundException {
        this.Path = Path;

        try {
            BufferedReader in = new BufferedReader(new FileReader(Path));
            while (in.readLine() != null) {
                matrixRows++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        CSVFileReader reader = new CSVFileReader(Path);
        for (int i = 0; i < matrixRows; i++) {
            try {
                matrix.add(reader.next());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (!testMatrix()) {
            throw new IllegalArgumentException("Matrix is not quadratic!");
        }
    }

    /**
     * Überprüft, ob die Matrix quadratisch ist.
     *
     * @return true, wenn die Matrix quadratisch ist, sonst false.
     */
    private boolean testMatrix() {
        for (int i = 0; i < matrixRows; i++) {
            if (matrix.get(i).length != matrixRows) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gibt die Matrix als formatierten String zurück.
     *
     * @return Ein formatierter String, der die Matrix repräsentiert.
     */
    public String getMatrix() {
        StringBuilder output = new StringBuilder();
        for (int i = 1; i < matrix.get(1).length; i++) {
            output.append(Character.toString(64 + i)).append(": ");
            for (int j = 1; j < matrixRows; j++) {
                if (!Objects.equals(matrix.get(j)[i], "")) {
                    output.append("nach ").append(Character.toString(64 + j)).append(":").append(matrix.get(j)[i]).append(", ");
                }
                if (j == matrixRows - 1) {
                    output.delete(output.length() - 2, output.length());
                    output.append("\n");
                }
            }
        }
        return output.toString().trim();
    }
}
