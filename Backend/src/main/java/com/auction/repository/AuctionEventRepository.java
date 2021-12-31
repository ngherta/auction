package com.auction.repository;

import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.projection.AuctionEventSortProjection;
import com.auction.projection.CategoryCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionEventRepository extends JpaRepository<AuctionEvent, Long> {
  @Query(nativeQuery = true, value =
          "select * from auction as a " +
                  "where a.status = :status and a.start_date <= LOCALTIMESTAMP")
  List<AuctionEvent> getListForStartOrFinish(@Param("status") String status);

  List<AuctionEvent> findByStatusTypeAndStartDateLessThanEqual(AuctionStatus status, LocalDateTime dateTime);

  @Query(nativeQuery = true, value =
          "select a.id as auctionId, " +
                  "(select count(*) from auction_action aa where aa.auction_id = a.id) as count " +
                  "from auction AS a " +
                  "where a.status != 'BLOCKED' " +
                  "order by count")
  List<AuctionEventSortProjection> getAuctionEventForSorting();

  @Query(nativeQuery = true, value =
          "SELECT a.* FROM auction AS a " +
                  "LEFT JOIN auction_sort AS aa ON a.id = aa.auction_id " +
                  "WHERE a.status != 'BLOCKED' " +
                  "ORDER BY aa.rating")
  Page<AuctionEvent> getAuctionEventByRating(Pageable pageable);


  List<AuctionEvent> findByUser(User user);

  @Query(nativeQuery = true, value =
        "SELECT a.* FROM auction AS a " +
        "WHERE a.status = 'ACTIVE' OR " +
        "a.status = 'FINISHED' AND " +
        "a.title LIKE :search " +
        "LIMIT :rows")
  List<AuctionEvent> search(@Param("search") String message,
                            @Param("rows") int limit);

  @Query(nativeQuery = true, value = "" +
          "SELECT ae.* FROM auction AS ae " +
          "WHERE ae.id IN ( SELECT ac.auction_id FROM auction_category AS ac " +
          "WHERE ac.sub_category_id = :subCategoryId) " +
          "")
  Page<AuctionEvent> findByCategory(Long subCategoryId, Pageable pageable);


  @Query(nativeQuery = true, value = "" +
          "SELECT sc.category AS name, " +
          "       count(ac.auction_id) AS count " +
          "FROM auction_category AS ac " +
          "         LEFT JOIN sub_category sc on ac.sub_category_id = sc.id " +
          "         LEFT JOIN category c on sc.category_id = c.id " +
          "GROUP BY sc.category ")
  List<CategoryCountProjection> getCountPerSubCategory();

  @Query(nativeQuery = true, value = "" +
          "SELECT c.category AS name, " +
          "       count(ac.auction_id) AS count " +
          "FROM auction_category AS ac " +
          "         LEFT JOIN sub_category sc on ac.sub_category_id = sc.id " +
          "         LEFT JOIN category c on sc.category_id = c.id " +
          "GROUP BY c.category ")
  List<CategoryCountProjection> getCountPerCategory();
}
