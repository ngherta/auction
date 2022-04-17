package com.auction.repository;

import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.projection.AuctionEventSortProjection;
import com.auction.projection.AuctionSearchProjection;
import com.auction.projection.CategoryCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionEventRepository extends JpaRepository<AuctionEvent, Long>,
        JpaSpecificationExecutor<AuctionEvent> {
  @Query(nativeQuery = true, value =
          "select * from auction as a " +
                  "where a.status = :status and a.start_date <= LOCALTIMESTAMP")
  List<AuctionEvent> getListForStart(@Param("status") String status);

  @Query(nativeQuery = true, value =
          "select * from auction as a " +
                  "where a.status = :status and a.finish_date <= LOCALTIMESTAMP")
  List<AuctionEvent> getListForFinish(@Param("status") String status);

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
                  "WHERE a.status != 'BLOCKED'")
  Page<AuctionEvent> getAuctionEventByRating(Pageable pageable);


  List<AuctionEvent> findByUser(User user);

  Page<AuctionEvent> findByUser(User user, Pageable pageable);

  @Query(nativeQuery = true, value = "" +
          "SELECT ae.* FROM auction AS ae " +
          "WHERE ae.id IN ( SELECT ac.auction_id FROM auction_category AS ac " +
          "WHERE ac.sub_category_id = :subCategoryId )")
  Page<AuctionEvent> findByCategory(@Param("subCategoryId") Long subCategoryId, Pageable pageable);


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

  @Query(nativeQuery = true, value = "" +
          " SELECT a.id as id, " +
          " a.title as title," +
          " a.status as status, " +
          " a.start_date as startDate, " +
          " a.finish_date as finishDate " +
          " FROM auction a " +
          " WHERE lower(a.title) like '%' || lower(:message) || '%' " +
          " OR lower(a.description)  like '%' || lower(:message) || '%' " +
          " LIMIT :limit ")
  List<AuctionSearchProjection> findByText(@Param("message") String message, Integer limit);

  @Query(nativeQuery = true, value = "" +
      "SELECT * FROM auction a " +
      "        WHERE a.id in (SELECT aa.auction_id " +
      "        FROM auction_action aa WHERE aa.user_id = :userId)")
  Page<AuctionEvent> findByUserParticipant(@Param("userId") Long userId, Pageable pageable);
}
