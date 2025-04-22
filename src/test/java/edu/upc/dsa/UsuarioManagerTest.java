package edu.upc.dsa;

import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.manager.UsuarioManagerImpl;
import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioManagerTest {

    final static Logger logger = Logger.getLogger(UsuarioManagerTest.class);
    UsuarioManager um;

    @Before
    public void setUp() {
        this.um = UsuarioManagerImpl.getInstance();
        this.um.clear(); // Nos aseguramos que no haya datos anteriores
        this.um.addUsuario("Paco", "1234");
        this.um.addUsuario("Lopez", "1234");
        this.um.addUsuario("Ana", "1234");
        this.um.addUsuario("Miguel", "1234");
    }

    @After
    public void tearDown() {
        this.um.clear();
    }

    @Test
    public void addUsuarioTest() throws Exception {
        assertEquals(4, um.sizeUsuarios());
        this.um.addUsuario("John", "1234");
        assertEquals(5, um.sizeUsuarios());
        assertThrows(UsuarioYaExisteException.class, () ->
                this.um.addUsuario("John", "1234"));
    }

    @Test
    public void getUsuarioTest() throws Exception {
        Usuario u = this.um.getUsuario("Paco");
        assertEquals("Paco", u.getNombreUsu());
        assertEquals("1234", u.getPassword());

        assertThrows(UsuarioNotFoundException.class, () ->
                this.um.getUsuario("UUUUUU"));
    }

    @Test
    public void loginUsuarioTest() throws Exception {
        Usuario u = this.um.loginUsuario("Paco", "1234");
        assertEquals("Paco", u.getNombreUsu());

        assertThrows(PasswordNotMatchException.class, () ->
                this.um.loginUsuario("Paco", "PPPPPP"));
    }

}
