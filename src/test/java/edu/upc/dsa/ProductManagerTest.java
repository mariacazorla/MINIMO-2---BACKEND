package edu.upc.dsa;

import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductManagerTest {
    final static Logger logger = Logger.getLogger(ProductManagerTest.class); //
    ProductManager pm;

    @Before
    public void setUp(){
        this.pm = ProductManagerImpl.getInstance();
        this.pm.addUsuario("Paco", "1234");
        this.pm.addUsuario("Lopez", "1234");
        this.pm.addUsuario("Ana", "1234");
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.pm.clear();
    }

    @Test
    public void addUsuarioTest() throws Exception{
        Assert.assertEquals(3, pm.sizeUsuarios());

        this.pm.addUsuario("John", "1234");

        Assert.assertEquals(4, pm.sizeUsuarios());

        Assert.assertThrows(UsuarioYaExisteException.class, () ->
                this.pm.addUsuario("John", "1234"));

    }

    @Test
    public void getUsuarioTest() throws Exception{
        Assert.assertEquals(3, pm.sizeUsuarios());
        Usuario u = this.pm.getUsuario("Paco");
        Assert.assertEquals("Paco", u.getNombreUsu());
        Assert.assertEquals("1234", u.getPassword());

        Assert.assertThrows(UsuarioNotFoundException.class, () ->
                this.pm.getUsuario("UUUUUU"));
    }

    @Test
    public void loginUsuarioTest() throws Exception{
        Assert.assertEquals(3, pm.sizeUsuarios());
        Usuario u = this.pm.loginUsuario("Paco","1234");
        Assert.assertEquals("Paco", u.getNombreUsu());
        Assert.assertEquals("1234", u.getPassword());

        Assert.assertThrows(PasswordNotMatchException.class, () ->
                this.pm.loginUsuario("Paco","PPPPPP"));

    }
}
