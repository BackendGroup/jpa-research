/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.SportGroup.tests;

import static co.edu.uniandes.csw.SportGroup.tests._TestUtil.generateRandom;
import co.edu.uniandes.csw.sportclub.api.ICountryLogic;
import co.edu.uniandes.csw.sportclub.converters.CountryConverter;
import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import co.edu.uniandes.csw.sportclub.ejbs.CountryLogic;
import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import co.edu.uniandes.csw.sportclub.persistence.CountryPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author afesguerra
 */
@RunWith(Arquillian.class)
public class CountryPersistenceTest {

    public static final String DEPLOY = "Prueba";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(CountryEntity.class.getPackage())
                .addPackage(CountryDTO.class.getPackage())
                .addPackage(CountryConverter.class.getPackage())
                .addPackage(CountryPersistence.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private CountryPersistence countryPersistence;

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
            entity.setFoundation(generateRandom(Date.class));
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createCountryTest() {
        CountryEntity newEntity = new CountryEntity();
        newEntity.setName(generateRandom(String.class));
        newEntity.setPopulation(generateRandom(Integer.class));
        newEntity.setFoundation(generateRandom(Date.class));

        CountryEntity result = countryPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CountryEntity entity = em.find(CountryEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getPopulation(), entity.getPopulation());
        Assert.assertEquals(newEntity.getFoundation(), entity.getFoundation());
    }

    @Test
    public void getCountrysTest() {
        List<CountryEntity> list = countryPersistence.findAll(null, null);
        Assert.assertEquals(list.size(), data.size());
        for (CountryEntity dto : list) {
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
        CountryEntity dto = countryPersistence.find(entity.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals(entity.getName(), dto.getName());
        Assert.assertEquals(entity.getPopulation(), dto.getPopulation());
        Assert.assertEquals(entity.getFoundation(), dto.getFoundation());
    }

    @Test
    public void deleteCountryTest() {
        CountryEntity entity = data.get(0);
        countryPersistence.delete(entity.getId());
        CountryEntity deleted = em.find(CountryEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateCountryTest() {
        CountryEntity entity = data.get(0);

        entity.setId(entity.getId());
        entity.setName(generateRandom(String.class));
        entity.setPopulation(generateRandom(Integer.class));
        entity.setFoundation(generateRandom(Date.class));

        countryPersistence.update(entity);

        CountryEntity resp = em.find(CountryEntity.class, entity.getId());

        Assert.assertEquals(entity.getName(), resp.getName());
        Assert.assertEquals(entity.getPopulation(), resp.getPopulation());
        Assert.assertEquals(entity.getFoundation(), resp.getFoundation());
    }

    @Test
    public void getCountryPaginationTest() {
        //Page 1
        List<CountryEntity> ent1 = countryPersistence.findAll(1, 2);
        Assert.assertNotNull(ent1);
        Assert.assertEquals(2, ent1.size());
        //Page 2
        List<CountryEntity> ent2 = countryPersistence.findAll(2, 2);
        Assert.assertNotNull(ent2);
        Assert.assertEquals(1, ent2.size());

        for (CountryEntity ent : ent1) {
            boolean found = false;
            for (CountryEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

        for (CountryEntity ent : ent2) {
            boolean found = false;
            for (CountryEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
}
