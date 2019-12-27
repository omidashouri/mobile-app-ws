package ir.omidashouri.mobileappws.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TBL_PASSWORD_RESET_TOKEN",schema = "photo_app")
public class PasswordResetTokenEntity extends BaseEntity {

    private static final long serialVersionUID = 7070742163800372449L;

    private String token;

//    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;




}
