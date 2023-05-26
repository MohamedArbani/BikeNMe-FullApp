package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.Location;
import ma.ac.emi.ginfo.restfull.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("*")
@RequestMapping(value = {"/locations"})
public class LocationController {
    @Autowired
    LocationService locationService;

    @RequestMapping(value = {"", "/search"})
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }
    @GetMapping("/{name}")
    public List<Location> getLocalisation(@PathVariable String name) {
        return locationService.getLocaByName(name);
    }

    @GetMapping("/search/{name}")
    public List<Location> searchLocalisationByName(@PathVariable String name) {
        return locationService.searchLocalisationByName(name);
    }

}
