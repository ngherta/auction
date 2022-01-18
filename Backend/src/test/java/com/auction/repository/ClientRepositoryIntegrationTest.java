//package com.auction.repository;
//
//import com.auction.model.AuctionEvent;
//import com.auction.model.enums.AuctionStatus;
//import com.auction.model.enums.AuctionType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.utility.DockerImageName;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class ClientRepositoryIntegrationTest {
//  public static final PostgreSQLContainer<?> postgres =
//          new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));
//
//  static {
//    postgres.start();
//  }
//
//  @Autowired
//  private AuctionEventRepository auctionEventRepository;
//  @Autowired
//  //Alternative to EntityManager for use in JPA tests.
//  // Provides a subset of EntityManager methods that are useful for tests as well as helper methods for common testing tasks such as persist/flush/find.
//  private TestEntityManager entityManager; //convenient for not injecting other repositories
//
//  @DynamicPropertySource
//  public static void properties(DynamicPropertyRegistry registry) {
//    registry.add("spring.datasource.url", postgres::getJdbcUrl);
//    registry.add("spring.datasource.username", postgres::getUsername);
//    registry.add("spring.datasource.password", postgres::getPassword);
//  }
//
//  @Test
//  @Sql("/test-data/auctionEvent/lot_test_1.sql")
//  void findByStatus_returnsListOfClients() {
//    final AuctionEvent expectedAuctionEvent = AuctionEvent.builder()
//            .title("Nike Air Jordan 1")
//            .auctionType(AuctionType.COMMERCIAL)
//            .description("Best shoes!")
//            .startDate(LocalDateTime.parse("2022-01-15 18:30:31.291387"))
//            .finishDate(LocalDateTime.parse("2022-01-15 18:30:31.291387"))
//            .startPrice(2.0D)
//            .finishPrice(100.0D)
//            .statusType(AuctionStatus.EXPECTATION)
//            .charityPercent(0D)
//            .genDate(LocalDateTime.parse("2022-01-15 18:30:31.291387"))
////            .user()
//            .build();
//
//    final List<AuctionEvent> auctionEventList = auctionEventRepository.getListForFinish(AuctionStatus.ACTIVE.name());
//    assertThat(auctionEventList).hasSize(1);
////    assertThat(auctionEventList.get(0)).usingRecursiveComparison().isEqualTo(expectedAuctionEvent);
//  }
//}
