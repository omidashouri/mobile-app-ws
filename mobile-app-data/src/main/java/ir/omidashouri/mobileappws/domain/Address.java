package ir.omidashouri.mobileappws.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_ADDRESS",schema = "photo_app")
public class Address extends BaseEntity{

    private static final long serialVersionUID = -2935476350760223385L;

    @Column(name= "ADDRESS_ID",length = 30,nullable = false,unique = true)
    private String addressId;

    @Column(name= "COUNTRY",length = 15)
    private String country;

    @Column(name= "CITY",length = 15)
    private String city;

    @Column(name= "STREET_NAME",length = 100)
    private String streetName;

    @Column(name= "POSTAL_CODE",length = 10)
    private String postalCode;

    @Column(name= "TYPE",length = 10)
    private String type;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;


}
