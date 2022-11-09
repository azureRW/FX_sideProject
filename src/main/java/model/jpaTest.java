package model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface jpaTest extends PagingAndSortingRepository<tradeUser,Long> {
    Optional<tradeUser> findById(Long id);

}
