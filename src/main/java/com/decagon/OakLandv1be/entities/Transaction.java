package com.decagon.OakLandv1be.entities;


import com.decagon.OakLandv1be.enums.TransactionStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends BaseEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @OneToOne
    private Order order;

}
