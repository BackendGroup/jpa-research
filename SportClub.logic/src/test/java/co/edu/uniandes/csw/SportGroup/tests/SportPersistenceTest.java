package co.edu.uniandes.csw.SportGroup.tests;

import static co.edu.uniandes.csw.SportGroup.tests._TestUtil.generateRandom;
import co.edu.uniandes.csw.sportclub.converters.CountryConverter;
import co.edu.uniandes.csw.sportclub.converters.SportConverter;
import co.edu.uniandes.csw.sportclub.dtos.CountryDTO;
import co.edu.uniandes.csw.sportclub.dtos.SportDTO;
import co.edu.uniandes.csw.sportclub.entities.CountryEntity;
import co.edu.uniandes.csw.sportclub.entities.SportEntity;
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
public class SportPersistenceTest {

    public static final String DEPLOY = "Prueba";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(SportEntity.class.getPackage())
                .addPackage(SportDTO.class.getPackage())
                .addPackage(SportConverter.class.getPackage())
                .addPackage(SportPersistence.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private SportPersistence sportPersistence;

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
        SportEntity newEntity = new SportEntity();
        newEntity.setName(generateRandom(String.class));
        newEntity.setMinAge(generateRandom(Integer.class));
        newEntity.setMaxAge(generateRandom(Integer.class));
        newEntity.setRules(generateRandom(String.class));
        newEntity.setCountry(data.get(0).getCountry());

        SportEntity result = sportPersistence.create(newEntity);

        Assert.assertNotNull(result);

        SportEntity entity = em.find(SportEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getMinAge(), entity.getMinAge());
        Assert.assertEquals(newEntity.getMaxAge(), entity.getMaxAge());
        Assert.assertEquals(newEntity.getRules(), entity.getRules());
        Assert.assertEquals(newEntity.getCountry().getId(), entity.getCountry().getId());
    }

    @Test
    public void getSportsTest() {
        List<SportEntity> list = sportPersistence.findAll(null, null);
        Assert.assertEquals(list.size(), data.size());
        for (SportEntity ent : list) {
            boolean found = false;
            for (SportEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getSportTest() {
        SportEntity entity = data.get(0);
        SportEntity newEntity = sportPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getMinAge(), newEntity.getMinAge());
        Assert.assertEquals(entity.getMaxAge(), newEntity.getMaxAge());
        Assert.assertEquals(entity.getRules(), newEntity.getRules());
        Assert.assertEquals(entity.getCountry().getId(), newEntity.getCountry().getId());
    }

    @Test
    public void deleteSportTest() {
        SportEntity entity = data.get(0);
        sportPersistence.delete(entity.getId());
        SportEntity deleted = em.find(SportEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateSportTest() {
        SportEntity entity = data.get(0);

        SportEntity newEntity = new SportEntity();
        newEntity.setId(entity.getId());
        newEntity.setName(generateRandom(String.class));
        newEntity.setMinAge(generateRandom(Integer.class));
        newEntity.setMaxAge(generateRandom(Integer.class));
        newEntity.setRules(generateRandom(String.class));
        newEntity.setCountry(data.get(1).getCountry());

        sportPersistence.update(newEntity);

        SportEntity resp = em.find(SportEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getMinAge(), resp.getMinAge());
        Assert.assertEquals(newEntity.getMaxAge(), resp.getMaxAge());
        Assert.assertEquals(newEntity.getRules(), resp.getRules());
        Assert.assertEquals(newEntity.getCountry().getId(), resp.getCountry().getId());
    }

    @Test
    public void getSportPaginationTest() {
        //Page 1
        List<SportEntity> ent1 = sportPersistence.findAll(1, 2);
        Assert.assertNotNull(ent1);
        Assert.assertEquals(2, ent1.size());
        //Page 2
        List<SportEntity> ent2 = sportPersistence.findAll(2, 2);
        Assert.assertNotNull(ent2);
        Assert.assertEquals(1, ent2.size());

        for (SportEntity ent : ent1) {
            boolean found = false;
            for (SportEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

        for (SportEntity ent : ent2) {
            boolean found = false;
            for (SportEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
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
        List<SportEntity> list = sportPersistence.findByName(name);

        for (SportEntity entity : data) {
            if (entity.getName().equals(name)) {
                cache.add(entity);
            }
        }
        Assert.assertEquals(list.size(), cache.size());
        for (SportEntity ent : list) {
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
