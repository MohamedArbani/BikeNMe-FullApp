package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.Rental;
import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.UserRepository;
import ma.ac.emi.ginfo.restfull.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService ;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("admin/first/{firstName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> searchUsersByFirstName(@PathVariable String firstName) {
        List<User> users = userService.searchUserByFirstName(firstName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("admin/last/{lastName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> searchUsersByLastName(@PathVariable String lastName) {
        List<User> users = userService.searchUserByLastName(lastName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("admin/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> searchUsersByEmail(@PathVariable String email) {
        List<User> users = userService.searchUserByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id ){
        return this.userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User owner){
        return userService.updateUser(id,owner);
    }

    @PatchMapping("/{id}/current_bikes")
    public User updateCurrentBikesUser(@PathVariable("id") Long id, @RequestBody User owner){
        return userService.updateCurrentBikesUser(id,owner);
    }

    @GetMapping("/{id}/bikes")
    public List<Bike> getOwnerBikes(@PathVariable("id") Long id){
        return userService.showAllOwnerBikes(id);
    }

    @GetMapping("/{id}/bikes/{bikeId}")
    public ResponseEntity<Bike> getOwnerBikes(@PathVariable("id") Long id, @PathVariable("bikeId") Long bikeId){
        Bike bike = userService.getBikeByUserId(id,bikeId);
        return (bike!=null)?ResponseEntity.ok().body(bike):ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }

    @RequestMapping(value = {"admin/first","admin/second","admin/email"})
    public List<User> redirectTo(){
        return this.getAllUsers();
    }

    @GetMapping("/{id}/rentals")
    public List<Rental> getRentals(@PathVariable Long id){
        return userService.getRentals(id);
    }

    @PatchMapping("/{id}/rentals")
    public ResponseEntity<?> updateRentals(@PathVariable Long id, @RequestBody Rental rental){
        return userService.updateRentalsByUserId(id,rental);
    }

    @DeleteMapping("/{id}/rentals")
    public ResponseEntity<?> deleteRentals(@PathVariable Long id){
        return userService.deleteRentals(id);
    }


    @DeleteMapping("/{id}/myBikes")
    public ResponseEntity<?> deleteMyBikes(@PathVariable Long id){
        return userService.deleteMyBikes(id);
    }

    @DeleteMapping("/{id}/myBikes/{bikeId}")
    public ResponseEntity<?> deleteMyBikeById(@PathVariable Long id,@PathVariable Long bikeId){
        return userService.deleteMyBikeById(id,bikeId);
    }


    @DeleteMapping("/{id}/currentBikes")
    public ResponseEntity<?> deleteCurrentBikes(@PathVariable Long id){
        return userService.deleteCurrentBikes(id);
    }

    @DeleteMapping("/{id}/currentBikes/{bikeId}")
    public ResponseEntity<?> deleteCurrentBikeById(@PathVariable Long id,@PathVariable Long bikeId){
        return userService.deleteCurrentBikeById(id,bikeId);
    }


}

class AuthResponse {

    private String message;
    private String token;
    private int expiresIn;

    public AuthResponse(String message) {
        this.message = message;
    }

    public AuthResponse(String token, int expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    // Getters and setters omitted for brevity


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}

