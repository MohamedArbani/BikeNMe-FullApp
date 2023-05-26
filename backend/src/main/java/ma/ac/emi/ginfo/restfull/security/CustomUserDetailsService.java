package ma.ac.emi.ginfo.restfull.security;

import ma.ac.emi.ginfo.restfull.entities.Role;
import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

//
//import ma.ac.emi.ginfo.restfull.entities.User;
//import ma.ac.emi.ginfo.restfull.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    UserService us;
//
//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        // Load the user details from the data store based on the name
//        UserDetails user = this.us.getUserByName(name);
//
//        //UserDetails userDetails = user;
//        // Create a UserDetails object and return it
//        UserDetails userDetails = (UserDetails) new User(user.getFirstName(), user.getEmail(), user.getPassword());
//        return userDetails;
//    }
//
////    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
////        // Define the user's authorities
////    }
//}
//
//import ma.ac.emi.ginfo.restfull.entities.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class CustomUserDetailsService implements UserDetails {
//
//    private User user;
//
//    public CustomUserDetailsService(User user) {
//        this.user = user;
//    }
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getFirstName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.isEnabled();
//    }
//}
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole().name()));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}