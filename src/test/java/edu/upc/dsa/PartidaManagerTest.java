package edu.upc.dsa;

import edu.upc.dsa.manager.*;
import edu.upc.dsa.util.IniciarDatosTests;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PartidaManagerTest {
    
    final static Logger logger = Logger.getLogger(PartidaManagerTest.class);
    TiendaManager tm;
    UsuarioManager um;
    PartidaManager pm;

    @Before
    public void setUp() {
        this.tm = TiendaManagerImpl.getInstance();
        this.um = UsuarioManagerImpl.getInstance();
        this.pm = PartidaManagerImpl.getInstance();
        this.tm.clear(); // Nos aseguramos que no haya datos anteriores
        this.um.clear();
        this.pm.clear();

        IniciarDatosTests.initUsuarios(this.um);
        IniciarDatosTests.initProductos(this.tm);
    }

    @After
    public void tearDown() {
        this.tm.clear();
        this.um.clear();
        this.pm.clear();
    }

    @Test
    public void addPartidaTest() throws Exception {
        this.pm.addPartida("1", "Paco", 3, 100, 0);
        this.pm.getPartidas("Paco");
        assertEquals(1,this.pm.sizePartidas("Paco"));
        this.pm.addPartida("2", "Paco", 3, 100, 0);
        assertEquals(2,this.pm.sizePartidas("Paco"));
        this.pm.deletePartida("Paco", "1");
        assertEquals(1,this.pm.sizePartidas("Paco"));
    }
}
