package model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface jpaTest extends CrudRepository<tradeUser,Long> {
    Optional<tradeUser> findById(Long id);

}
