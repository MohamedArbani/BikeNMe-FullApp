package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.UserRepository;
import ma.ac.emi.ginfo.restfull.services.JwtTokenProvider;
import ma.ac.emi.ginfo.restfull.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/signup")
    public User addUser(@RequestBody User user){
        return this.userService.addUser(user);
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody User user) {
        return userService.authenticate(user);
    }
}
