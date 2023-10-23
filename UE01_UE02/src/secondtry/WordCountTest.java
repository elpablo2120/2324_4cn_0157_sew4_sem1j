package secondtry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static secondtry.WordCount.count;

class WordCountTest {
    @Test
    public void leicht() {
        assertEquals(0, count(""));
        assertEquals(0, count(" "));
        assertEquals(0, count("   "));
    }

    @Test
    public void normal() {
        assertEquals(1, count("eins"));
        assertEquals(1, count(" eins"));
        assertEquals(1, count("eins "));
        assertEquals(1, count(" eins "));
        assertEquals(1, count(" eins  "));
        assertEquals(1, count("  eins "));
        assertEquals(1, count("  eins  "));

        assertEquals(1, count("eins:"));
        assertEquals(1, count(":eins:"));
        assertEquals(1, count(":eins"));
        assertEquals(1, count(" eins  "));
        assertEquals(1, count(" eins : "));
        assertEquals(1, count(": eins :"));

        assertEquals(3, count("ein erster Text"));
        assertEquals(3, count(" ein  erster   Text      "));
        assertEquals(3, count("ein:erster.Text"));
    }

    @Test
    void vielleichtFalsch() {
        assertEquals(1, count("a"));
        assertEquals(1, count(" a"));
        assertEquals(1, count("a "));
        assertEquals(1, count(" a "));
    }

    @Test
    void mitHtml() {
        assertEquals(1, count(" eins  <html> "));
        assertEquals(1, count(" eins  < html> "));
        assertEquals(1, count(" eins  <html > "));
        assertEquals(1, count(" eins  < html > "));
        assertEquals(4, count(" eins <html> zwei<html>drei <html> vier"));

        assertEquals(2, count(" eins <html> zwei "));
        assertEquals(2, count(" eins <html>zwei "));
        assertEquals(2, count(" eins<html> zwei "));
        assertEquals(2, count(" eins<html>zwei "));
        assertEquals(2, count(" eins<img alt=\"xxx\" > zwei"));
        assertEquals(2, count(" eins<img alt=\"xxx yyy\" > zwei"));

        assertEquals(2, count(" eins \"zwei\" "));
        assertEquals(2, count(" eins\"zwei\" "));
        assertEquals(2, count(" eins \"zwei\""));
        assertEquals(3, count(" eins \"zwei\"drei"));
        assertEquals(3, count(" eins \"zwei\" drei"));
    }

    @Test
    void htmlTrickreich() {
        // Achtung: das ist teilweise nicht ganz legales HTML

        assertEquals(1, count(" eins<html"));

        assertEquals(2, count(" eins<img alt=\"<bild>\" > zwei"));
        assertEquals(2, count(" eins<img alt=\"bild>\" > zwei"));
        assertEquals(2, count(" eins<img alt=\"<bild>\" keinwort> zwei"));
        assertEquals(2, count(" eins<img alt=\"<bild>\" src=\"bild.png\" >zwei"));
        assertEquals(2, count(" eins<img alt=\"<bild\" keinwort>zwei"));

        assertEquals(1, count(" eins<img alt=\"<bild\" keinwort"));
        assertEquals(2, count(" eins<img alt=\"<bild\" keinwort> zwei"));
        assertEquals(1, count(" eins<img alt=\"<bild keinwort> keinwort"));
        assertEquals(2, count("eins<img alt=\"<bild keinwort keinwort\">zwei"));
        assertEquals(2, count(" eins<img alt=\"<bild keinwort< keinwort\">zwei"));

        assertEquals(2, count(" eins<img alt=\"<bild \\\" keinwort> keinwort\" keinwort>zwei"));
        assertEquals(2, count(" eins<img alt=\"<bild \\\" keinwort<keinwort\" keinwort>zwei"));
        assertEquals(2, count(" eins<img alt=\"<bild \\\" keinwort keinwort\" keinwort>zwei"));

        assertEquals(4, count(" \\\"null\\\" eins<img alt=\"<bild \\\" keinwort keinwort\" keinwort>zwei \"drei\""));
    }
}