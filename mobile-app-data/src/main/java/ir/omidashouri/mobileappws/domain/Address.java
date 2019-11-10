package ir.omidashouri.mobileappws.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_ADDRESS",schema = "photo_app")
public class Address extends BaseEntity{

    @Column(name= "ADDRESS_ID",nullable = false,unique = true)
    private String addressId;

    @Column(name= "COUNTRY",length = 50)
    private String country;

    @Column(name= "CITY",length = 50)
    private String city;

    @Column(name= "STREET_NAME")
    private String streetName;

    @Column(name= "POSTAL_CODE")
    private String postalCode;

    @Column(name= "TYPE")
    private String type;

    @Column(name= "USER_ID")
    private User user;


}
