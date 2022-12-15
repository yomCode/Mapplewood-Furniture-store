package com.decagon.OakLandv1be.entities;

import com.decagon.OakLandv1be.enums.TokenStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
public class Token extends BaseEntity {

    private String token;
    private TokenStatus tokenStatus;

    @OneToOne()
    @JoinColumn(name = "person_tbl_id")
    private Person person;
}
