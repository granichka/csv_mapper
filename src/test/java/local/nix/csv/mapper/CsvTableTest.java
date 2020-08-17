package local.nix.csv.mapper;
import local.nix.csv.mapper.parser.CsvTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.*;

public class CsvTableTest {

    private CsvTable table;

    @BeforeEach
    public void setUp() throws Exception {
        Path source = Paths.get(getClass().getClassLoader().getResource("data.csv").toURI());
        table = CsvTable.fromFile(source).orElseThrow();
    }

    @Test
    public void getHeadersMethodTest() {
        assertEquals(4, table.getHeaders().size());
    }

    @Test
    public void getByRowAndColumnNumberMethodTest() {
        assertEquals("23", table.get(1, 1));
    }

    @Test
    public void getByRowNumberAndHeaderMethodTest() {
        assertEquals("recruiter", table.get(1, "occupation"));
    }
}
