package com.auction.model;

import com.auction.model.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//TODO: if remove AllArgsConstructor appears error
@AllArgsConstructor
public class Role extends AbstractEntity implements Serializable {
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserRole userRole;
}
