package ir.omidashouri.mobileappws.repositories;

import ir.omidashouri.mobileappws.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findUserByEmail(String email);

    User findUserByUserId(String userId);
}
