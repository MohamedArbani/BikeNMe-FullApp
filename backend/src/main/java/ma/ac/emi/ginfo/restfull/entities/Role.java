package ma.ac.emi.ginfo.restfull.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//package ma.ac.emi.ginfo.restfull.entities;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users = new ArrayList<>();
//
//    private String name;
//
//    public Role() {}
//
//    public Role(String name) {
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return name;
//    }
//
//    public void setFirstName(String name) {
//        this.name = name;
//    }
//}
public enum Role {
    ADMIN,USER;
    public Collection<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(name()));
        return authorities;
    }

}