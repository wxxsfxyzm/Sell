package com.carlyu.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "student_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    // @GeneratedValue
    @Column(name = "student_id")
    private String userId;

    @Column(name = "student_name")
    private String username;

    private String password;

    private BigDecimal balance;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String userId,
                String username,
                String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.balance = BigDecimal.valueOf(0.00);
    }

}
