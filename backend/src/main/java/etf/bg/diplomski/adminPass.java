import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class adminPass {

    public static void main(String[] args){
        String password="Admin@123";
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPass=passwordEncoder.encode(password);
        System.out.println(encodedPass);
    }
}

// Dobijeni kriptovani password:$2a$10$RkVNG7UttF2egqEQWHNLNeZCK5zy01fcLdz/GVwL1W53CfeQIE6ZW
