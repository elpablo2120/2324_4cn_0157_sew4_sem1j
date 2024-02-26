package csv;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {
    CSVReaderTest() throws FileNotFoundException {
    }
    CSVReader Version1 = new CSVReader(';', '"', false);
    CSVReader Version15 = new CSVReader(';', '"', true);
    CSVFileReader version2 = new CSVFileReader("/Users/paulwaldecker/Desktop/HTL3R/4CN/SEW/sew4_sem1j/UE03/src/csv/csv.csv");
    CSVMatrixReader version3 = new CSVMatrixReader("/Users/paulwaldecker/Desktop/HTL3R/4CN/SEW/sew4_sem1j/UE03/src/csv/matrix.csv");
    String CSVAusgabe = "A: nach B:4, nach C:7, nach D:8\n" +
            "B: nach A:4, nach D:5, nach E:3, nach F:3\n" +
            "C: nach A:7, nach D:2, nach G:1\n" +
            "D: nach A:8, nach C:2, nach E:1, nach G:2\n" +
            "E: nach B:5, nach D:1, nach F:1, nach H:5\n" +
            "F: nach B:3, nach E:1, nach H:1\n" +
            "G: nach C:1, nach D:2, nach H:1\n" +
            "H: nach E:5, nach F:1, nach G:1";

    @Test
    void einfach () {
        assertEquals("[Apfel, Birne, Orange]", Arrays.toString(Version1.split("Apfel;Birne;Orange")));
        assertEquals("[Apfel, , Birne]", Arrays.toString(Version1.split("Apfel;;Birne")));
    }

    @Test
    void mitFeldbegrenzer () {
        assertEquals("[ok, ok; ok]", Arrays.toString(Version1.split("  \"ok\"; \"ok; ok\" ")));
        assertEquals("[ok, ok; ok, ok]", Arrays.toString(Version1.split("\"ok\";\"ok; ok\";ok\"ok\"")));
        assertThrows(IllegalArgumentException.class, ()->{Arrays.toString(Version1.split("\"ok\";\"ok; ok\";ok\"ok"));});
    }

    @Test
    void mitFeldbegrenzerinDaten () {
        assertEquals("[ok, ok\"\"ok, ok]", Arrays.toString(Version1.split("  \"ok\"; \"ok\"\"ok\" ;ok")));
        assertEquals("[ok, ok\"\" ok, \"\"ok]", Arrays.toString(Version1.split("ok; \"ok\"\" ok\"; \"\"\"ok\"")));
    }

    @Test
    void initialWhitespaces () {
        assertEquals("[ok, okok ,   ok]", Arrays.toString(Version15.split("  \"ok; \"okok ;  ok")));
        assertEquals("[   ok, ok  , OKK]", Arrays.toString(Version15.split("   ok; \"ok  ;\"OKK")));
    }

    @Test
    void CSVFileReaderTest() throws IOException {
        assertEquals("[warrior, Pyrrhos, Gummibärenbande, 90, Sword, 240]", Arrays.toString(version2.next()));
        assertEquals("[farmer, Henry, Gummibärenbande, 45, Corn, 120]", Arrays.toString(version2.next()));
    }
    @Test
    void CVSMatrixReaderTest(){
        assertEquals(CSVAusgabe, version3.getMatrix());
    }
}