package dao;

import modelEntity.Accounts;

import java.util.Optional;

/**
 * Created by ppolozhe on 5/23/19.
 */
public interface AccountsDaoInterface extends CrudDao<Accounts>{
    Optional<Accounts> findAccountsByLogin(String login) throws Exception;
}
