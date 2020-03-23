package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer age;
    @Column
    private BigDecimal money;
    //getter setter
}
