package server.database;

import commons.TDList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<TDList, Long> {
}
