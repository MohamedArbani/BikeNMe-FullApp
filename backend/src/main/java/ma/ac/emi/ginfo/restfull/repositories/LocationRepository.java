package ma.ac.emi.ginfo.restfull.repositories;

import ma.ac.emi.ginfo.restfull.entities.Location;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long>, CrudRepository<Location,Long> {

    List<Location> findByName(String name);

    List<Location> findByNameContainingIgnoreCaseAndNameIsNotNull(String name);

    @Query(nativeQuery = true,value = "SELECT * FROM Location l WHERE ST_Distance_Sphere(l.location, :point) <= :distance;")
    List<Location> findByLocationNear(@Param("point") Point point, @Param("distance") double distance);
}
