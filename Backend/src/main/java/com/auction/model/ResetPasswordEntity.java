package com.auction.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reset_password")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResetPasswordEntity extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "code")
    private String code;

    @Column(name = "enabled")
    private boolean enabled;
}
