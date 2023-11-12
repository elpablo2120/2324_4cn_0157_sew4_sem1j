package csv;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVReaderTest {
    @Test
    public void testB1() throws Exception {
        assertEquals(new ArrayList<String>(List.of("ok", "ok", "test")), new CSVReader().split("ok,ok,test"));
        assertEquals(new ArrayList<String>(List.of("hallo", "welt")), new CSVReader().split("hallo,welt"));
        assertEquals(new ArrayList<String>(List.of("hallo", "", "welt")), new CSVReader().split("hallo,,welt"));
    }
}
