package org.example.debut.repositories;



import org.example.debut.entity.Card;
import org.example.debut.entity.RoleType;
import org.example.debut.entity.TemplateType;
import org.example.debut.web.DTO.response.TemplateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateTypeRepository extends JpaRepository<TemplateType, UUID>{

    Optional<TemplateType> findByName(String name);

    Page<TemplateType> findAllByRoleType(RoleType roleType, Pageable pageable);
}
