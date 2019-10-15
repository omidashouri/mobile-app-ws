package ir.omidashouri.mobileappws.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_USER",schema = "photo_app")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}