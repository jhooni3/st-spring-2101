package com.example.demo.entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

public class FwkTransactionHstId implements Serializable {
    @Column
    LocalDate transactionDate;
    @Column(length = 50) String appName;
    @Column(length = 20) String appVersion;
    @Column(length = 36) String gid;
}


