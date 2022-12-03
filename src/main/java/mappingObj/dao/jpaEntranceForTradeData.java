package mappingObj.dao;

import model.userRecode;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface jpaEntranceForTradeData extends CrudRepository<userRecode,Long> {
    ArrayList<userRecode> findByOuterJoinAndOffsetIsFalse(Long out);
    ArrayList<userRecode> findByOuterJoin(Long out);
}
