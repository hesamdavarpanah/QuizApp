package repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<U, V> {
    List<U> findAll();

    Optional<U> findById(V id);

    V create(U obj);

    void update(V id, U obj);

    void delete(V id);
}
