package csv;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVReaderTest {
    @Test
    public void testB1() throws Exception {
        assertEquals(new ArrayList<String>(List.of("ok", "ok", "test")), new CSVReader().split("ok,ok,test"));
        assertEquals(new ArrayList<String>(List.of("hallo", "welt")), new CSVReader().split("hallo,welt"));
        assertEquals(new ArrayList<String>(List.of("hallo", "", "welt")), new CSVReader().split("hallo,,welt"));
    }

    @Test
    public void testB2() throws Exception {
        assertEquals(new ArrayList<String>(List.of("ok,", "ok", "test")), new CSVReader().split("\"ok,\",ok,test"));
        assertEquals(new ArrayList<String>(List.of("hallo", "welt")), new CSVReader().split("hallo,\"welt\""));
        assertEquals(new ArrayList<String>(List.of("hallo", "welt")), new CSVReader().split("hallo,\"welt\""));
        assertEquals(new ArrayList<String>(List.of("hallo", "welt")), new CSVReader().split("\"hallo\",\"welt\""));
        assertThrows(IllegalArgumentException.class, () -> {
            new CSVReader().split("\"nicht\" ok");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CSVReader().split("\"nicht ok");
        });
    }

    @Test
    public void testB5() {
        try( CSVFileReader csvFileReader = new CSVFileReader("CSVFile.csv")){


            Iterator<ArrayList<String>> iterator = csvFileReader.iterator();

            assertEquals(new ArrayList<String>(List.of("ok", "ok", "test")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("hallo", "welt")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("hallo", "", "welt")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("ok,", "ok", "test")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("hallo", "welt")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("hallo", "welt")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("hallo", "welt")), iterator.next());
            assertThrows(Exception.class, iterator::next);
            assertThrows(Exception.class, iterator::next);
            assertEquals(new ArrayList<String>(List.of("hallo\"", "welt")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("ok", "ok\"ok", "ok")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("hallo", "welt")), iterator.next());
            assertEquals(new ArrayList<String>(List.of("hall\"o", "welt")), iterator.next());
            assertNull(iterator.next());

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
