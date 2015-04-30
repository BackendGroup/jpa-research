package co.edu.uniandes.csw.SportGroup.country.logic.ejb;

import co.edu.uniandes.csw.SportGroup.country.logic.api.ICountryLogic;
import co.edu.uniandes.csw.SportGroup.country.logic.converter.CountryConverter;
import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryDTO;
import co.edu.uniandes.csw.SportGroup.country.logic.dto.CountryPageDTO;
import co.edu.uniandes.csw.SportGroup.country.logic.entity.CountryEntity;
import static co.edu.uniandes.csw.SportGroup.util._TestUtil.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CountryLogicTest {
    public static final String DEPLOY = "Prueba";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(CountryEntity.class.getPackage())
                .addPackage(CountryDTO.class.getPackage())
                .addPackage(CountryConverter.class.getPackage())
                .addPackage(CountryLogic.class.getPackage())
                .addPackage(ICountryLogic.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private ICountryLogic countryLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    @Before
    public void configTest() {
        System.out.println("em: " + em);
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from CountryEntity").executeUpdate();
    }

    private List<CountryEntity> data = new ArrayList<CountryEntity>();

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CountryEntity entity = new CountryEntity();
            entity.setName(generateRandom(String.class));
            entity.setPopulation(generateRandom(Integer.class));
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createCountryTest() {
        CountryDTO dto = new CountryDTO();
        dto.setName(generateRandom(String.class));
        dto.setPopulation(generateRandom(Integer.class));

        CountryDTO result = countryLogic.createCountry(dto);

        Assert.assertNotNull(result);

        CountryEntity entity = em.find(CountryEntity.class, result.getId());

        Assert.assertEquals(dto.getName(), entity.getName());
        Assert.assertEquals(dto.getPopulation(), entity.getPopulation());
    }

    @Test
    public void getCountrysTest() {
        List<CountryDTO> list = countryLogic.getCountries();
        Assert.assertEquals(list.size(), data.size());
        for (CountryDTO dto : list) {
            boolean found = false;
            for (CountryEntity entity : data) {
                if (dto.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getCountryTest() {
        CountryEntity entity = data.get(0);
        CountryDTO dto = countryLogic.getCountry(entity.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals(entity.getName(), dto.getName());
        Assert.assertEquals(entity.getPopulation(), dto.getPopulation());

    }

    @Test
    public void deleteCountryTest() {
        CountryEntity entity = data.get(0);
        countryLogic.deleteCountry(entity.getId());
        CountryEntity deleted = em.find(CountryEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateCountryTest() {
        CountryEntity entity = data.get(0);

        CountryDTO dto = new CountryDTO();
        dto.setId(entity.getId());
        dto.setName(generateRandom(String.class));
        dto.setPopulation(generateRandom(Integer.class));

        countryLogic.updateCountry(dto);

        CountryEntity resp = em.find(CountryEntity.class, entity.getId());

        Assert.assertEquals(dto.getName(), resp.getName());
        Assert.assertEquals(dto.getPopulation(), resp.getPopulation());
    }

    @Test
    public void getCountryPaginationTest() {
        //Page 1
        CountryPageDTO dto1 = countryLogic.getCountries(1, 2);
        Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(), 2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(), 3L);
        //Page 2
        CountryPageDTO dto2 = countryLogic.getCountries(2, 2);
        Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(), 1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(), 3L);

        for (CountryDTO dto : dto1.getRecords()) {
            boolean found = false;
            for (CountryEntity entity : data) {
                if (dto.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

        for (CountryDTO dto : dto2.getRecords()) {
            boolean found = false;
            for (CountryEntity entity : data) {
                if (dto.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
}
