package ir.omidashouri.mobileappws.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TBL_ADDRESS",schema = "photo_app")
public class Address extends BaseEntity {

    private static final long serialVersionUID = -2935476350760223385L;

/*    @Id
    @GeneratedValue
    private Long id;*/

    @Column(name= "ADDRESS_PUBLIC_ID",length = 30,nullable = false)
    private String addressPublicId;

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

    @EqualsAndHashCode.Exclude  //for solving recursive error
    @ToString.Exclude   //for solving recursive error
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


}
