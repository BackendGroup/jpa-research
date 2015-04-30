/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.SportGroup.web.test;

import co.edu.uniandes.csw.SportGroup.service.CountryService;
import co.edu.uniandes.csw.SportGroup.service.SportService;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Jj.alarcon10
 */
//Anotacion de Junit para indicar el orden de ejecucion
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class SportClubTest {

    public static String baseUrl;
    public static String URLRESOURCES = "src/main/webapp";
    public static WebDriver driver;
    private static StringBuffer verificationErrors = new StringBuffer();

    // Esta variable contendra la URL que utiliza arquillian paraejecutar el contenedor
    @ArquillianResource
    URL deploymentURL;

    @Deployment
    public static Archive<?> createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        WebArchive war = ShrinkWrap
                // Nombre del Proyecto "SportClub.service" seguido de ".war". Debe ser el mismo nombre del proyecto web que contiene los javascript y los  servicios Rest
                .create(WebArchive.class, "SportClub.service.war")
                // Se agrega la dependencia a la l�gica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(resolver.artifact("co.edu.uniandes.csw.SportGroup:SportClub.logic:0.0.1")
                        .resolveAsFiles())
                // Se agregan los compilados de los paquetes que se van a probar
                .addPackage(CountryService.class.getPackage())
                .addPackage(SportService.class.getPackage())
                // Se agregan los html, el bootstrap y los javascript. 
                .addAsWebResource(new File(URLRESOURCES, "index.html"))
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/").as(GenericArchive.class), "/src/", Filters.includeAll())
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/assets/css/").as(GenericArchive.class), "/src/assets/css/", Filters.includeAll())
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/assets/fonts/").as(GenericArchive.class), "/src/assets/fonts/", Filters.includeAll())
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/assets/libs/").as(GenericArchive.class), "/src/assets/libs/", Filters.includeAll())
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/shared/crud").as(GenericArchive.class), "/src/shared/crud", Filters.includeAll())
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/shared/mocks").as(GenericArchive.class), "/src/shared/mocks", Filters.includeAll())
                // Los siguientes son los javascript de los modulos que se van a probar.
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/modules/country/").as(GenericArchive.class), "/src/modules/country/", Filters.includeAll())
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/modules/sport/").as(GenericArchive.class), "/src/modules/sport/", Filters.includeAll())
                // El archivo que contiene la configuracion a la base de datos. 
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
//                // El archivo beans.xml es necesario para injecci�n de dependencias. 
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));

        return war;
    }

    @BeforeClass
    public static void setUp() {
        // Se crea un ainstancia del driver de firefox sobre el que se ejecutara la aplicacion.
        driver = new FirefoxDriver();
//        // Este timeout sirve para esperar que inicie el firefox
//        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
    }

    @Before
    public void setUpTest() {
        // El browser ira a esta url. Se ejecuta al inicar cada uno de los metodos de prueba indicados con @Test
        driver.get(deploymentURL + "index.html");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        //Se ejecuta al terminar todos los metodos de prueba indicados con @Test
        // Cierra el browser
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @Test
    @RunAsClient
    public void t1createCountry() throws InterruptedException {
        Thread.sleep(1500);
        boolean success = false;
        driver.findElement(By.partialLinkText("Country")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("createBtn")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Colombia");
        driver.findElement(By.id("population")).clear();
        driver.findElement(By.id("population")).sendKeys("80000000");
        driver.findElement(By.id("saveBtn")).click();
        Thread.sleep(2000);
        List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@id,'recordList')]/tbody/tr"));
        for (WebElement webElement : rows) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
            if (elems.get(0).getText().equals("Colombia") && elems.get(1).getText().equals("80000000")) {
                success = true;
            }
        }
        assertTrue(success);
        Thread.sleep(1000);
    }

    @Test
    @RunAsClient
    public void t2createSport() throws Exception {
        boolean success = false;
        driver.findElement(By.partialLinkText("Sport")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("createBtn")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Futbol");
        driver.findElement(By.id("minAge")).clear();
        driver.findElement(By.id("minAge")).sendKeys("3");
        driver.findElement(By.id("maxAge")).clear();
        driver.findElement(By.id("maxAge")).sendKeys("90");
        driver.findElement(By.id("rules")).clear();
        driver.findElement(By.id("rules")).sendKeys("No rules");
        Select contries = new Select(driver.findElement(By.id("country")));
        contries.selectByVisibleText("Colombia");
        driver.findElement(By.id("saveBtn")).click();
        Thread.sleep(3000);
        List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@id,'recordList')]/tbody/tr"));
        for (WebElement webElement : rows) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
            if (elems.get(0).getText().equals("Futbol") && elems.get(5).getText().equals("Colombia")) {
                success = true;
            }
        }
        assertTrue(success);
        Thread.sleep(1000);
    }

    @Test
    @RunAsClient
    public void t3deleteSport() throws Exception {
        boolean success = false;
        driver.findElement(By.partialLinkText("Sport")).click();
        Thread.sleep(3000);
        List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@id,'recordList')]/tbody/tr"));
        for (WebElement webElement : rows) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
            if (elems.get(0).getText().equals("Futbol") && elems.get(5).getText().equals("Colombia")) {
                List<WebElement> buttons = elems.get(6).findElements(By.xpath("button"));
                buttons.get(1).click();
                success = true;
                break;
            }
        }
        Thread.sleep(3000);
        //Se busca el objeto que fue eliminado, si se encuentra hay error al eliminar
        rows = driver.findElements(By.xpath("//table[contains(@id,'recordList')]/tbody/tr"));
        if (rows.size() > 0) {
            for (WebElement webElement : rows) {
                List<WebElement> elems = webElement.findElements(By.xpath("td"));
                if (elems.get(0).getText().equals("Futbol") && elems.get(5).getText().equals("Colombia")) {
                    success = false;
                    break;
                }
            }
        }

        Thread.sleep(1000);
        assertTrue(success);
    }

    @Test
    @RunAsClient
    public void t4deleteCountry() throws Exception {
        boolean success = false;
        driver.findElement(By.partialLinkText("Country")).click();
        Thread.sleep(1500);
        List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@id,'recordList')]/tbody/tr"));
        for (WebElement webElement : rows) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
            if (elems.get(0).getText().equals("Colombia") && elems.get(1).getText().equals("80000000")) {
                List<WebElement> buttons = elems.get(2).findElements(By.xpath("button"));
                buttons.get(1).click();
                success = true;
                break;
            }
        }
        Thread.sleep(2000);
        //Se busca el objeto que fue eliminado, si se encuentra hay error al eliminar
        rows = driver.findElements(By.xpath("//table[contains(@id,'recordList')]/tbody/tr"));
        if (rows.size() > 0) {
            for (WebElement webElement : rows) {
                List<WebElement> elems = webElement.findElements(By.xpath("td"));
                if (elems.get(0).getText().equals("Colombia") && elems.get(1).getText().equals("80000000")) {
                    success = false;
                    break;
                }
            }
        }
        Thread.sleep(2000);
        assertTrue(success);
    }
}
