package mappingObj;

import antlr.collections.List;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Iterator;

public interface jpaEntranceForTradeData extends CrudRepository<userRecode,Long> {
    ArrayList<userRecode> findByOuterJoinAndOffsetIsFalse(Long out);
    ArrayList<userRecode> findByOuterJoin(Long out);
}
