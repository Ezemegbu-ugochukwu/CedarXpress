package com.example.cedarxpressliveprojectjava010.entity;

import com.example.cedarxpressliveprojectjava010.enums.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customer_user_detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class LoginTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean enabled;
    private boolean locked;
    private boolean accountExpired;
    private boolean credentialsExpired;
}