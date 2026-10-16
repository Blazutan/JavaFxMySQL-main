package co.edu.poli.examen2_Soto.tests.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_Soto.modelo.Credito;
import co.edu.poli.examen2_Soto.modelo.Debito;
import co.edu.poli.examen2_Soto.modelo.EstadoTarjeta;
import co.edu.poli.examen2_Soto.modelo.Tarjeta;
import co.edu.poli.examen2_Soto.modelo.Titular;
import co.edu.poli.examen2_Soto.servicios.DAOTarjeta;

public class TestDAOTarjeta {

    DAOTarjeta dao = new DAOTarjeta();

    @Test
    void create_debito_y_readone() throws Exception {

        Titular titular = new Titular("T001", "Test");

        Debito debito = new Debito(
                "999005",
                "2025-12-25",
                true,
                titular,
                5000.0
        );

        String result = dao.create(debito);

        assertTrue(result.contains("guardada"));

        Tarjeta t = dao.readone("999005");

        assertNotNull(t);
        assertTrue(t instanceof Debito);
        assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());

        Debito d = (Debito) t;
        assertEquals(5000.0, d.getSaldo());
    }

    @Test
    void create_credito_y_readone() throws Exception {

        Titular titular = new Titular("T001", "Test");

        Credito credito = new Credito(
                "999006",
                "2025-12-25",
                true,
                titular,
                10000.0
        );

        String result = dao.create(credito);

        assertTrue(result.contains("guardada"));

        Tarjeta t = dao.readone("999006");

        assertNotNull(t);
        assertTrue(t instanceof Credito);
        assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());

        Credito c = (Credito) t;
        assertEquals(10000.0, c.getLimite());
    }

    @Test
    void create_y_readone_conservanEstadoCancelada() throws Exception {

        Titular titular = new Titular("T001", "Test");
        Debito debito = new Debito(
                "999007",
                "2025-12-25",
                EstadoTarjeta.CANCELADA,
                titular,
                5000.0
        );

        String result = dao.create(debito);

        assertTrue(result.contains("guardada"));
        assertEquals(EstadoTarjeta.CANCELADA, dao.readone("999007").getEstado());
    }

    @Test
    void updateEstado_persisteNuevoEstado() throws Exception {

        Titular titular = new Titular("T001", "Test");
        Debito debito = new Debito(
                "999008",
                "2025-12-25",
                EstadoTarjeta.ACTIVA,
                titular,
                5000.0
        );

        dao.create(debito);
        dao.updateEstado("999008", EstadoTarjeta.BLOQUEADA);

        assertEquals(EstadoTarjeta.BLOQUEADA, dao.readone("999008").getEstado());
    }

    @Test
    void readone_noExiste() throws Exception {

        Tarjeta t = dao.readone("000000");

        assertNull(t);
    }
}
