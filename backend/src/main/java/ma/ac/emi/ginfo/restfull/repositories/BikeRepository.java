package ma.ac.emi.ginfo.restfull.repositories;

import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.Location;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    List<Bike> findAvailableBikesByLocationName(String locationName);

    Bike findBikeByBrand(String name);

    Bike findBikeByCategory(String category);

    Collection<Bike> findBikeByLocation(Location localisation);

    //List<Bike> findAllByNameContainingOrBrandContainingOrCategoryContainingOrModelContainingOrSubCategory(String value);

    @Query("SELECT b FROM Bike b WHERE (b.bike_owner.id = :userId) AND (lower(b.name) LIKE lower(concat('%', :searchTerm, '%'))"
            + " OR lower(b.brand) LIKE lower(concat('%', :searchTerm, '%'))"
            + " OR lower(b.model) LIKE lower(concat('%', :searchTerm, '%'))"
            + " OR lower(b.category) LIKE lower(concat('%', :searchTerm, '%'))"
            + " OR lower(b.subCategory) LIKE lower(concat('%', :searchTerm, '%')))")
    List<Bike> searchBikesForUser(@Param("searchTerm") String searchTerm, @Param("userId") Long userId);

    @Query(value = "SELECT b.* FROM Bike b JOIN Location l WHERE ST_Distance_Sphere(l.point, :point) <= :distance", nativeQuery = true)
    List<Bike> findByLocationNear(@Param("point") Point point, @Param("distance") double distance);
}