package ma.ac.emi.ginfo.restfull.services;

import ma.ac.emi.ginfo.restfull.controllors.AuthController;
import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.Rental;
import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository ;

    @Autowired
    BikeService bikeService ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailService mailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(()->new IllegalStateException("User not found"));
        //return userRepository.getById(id);
    }

    public User findUserByEmailAndPass(String email , String password){
        return userRepository.findByEmailAndPassword(email,password) ;
    }
    public User findUserByName(String name){return userRepository.findUserByFirstName(name).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + name));}

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        mailService.sendVerificationMail(user.getEmail(),"You are Successfully Register","Bike Near Me Verification",user.getFirstName());
        return this.userRepository.save(user);
    }

    public User getUserByName(String name) {
        return userRepository.findUserByFirstName(name).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + name));
    }

    public List<User> searchUserByFirstName(String firstName){
        return this.userRepository.findByFirstNameContainingIgnoreCase(firstName);
    }
    public List<User> searchUserByLastName(String lastName){
       return this.userRepository.findByLastNameContainingIgnoreCase(lastName);
    }
    public List<User> searchUserByEmail(String email){
       return this.userRepository.findByEmailContainingIgnoreCase(email);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User owner){
        owner.setId(id);
        return userRepository.saveAndFlush(owner);
    }

    public Bike getBikeByUserId(Long userId,Long bikeId){
        User user = getUserById(userId);
        for (Bike bike:user.getMyBikes()) {
            if(bike.getId().equals(bikeId))
                return bike;
        }
        return null;
    }

    public User updateCurrentBikesUser(Long id, User owner){
        User user = getUserById(id);
        user.setCurrent_bikes(owner.getCurrent_bikes());
        return userRepository.saveAndFlush(user);
    }

    public List<Bike> showAllOwnerBikes(Long id){
        User owner = getUserById(id);
        return owner.getMyBikes();
    }

    public List<Rental> getRentals(Long id) {
        return getUserById(id).getMyBookings();
    }

    public ResponseEntity<Map<String, String>> updateRentalsByUserId(Long id, Rental rental) {
        User user = getUserById(id);
        List<Rental> rentalList = user.getMyBookings();
        rental.setBikeRentals(bikeService.getBikeById(rental.getBikeRentals().getId()));
        rental.setUser(user);
        rentalList.add(rental);
        user.setMyBookings(rentalList);
        userRepository.saveAndFlush(user);
        Map<String,String> message = new HashMap<>();
        message.put("message","Rental updated");
        return ResponseEntity.ok().body(message);
    }

    public ResponseEntity<Map<String, String>> deleteRentals(Long id){
        User user = getUserById(id);
        user.getMyBookings().forEach(
            rental -> {
                rental.setUser(null);
            }
        );
        user.setMyBookings(null);
        userRepository.saveAndFlush(user);
        Map<String,String> message = new HashMap<>();
        message.put("message","Rentals deleted");
        return ResponseEntity.ok().body(message);
    }

    static class TokenResponse {
        private String token;
        private int expiresIn;

        TokenResponse(String token, int expiresIn) {
            this.token = token;
            this.expiresIn = expiresIn;
        }

        public String getToken() {
            return token;
        }

        public int getExpiresIn() {
            return expiresIn;
        }
    }

    public ResponseEntity<?> authenticate(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());

        if(foundUser == null) {
            return ResponseEntity.badRequest().body("Email or password is incorrect");
        }

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.badRequest().body("Email or password is incorrect");
        }

        String token = jwtTokenProvider.generateToken(foundUser);
        return ResponseEntity.ok(new TokenResponse(token, jwtTokenProvider.getJwtExpirationTimeInMillis()));
    }

    public ResponseEntity<?> deleteMyBikes(Long id) {
        User user = getUserById(id);
        user.getMyBikes().forEach(
            bike -> {
                user.removeMyBike(bike);
                bikeService.deleteBikeById(bike.getId());
            }
        );
        userRepository.saveAndFlush(user);
        Map<String,String> message = new HashMap<>();
        message.put("message","My Own Bikes deleted");
        return ResponseEntity.ok().body(message);
    }

    public ResponseEntity<?> deleteMyBikeById(Long id, Long bikeId) {
        User user = getUserById(id);
        Bike myBike = bikeService.getBikeById(bikeId);
        user.removeMyBike(myBike);
        bikeService.deleteBikeById(bikeId);
        userRepository.saveAndFlush(user);
        Map<String,String> message = new HashMap<>();
        message.put("message","My Bike " + myBike.getName() + " deleted");
        return ResponseEntity.ok().body(message);
    }

    public ResponseEntity<?> deleteCurrentBikes(Long id) {
        User user = getUserById(id);
        user.getCurrent_bikes().forEach(
                bike -> {
                    user.removeMyBike(bike);
                    bikeService.deleteBikeById(bike.getId());
                }
        );
        userRepository.saveAndFlush(user);
        Map<String,String> message = new HashMap<>();
        message.put("message","All Current Bikes deleted");
        return ResponseEntity.ok().body(message);
    }

    public ResponseEntity<?> deleteCurrentBikeById(Long id, Long bikeId) {
        User user = getUserById(id);
        Bike currentBike = bikeService.getBikeById(bikeId);
        user.removeMyBike(currentBike);
        bikeService.deleteBikeById(bikeId);
        userRepository.saveAndFlush(user);
        Map<String,String> message = new HashMap<>();
        message.put("message","My current Bike " + currentBike.getName() + " deleted");
        return ResponseEntity.ok().body(message);
    }
}
