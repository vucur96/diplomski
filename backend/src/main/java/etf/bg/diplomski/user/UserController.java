package etf.bg.diplomski.user;

import etf.bg.diplomski.common.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static etf.bg.diplomski.common.ApplicationURL.*;

@RestController
@RequestMapping(USER_URL)
@CrossOrigin(FRONT_URL)
public class UserController {

  @Autowired private AppUserService appUserService;

  @Operation(description = "Login for admin.")
  @PostMapping(ADMIN_LOGIN)
  public ResponseEntity<AppUserDTO> adminLogin(@RequestBody LoginDTO data) {
    try {
      return new ResponseEntity<>(
          appUserService.adminLogin(data.username(), data.password()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
