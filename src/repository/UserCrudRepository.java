package repository;

import java.util.Optional;

public interface UserCrudRepository<U, V> extends CrudRepository<U, V> {
    Optional<U> login(String username, String password);
}
