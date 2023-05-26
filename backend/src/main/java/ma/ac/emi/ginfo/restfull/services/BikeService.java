package ma.ac.emi.ginfo.restfull.services;

import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.Location;
import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.BikeRepository;
import ma.ac.emi.ginfo.restfull.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BikeService {
    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    PicturesStorageService picturesStorageService;

    public Optional<Bike> getAvailableBike(Long id) {
        return bikeRepository.findById(id);
    }

    // Récuperer les vélos dispo à une location donnée
    public List<Bike> getAvailableBikes(String locationName) {
        return bikeRepository.findAvailableBikesByLocationName(locationName);
    }

    public Bike addBike(Bike newBike){
        newBike.getPictures().forEach(
                picture -> picture.setBike(newBike)
        );
        return bikeRepository.save(newBike);
    }

    public List<Bike> addAllBikes(List<Bike> newBikes){
        newBikes.forEach(this::addBike);
        return newBikes;
    }

    public List<Bike> showAllBikes(){
        return bikeRepository.findAll();
    }
    public Location addLocation(Location loc){
        return locationRepository.save(loc);
    }

    public List<Bike> getAllBikesByNearLocation(Point loc,double distance){
        return bikeRepository.findByLocationNear(loc,distance);
    }

    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Could not find bike"));
    }

    public Bike getBikeByName(String name) {
        return bikeRepository.findBikeByBrand(name);
    }
    public Bike getBikeByCategory(String category) {
        return bikeRepository.findBikeByCategory(category);
    }
    public Collection<Bike> getBikeByLocalisation(Location localisation) {
        return bikeRepository.findBikeByLocation(localisation);
    }

    public List<Bike> searchUserBikes(Long userId,String searchTerm){
        return bikeRepository.searchBikesForUser(searchTerm,userId);
    }

    public void deleteBikeById(Long id){
        Bike bike = getBikeById(id);
        String dir = (bike.getName() + "-" + bike.getBrand() + "-" + bike.getModel()).replaceAll("[\\s\\\\/]","_");
        picturesStorageService.deleteBikeFolder(dir);
        bikeRepository.delete(bike);
    }

    public Bike updateBike(Long id, Bike bike){
        bike.setId(id);
        return bikeRepository.saveAndFlush(bike);
    }

    public Bike reserveBike(Long id, User user) {
        Bike bike = getBikeById(id);
        bike.setAvailability(false);
        bike.setCurrent_user(user);
        return bikeRepository.saveAndFlush(bike);
    }
}
