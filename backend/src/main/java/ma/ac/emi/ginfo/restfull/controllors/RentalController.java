package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.Location;
import ma.ac.emi.ginfo.restfull.entities.Rental;
import ma.ac.emi.ginfo.restfull.repositories.BikeRepository;
import ma.ac.emi.ginfo.restfull.repositories.LocationRepository;
import ma.ac.emi.ginfo.restfull.services.BikeService;
import ma.ac.emi.ginfo.restfull.services.LocationService;
import ma.ac.emi.ginfo.restfull.services.RentalService;
import ma.ac.emi.ginfo.restfull.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
public class RentalController {
    @Autowired
    RentalService rentalService;

    @Autowired
    LocationService locationService;
    @Autowired
    UserService userService;

    @Autowired
    BikeService bikeService;

    @GetMapping("/rentals")
    public List<Rental> getRentalList(){
        return rentalService.getRentalList();
    }

    @GetMapping("/available/{name}")
    public List<Bike> getAvailableBike(@PathVariable String name) {
        return rentalService.getAvailableBikes(name);
    }
    @GetMapping("/availableb/{id}")
    public Bike getAvailableBike(@PathVariable Long id) {
        return bikeService.getBikeById(id);
    }

    @PostMapping("/availableb")
    public void addAvailableBike(@RequestBody Bike bike) {
        bikeService.addBike(bike);
    }
    @PostMapping("/rent")
    public ResponseEntity<?> rentBike(@RequestParam Long bikeId, @RequestParam Long userId,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime timeStart, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime  timeEnd,
                         @RequestParam String destLocation) {
        if(!rentalService.addRental(bikeService.getBikeById(bikeId), userService.getUserById(userId),timeStart, timeEnd,locationService.getLocaByName(destLocation).get(0)))
            return new ResponseEntity<>("Bike is not available for rent during this time. Please check back later.", HttpStatus.FORBIDDEN);
        return null;
    }

    @PutMapping("/rentals/{rentalId}/endTime")
    public void  updateRentalEndTime(@PathVariable Long rentalId,@RequestParam LocalDateTime timeEnd) {
        Rental rental = rentalService.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
        if (LocalDateTime .now().isBefore(rental.getEndTime())){
            rental.setEndTime(timeEnd);
            rentalService.updateRental(rental);
        }
        else {
            System.out.println(" location déja terminée impossible de modifier endTime") ;
        }
    }

    @PutMapping("/rentals/{rentalId}/destLocation")
    public void  updateRentalDestLocation(@PathVariable Long rentalId,@RequestParam String destLocation) {
        Rental rental = rentalService.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
        if (LocalDateTime .now().isBefore(rental.getEndTime())){
            rental.setDestLocation(locationService.getLocaByName(destLocation).get(0));
            rentalService.updateRental(rental);
        }
        else {
            System.out.println(" location déja terminée impossible de modifier la location ") ;
        }
    }

    @DeleteMapping("/rentals/{id}")
    public void delete(@PathVariable Long id){
        this.rentalService.deleteRental(id);
    }

//    @PostMapping("/rentals")
//    public void addBikeToRental( @RequestParam String image,@RequestParam String brand ,
//                                @RequestParam String model,@RequestParam String location)
//    {
//        bikeService.createBike(image,brand,model,locationService.getLocaByName(location),true);
//
//    }
}