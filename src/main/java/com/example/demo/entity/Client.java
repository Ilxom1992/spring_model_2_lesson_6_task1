package com.example.demo.entity;

import com.example.demo.model.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends AbsEntity {

    private String passportNumber;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private RedisProperties.ClientType clientType;

    @ManyToMany
    private Set<Role> roles;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SimCard> simCardList;

}
