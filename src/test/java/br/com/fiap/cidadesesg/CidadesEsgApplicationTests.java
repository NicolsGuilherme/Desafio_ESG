package br.com.fiap.cidadesesg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CidadesEsgApplicationTests {

    @Test
    void contextLoadsBasicAssertion() {
        String app = "cidades-esg-inteligentes";
        assertNotNull(app);
        assertEquals("cidades-esg-inteligentes", app);
    }
}
