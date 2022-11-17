package mappingObj;

import org.springframework.data.repository.CrudRepository;

public interface jpaEntranceForUsers extends CrudRepository<tradeUser,Long> {
    tradeUser findByUserAccount(String Account);
    tradeUser findByUserAccountAndUserPassword(String Account,String Password);
    Boolean existsByUserAccount(String Account);
}
