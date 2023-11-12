package csv;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
