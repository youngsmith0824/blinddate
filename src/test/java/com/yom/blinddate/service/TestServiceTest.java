package com.yom.blinddate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.condition.OS.MAC;

import com.yom.blinddate.model.TestModel;
import com.yom.blinddate.model.entity.Animal;
import com.yom.blinddate.model.entity.Cctv;
import com.yom.blinddate.model.entity.Farm;
import com.yom.blinddate.repository.AnimalRepository;
import com.yom.blinddate.repository.CctvRepository;
import com.yom.blinddate.repository.FarmRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@EnabledOnOs(MAC)
//@Disabled
class TestServiceTest {
  @Autowired
  private TestService testService;

  @Autowired
  private FarmRepository farmRepository;

  @Autowired
  private AnimalRepository animalRepository;

  @Autowired
  private CctvRepository cctvRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  void test() {
    List<TestModel> testModels = testService.test();
  }

  @Test
  @Transactional
  void test2() {
    Farm farm = new Farm().setOwner("qqq")
        .setName("www");
 //       .setId(100L);

    Farm farm2 = farmRepository.save(farm);

    //assertThat("farm != farm2", farm == farm2);
    assertEquals(farm, farm2, "farm != farm2 (2)");

    farmRepository.findById(farm.getId());

    // https://www.whiteship.me/spring-transactional-and-spring-aop/
    // https://stackoverflow.com/questions/8529542/entitymanager-contains-returns-false-after-persist
    assertTrue(entityManager.contains(farm), "entityManager contains farm");
    assertTrue(entityManager.contains(farm2), "entityManager contains farm2");

  }

  @Test
  @Transactional
  void test3() {
    Farm farm = new Farm().setOwner("qqq")
        .setName("www")
           .setId(100L);

    Farm farm2 = farmRepository.save(farm);

    assertNotEquals(farm, farm2, "farm == farm2 (2)");

    farmRepository.findById(farm2.getId());

    // https://www.whiteship.me/spring-transactional-and-spring-aop/
    // https://stackoverflow.com/questions/8529542/entitymanager-contains-returns-false-after-persist
    assertFalse(entityManager.contains(farm), "entityManager contains farm");
    assertTrue(entityManager.contains(farm2), "entityManager contains farm2");

  }

  @Test
  @Transactional
  void test4() {
    Farm farm1 = farmRepository.getById(1L);
    System.out.println(farm1);
    Long id = farm1.getId();
    String name = farm1.getName();
    String owner = farm1.getOwner();
    List<Animal> animals = farm1.getAnimals();

    System.out.println("");
  }


  public static class Abc {
    String a;

    @Override
    public String toString() {
      return a;
    }
    public void setA(String a) {
      this.a = a;
    }

    public String getA() {
      return a;
    }
  }

  public static class Abcd extends Abc {
    String a;

    @Override
    public String toString() {
      System.out.println(super.getA());
      return a;
    }

    public void setA(String a) {
      this.a = a;
    }

    public String getA() {
      return a;
    }
  }

  @Transactional
  @Test
  void test5() {
    Abcd abcd = new Abcd();
    abcd.setA("zzzzz");
    System.out.println(abcd);

    Cctv cctv = new Cctv();
    cctv.setOffer("tmap");
    cctv.setMeshId(191919L);
    cctv.setRoadCate("complex");
    cctv.setSnapshotId(1010L);

    cctvRepository.save(cctv);
  }

  @Test
  void test6() {
    Abcd abcd = new Abcd();
    abcd.setA("a");
    Abcd abcd2 = new Abcd();
    abcd2.setA("a");

    System.out.println(abcd.equals(abcd2));
  }

}