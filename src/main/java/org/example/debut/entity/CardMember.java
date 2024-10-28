package org.example.debut.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@Table(name = "card_members")
@FieldNameConstants
public class CardMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "company")
    private String company;

    @Column(name = "position")
    private String position;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinTable(name = "members_roles",
            joinColumns = @JoinColumn(name = "memebr_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private RoleType role;

    @OneToOne(mappedBy = "cardMember", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Card card;


}