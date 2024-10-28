package org.example.debut.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "cards")
@FieldNameConstants
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = true)
    @ToString.Exclude
    private CardMember cardMember;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "template_type_id", referencedColumnName = "id", nullable = true)
    @ToString.Exclude
    private TemplateType templateType;
}