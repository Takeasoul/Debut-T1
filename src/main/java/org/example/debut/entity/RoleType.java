package org.example.debut.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@Table(name = "roles")
public class RoleType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "roleType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TemplateType> templateTypes;
}