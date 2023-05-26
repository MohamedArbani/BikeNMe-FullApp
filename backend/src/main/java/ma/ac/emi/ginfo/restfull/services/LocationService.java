package ma.ac.emi.ginfo.restfull.services;

import ma.ac.emi.ginfo.restfull.entities.Location;
import ma.ac.emi.ginfo.restfull.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    LocationRepository locaRepository ;


    public void createLoca(Location loca){
     locaRepository.save(loca) ;

    }

    public List<Location> getAllLocations() {
        return locaRepository.findAll();
    }

    public List<Location> getLocaByName(String name){
        return locaRepository.findByName(name);
    }

    public List<Location> searchLocalisationByName(String name) {
        return locaRepository.findByNameContainingIgnoreCaseAndNameIsNotNull(name);
    }


}
