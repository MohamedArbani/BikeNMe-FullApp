package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.Location;
import ma.ac.emi.ginfo.restfull.services.BikeService;
import ma.ac.emi.ginfo.restfull.services.LocationService;
import ma.ac.emi.ginfo.restfull.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping(value = {"/bikes"})
public class BikeController {
    @Autowired
    BikeService bikeService;

    @Autowired
    UserService userService;

    @Autowired
    LocationService locationService;

    /* Get Mapping */
    @GetMapping("unknown")
    public Collection<Bike> showAllBikes() {
        return bikeService.showAllBikes();
    }

    @GetMapping("unknown/{id}")
    public Bike getBikeById(@PathVariable("id") Long id){
        return bikeService.getBikeById(id);
    }

    @GetMapping("unknown/name/{name}")
    public Bike getBikeById(@PathVariable("name") String name){
        return bikeService.getBikeByName(name);
    }

    @GetMapping("unknown/category/{category}")
    public Bike getBikeByCategory(@PathVariable("category") String category){
        return bikeService.getBikeByCategory(category);
    }

//    @GetMapping("unknown/location/{location}")
//    public Collection<Bike> getBikeByLocalisation(@PathVariable("location") Location location){
//        return bikeService.getBikeByLocalisation(location);
//    }

    @GetMapping("unknown/location/{name}")
    public List<Bike> getAvailableBike(@PathVariable String name) {
        return bikeService.getAvailableBikes(name);
    }

    @GetMapping("unknown/searchByLocation")
    public List<Bike> getBikesByLocationAndDistance(@RequestParam("longitude") double longitude,
                                                    @RequestParam("latitude") double latitude,
                                                    @RequestParam("distance") double distance) {
        return bikeService.getAllBikesByNearLocation(new Point(longitude, latitude), distance);
    }

    @GetMapping("/owners/{ownerId}/searchBikes/{searchTerm}")
    public List<Bike> searchUserBikes(@PathVariable Long ownerId, @PathVariable String searchTerm){
        return bikeService.searchUserBikes(ownerId, searchTerm);
    }

    /* Post Mapping */
    @PostMapping
    public Bike addBike(@RequestBody Bike newBike){
        this.locationService.createLoca(newBike.getLocation());
        System.out.println(newBike);
        newBike.setBike_owner(userService.getUserById(newBike.getBike_owner_id()));
        return bikeService.addBike(newBike);
    }

    /* Update Mapping */
    @PatchMapping("/{id}")
    public Bike updateBike(@PathVariable("id") Long id, @RequestBody Bike bike){
        return bikeService.updateBike(id,bike);
    }

    @PatchMapping("/reserve/{id}")
    public Bike reserveBike(@PathVariable("id") Long id, @RequestParam Long userId){
        return bikeService.reserveBike(id,userService.getUserById(userId));
    }

    /* Delete Mapping */
    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable("id") Long id){
        bikeService.deleteBikeById(id);
    }

}
