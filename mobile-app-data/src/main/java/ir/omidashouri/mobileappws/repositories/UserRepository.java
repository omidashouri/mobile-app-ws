package ir.omidashouri.mobileappws.repositories;

import ir.omidashouri.mobileappws.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserByEmail(String email);

    Iterable<User> findAllByEmail(String email);

    User findByEmail(String email);

//  use FIND to select record
//  use BY to specify which field
//  then specify the field name
    User findUserByUserId(String userId);
}
