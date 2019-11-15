package ir.omidashouri.mobileappws.repositories;

import ir.omidashouri.mobileappws.domain.Address;
import ir.omidashouri.mobileappws.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address,Long> {

    List<Address> findAllByUser(User user);

    Address findByAddressPublicId(String addressPublicId);
}
