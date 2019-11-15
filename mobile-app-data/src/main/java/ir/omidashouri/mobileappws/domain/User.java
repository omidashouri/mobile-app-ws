package ir.omidashouri.mobileappws.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TBL_USER",schema = "photo_app")
public class User extends BaseEntity {

    @Column(name= "USER_PUBLIC_ID",nullable = false,unique = true)
    private String userPublicId;

    @Column(name= "FIRST_NAME",length = 50)
    private String firstName;

    @Column(name = "LAST_NAME",length = 50)
    private String lastName;

    @Column(name = "EMAIL",length = 120)
    private String email;

    @Column(name = "ENCRYPTED_PASSWORD")
    private String encryptedPassword;

    @Column(name = "EMAIL_VERIFICATION_TOKEN")
    private String emailVerificationToken;

    @Column(name = "EMAIL_VERIFICATION_STATUS")
    private Boolean emailVerificationStatus = false;

    @EqualsAndHashCode.Exclude  //for solving recursive error
    @ToString.Exclude   //for solving recursive error
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)   //with cascade when save user the address information also saved
    private List<Address> addresses= new ArrayList<>();

}