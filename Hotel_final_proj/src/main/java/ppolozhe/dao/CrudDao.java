package ppolozhe.dao;

import java.util.List;
import java.util.Optional;

/*
* Basic CRUD interfaces for Dao
*/
public interface CrudDao<T> {

    Optional<T> find(int id) throws Exception;
    boolean create(T entity) throws Exception;
    boolean delete(int id) throws Exception;
    boolean update(T entity) throws Exception;
    List<T> findAll() throws Exception;
}
