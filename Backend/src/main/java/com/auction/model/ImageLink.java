package com.auction.model;

import com.auction.model.enums.ImageLinkType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "image_link",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "sequence")
        })
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ImageLink extends AbstractEntity {
  @Column(name = "image_link")
  private String imageLink;

  @Column(name = "url")
  private String url;

  @Enumerated(EnumType.STRING)
  private ImageLinkType type;

  @Column(name = "sequence")
  private Integer sequence;

  @Column(name = "internal_link")
  private Boolean internalLink;
}
