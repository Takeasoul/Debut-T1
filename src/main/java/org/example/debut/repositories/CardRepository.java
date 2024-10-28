package org.example.debut.repositories;


import lombok.extern.slf4j.Slf4j;
import org.example.debut.entity.Card;
import org.example.debut.entity.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID>{

    List<Card> findAllByTemplateType(TemplateType templateType);
}
