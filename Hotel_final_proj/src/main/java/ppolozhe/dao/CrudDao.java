package ppolozhe.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> find(int id) throws Exception;
    boolean create(T entity) throws Exception;
    //<T> T findById(int id) throws Exception;
    boolean delete(int id) throws Exception;
    boolean update(T entity) throws Exception;
    List<T> findAll() throws Exception;
}
