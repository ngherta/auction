package com.auction.repository;

import com.auction.model.ImageLink;
import com.auction.model.enums.ImageLinkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageLinkRepository extends JpaRepository<ImageLink, Long> {
  List<ImageLink> findAllByType(ImageLinkType type);
}
