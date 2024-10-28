package org.example.debut.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@Table(name = "template_types")
@FieldNameConstants
@NoArgsConstructor
public class TemplateType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinTable(
            name = "roles_template_types",
            joinColumns = @JoinColumn(name = "template_type_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private RoleType roleType;

    @OneToMany(mappedBy = "templateType", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Card> cards;

}