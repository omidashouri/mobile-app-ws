package ir.omidashouri.mobileappws.repositories;

import ir.omidashouri.mobileappws.domain.PasswordResetTokenEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends PagingAndSortingRepository<PasswordResetTokenEntity,Long> {

    PasswordResetTokenEntity findByToken(String token);
}
