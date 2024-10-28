package org.example.debut.services;


import org.example.debut.web.DTO.response.ModelListResponse;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

public interface EntityService<E, R, ID> {

    CompletableFuture<ModelListResponse<E>> findAll(Pageable pageable);

    E findById(ID id);

    E create(R entityRequest);

    E update(ID id, R entityRequest);

    void deleteById(ID id);

}