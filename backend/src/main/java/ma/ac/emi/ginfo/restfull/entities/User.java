package ma.ac.emi.ginfo.restfull.entities;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "current_user",cascade = CascadeType.ALL)
    private List<Bike> current_bikes = new ArrayList<>();

    private double rating = 0;

    @OneToMany(mappedBy = "bike_owner",cascade = CascadeType.ALL)
    private List<Bike> myBikes = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "destinataire",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> ownerNotifications = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Rental> myBookings = new ArrayList<>();

    public boolean add(Bike bike) {
        bike.setBike_owner(this);
        return myBikes.add(bike);
    }

    public boolean removeMyBike(Bike bike) {
        return myBikes.remove(bike);
    }

    public boolean removeCurrentBike(Bike bike) {
        return current_bikes.remove(bike);
    }

    public boolean removeBooking(Rental rental){
        return myBookings.remove(rental);
    }

    public boolean removeNotification(Notification notification){
        return notifications.remove(notification);
    }

    public boolean removeOwnerNotification(Notification notification){
        return ownerNotifications.remove(notification);
    }

    // Constructer

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public User(String firstName, String email, String password, Collection<? extends GrantedAuthority> authorities) {

    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    // Getters and setters ...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Bike> getMyBikes() {
        return myBikes;
    }

    public void setMyBikes(List<Bike> myBikes) {
        this.myBikes = myBikes;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Bike> getCurrent_bikes() {
        return current_bikes;
    }

    public void setCurrent_bikes(List<Bike> current_bikes) {
        this.current_bikes = current_bikes;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getOwnerNotifications() {
        return ownerNotifications;
    }

    public void setOwnerNotifications(List<Notification> ownerNotifications) {
        this.ownerNotifications = ownerNotifications;
    }

    public List<Rental> getMyBookings() {
        return myBookings;
    }

    public void setMyBookings(List<Rental> myBookings) {
        this.myBookings = myBookings;
    }
}


