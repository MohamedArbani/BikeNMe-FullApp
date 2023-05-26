package ma.ac.emi.ginfo.restfull.services;

import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.Location;
import ma.ac.emi.ginfo.restfull.entities.Rental;
import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.BikeRepository;
import ma.ac.emi.ginfo.restfull.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BikeRepository bikeRepository;

//    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Bike is not available for rent. Please check back later.")
//    @ExceptionHandler(RuntimeException.class)
//    public void handleRentalException(RuntimeException ex) {
//    }

    public boolean addRental(Bike bike, User user, LocalDateTime startTime, LocalDateTime endTime, Location destLocation) {
        if (bike.getAvailability()) {
            Rental rental = new Rental();
            rental.setBikeRentals(bike);
            rental.setUser(user);
            bike.setCurrent_user(user);
            rental.setDestLocation(destLocation);
            rental.setStartTime(startTime);
            rental.setEndTime(endTime);
            rentalRepository.save(rental);
            // mettre à jour le statut du vélo
            bike.setAvailability(false);
            bikeRepository.save(bike);
            return true;
        }
        System.out.println("can't rent this bike it's not available ");
        return false;
    }

    // Récuperer la liste des vélos louées par le user
    public List<Rental> getUserRentals(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    // Récuperer les vélos dispo à une location donnée
    public List<Bike> getAvailableBikes(String locationName) {
        updateBikeAvailability();
        return bikeRepository.findAvailableBikesByLocationName(locationName);
    }

    public Optional<Bike> getBike(Long id) {
        updateBikeAvailability();
        return bikeRepository.findById(id);
    }

    // changing the availability status of the bike
    public void updateBikeAvailability() {
        List<Rental> rentals = rentalRepository.findByEndTimeBefore(LocalDateTime.now());
        rentals.forEach(rental -> {
            Bike bike = rental.getBikeRentals();
            bike.setLocation(rental.getDestLocation());
            bike.setAvailability(true);
            bikeRepository.save(bike);
        });
    }

    public Optional<Rental> findById(Long rentalId) {
        return rentalRepository.findById(rentalId);
    }


    public void updateRental(Rental rental) {
        rentalRepository.save(rental);
    }
    // public

    public void addBikeToRental(Bike bike) {
        bikeRepository.save(bike);
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public List<Rental> getRentalList() {
        return rentalRepository.findAll();
    }
}
