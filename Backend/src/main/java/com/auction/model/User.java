package com.auction.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class User extends AbstractEntity {

  @Column
  private String email;

  @Column
  private String password;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column(name = "birthday")
  private LocalDate birthday;

  @Column
  private boolean enabled = false;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> userRoles = new HashSet<>();

  @Column(name = "user_rating")
  private Double userRating;

  @Column(name = "gen_date")
  private LocalDateTime genDate;

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
