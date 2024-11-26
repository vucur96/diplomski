package etf.bg.diplomski.user;

import etf.bg.diplomski.common.BaseClass;
import etf.bg.diplomski.common.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class AppUser extends BaseClass {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String address;

    @Column
    private String phone;

    @Column(unique = true)
    private String email;

    @Column
    private String gender;

    @Column
    private String imageURL;

    @Column
    private UserStatus status;

    public AppUser(
            String username,
            String password,
            String firstName,
            String lastName,
            String address,
            String phone,
            String mail,
            String gender,
            String imageURL,
            UserStatus status) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = mail;
        this.gender=gender;
        this.imageURL = imageURL;
        this.status = status;
    }

    public AppUser(String username,String password){
        this.username=username;
        this.password=password;
    }

    @Override
    public String toString() {
        return firstName + lastName;
    }
}
