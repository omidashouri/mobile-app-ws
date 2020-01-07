package ir.omidashouri.mobileappws.repositories;

import ir.omidashouri.mobileappws.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserByEmail(String email);

    Iterable<User> findAllByEmail(String email);

    User findByEmail(String email);

//  use FIND to select record
//  use BY to specify which field
//  then specify the field name
    User findUserByUserPublicId(String userPublicId);

    User findUserByEmailVerificationToken(String token);

    @Query(value = "select * from photo_app.tbl_user u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            countQuery = "select count(*) from photo_app.tbl_user u where u.email_verification_status = 'true'",
            nativeQuery = true)
    Page<User> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);

    @Query(value = "select * from photo_app.tbl_user u where u.first_name = ?1 or last_name = ?2"
            ,nativeQuery = true)
    List<User> findUserByFirstNameOrLastName(String firstName,String lastName);

    @Query(value = "select * from photo_app.tbl_user u where u.last_name = :lName "
            ,nativeQuery = true)
    List<User> findUserByLastName(@Param("lName") String lastName);

    @Query(value = "select * from photo_app.tbl_user u where u.first_name like CONCAT('%',:kWord,'%') or u.last_name like CONCAT('%',:kWord,'%')"
            ,nativeQuery = true)
    List<User> findUsersByKeyword(@Param("kWord") String keyWord);

    @Query(value = "select u.first_name, u.last_name from photo_app.tbl_user u where u.first_name like CONCAT('%',:kWord,'%') or u.last_name like CONCAT('%',:kWord,'%')"
            ,nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("kWord") String keyWord);


//    use help to perform modify queries and if error occurred database record roll backed,
//    if we write modify query in Service or Controller class then use this annotation on that classes
//    use '@Modifying' for update and delete in SQL query
    @Transactional
    @Modifying
    @Query(value = "update photo_app.tbl_user u set u.email_verification_status=:eVerStatus where u.user_public_id=:uId"
            ,nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("eVerStatus") boolean emailVerificationStatus, @Param("uId") String userId);


    @Query("select user from User user where user.userPublicId=:uPublicId")
    User findUserByUserPublicIdJpql(@Param("uPublicId") String userPublicId);

    @Query("select user.firstName,user.lastName from User user where user.userPublicId=:uPublicId")
    List<Object[]> getUserFullNameByUserPublicId(@Param("uPublicId") String userPublicId);

    @Transactional
    @Modifying
    @Query("update User user set user.emailVerificationStatus =:eVStatus where user.userPublicId =:uPublicId ")
    void updateUserEmailVerificationStatusJpql(@Param("eVStatus") boolean emailVerificationStatus,@Param("uPublicId") String userPublicId);

}
