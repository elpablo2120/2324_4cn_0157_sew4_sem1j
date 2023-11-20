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
    public void testB3() throws Exception {
        assertEquals(new ArrayList<String>(List.of("hallo\"", "welt")), new CSVReader().split("hallo\"\", \"welt\""));
        assertEquals(new ArrayList<String>(List.of("ok", "ok\"ok", "ok")), new CSVReader().split("\"ok\",\"ok\"\"ok\",ok"));
    }

    @Test
    public void testB4() throws Exception {
        assertEquals(new ArrayList<String>(List.of("hallo", "welt")), new CSVReader().split("  hallo,  \"welt\""));
        assertEquals(new ArrayList<String>(List.of("hall\"o", "welt")), new CSVReader().split("hall\"\"o,  \"welt\""));
    }


}
