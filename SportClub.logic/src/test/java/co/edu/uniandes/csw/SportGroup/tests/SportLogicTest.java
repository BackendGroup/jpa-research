/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.SportGroup.tests;

import co.edu.uniandes.csw.sportclub.ejbs.SportLogic;
import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import co.edu.uniandes.csw.sportclub.api.ISportLogic;
import co.edu.uniandes.csw.sportclub.converters.CountryConverter;
import co.edu.uniandes.csw.sportclub.converters.SportConverter;
import co.edu.uniandes.csw.sportclub.dtos.SportDTO;
import co.edu.uniandes.csw.sportclub.entities.SportEntity;
import static co.edu.uniandes.csw.SportGroup.tests._TestUtil.generateRandom;
import co.edu.uniandes.csw.sportclub.persistence.SportPersistence;
import java.util.ArrayList;
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

@RunWith(Arquillian.class)
public class SportLogicTest {

    public static final String DEPLOY = "Prueba";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(SportEntity.class.getPackage())
                .addPackage(SportDTO.class.getPackage())
                .addPackage(SportConverter.class.getPackage())
                .addPackage(ISportLogic.class.getPackage())
                .addPackage(SportLogic.class.getPackage())
                .addPackage(SportPersistence.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private ISportLogic sportLogic;

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
        em.createQuery("delete from SportEntity").executeUpdate();
    }

    private List<SportEntity> data = new ArrayList<SportEntity>();

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CountryEntity country = new CountryEntity();
            country.setName(generateRandom(String.class));
            country.setPopulation(generateRandom(Integer.class));
            em.persist(country);

            SportEntity entity = new SportEntity();
            entity.setName(generateRandom(String.class));
            entity.setMinAge(generateRandom(Integer.class));
            entity.setMaxAge(generateRandom(Integer.class));
            entity.setRules(generateRandom(String.class));
            entity.setCountry(country);

            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createSportTest() {
        CountryDTO country = CountryConverter.fullEntity2DTO(data.get(0).getCountry());
        SportDTO dto = new SportDTO();
        dto.setName(generateRandom(String.class));
        dto.setMinAge(generateRandom(Integer.class));
        dto.setMaxAge(generateRandom(Integer.class));
        dto.setRules(generateRandom(String.class));
        dto.setCountry(country);

        SportDTO result = sportLogic.createSport(dto);

        Assert.assertNotNull(result);

        SportEntity entity = em.find(SportEntity.class, result.getId());

        Assert.assertEquals(dto.getName(), entity.getName());
        Assert.assertEquals(dto.getMinAge(), entity.getMinAge());
        Assert.assertEquals(dto.getMaxAge(), entity.getMaxAge());
        Assert.assertEquals(dto.getRules(), entity.getRules());
        Assert.assertEquals(dto.getCountry().getId(), entity.getCountry().getId());
    }

    @Test
    public void getSportsTest() {
        List<SportDTO> list = sportLogic.getSports(null, null);
        Assert.assertEquals(list.size(), data.size());
        for (SportDTO dto : list) {
            boolean found = false;
            for (SportEntity entity : data) {
                if (dto.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getSportTest() {
        SportEntity entity = data.get(0);
        SportDTO dto = sportLogic.getSport(entity.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals(entity.getName(), dto.getName());
        Assert.assertEquals(entity.getMinAge(), dto.getMinAge());
        Assert.assertEquals(entity.getMaxAge(), dto.getMaxAge());
        Assert.assertEquals(entity.getRules(), dto.getRules());
        Assert.assertEquals(entity.getCountry().getId(), dto.getCountry().getId());
    }

    @Test
    public void deleteSportTest() {
        SportEntity entity = data.get(0);
        sportLogic.deleteSport(entity.getId());
        SportEntity deleted = em.find(SportEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateSportTest() {
        SportEntity entity = data.get(0);
        CountryDTO newCountry = CountryConverter.fullEntity2DTO(data.get(1).getCountry());

        SportDTO dto = new SportDTO();
        dto.setId(entity.getId());
        dto.setName(generateRandom(String.class));
        dto.setMinAge(generateRandom(Integer.class));
        dto.setMaxAge(generateRandom(Integer.class));
        dto.setRules(generateRandom(String.class));
        dto.setCountry(newCountry);

        sportLogic.updateSport(dto);

        SportEntity resp = em.find(SportEntity.class, entity.getId());

        Assert.assertEquals(dto.getName(), resp.getName());
        Assert.assertEquals(dto.getMinAge(), resp.getMinAge());
        Assert.assertEquals(dto.getMaxAge(), resp.getMaxAge());
        Assert.assertEquals(dto.getRules(), resp.getRules());
        Assert.assertEquals(dto.getCountry().getId(), resp.getCountry().getId());
    }

    @Test
    public void getSportPaginationTest() {
        //Page 1
        List<SportDTO> dto1 = sportLogic.getSports(1, 2);
        Assert.assertNotNull(dto1);
        Assert.assertEquals(2, dto1.size());
        //Page 2
        List<SportDTO> dto2 = sportLogic.getSports(2, 2);
        Assert.assertNotNull(dto2);
        Assert.assertEquals(1, dto2.size());

        for (SportDTO dto : dto1) {
            boolean found = false;
            for (SportEntity entity : data) {
                if (dto.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

        for (SportDTO dto : dto2) {
            boolean found = false;
            for (SportEntity entity : data) {
                if (dto.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void findByName() {
        String name = data.get(0).getName();
        List<SportEntity> cache = new ArrayList<SportEntity>();
        List<SportDTO> list = sportLogic.searchByName(name);

        for (SportEntity entity : data) {
            if (entity.getName().equals(name)) {
                cache.add(entity);
            }
        }
        Assert.assertEquals(list.size(), cache.size());
        for (SportDTO ent : list) {
            boolean found = false;
            for (SportEntity cacheEntity : cache) {
                if (cacheEntity.getName().equals(ent.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Assert.fail();
            }
        }
    }
}
