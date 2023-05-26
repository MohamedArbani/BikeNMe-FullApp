package ma.ac.emi.ginfo.restfull.repositories;

import com.sun.istack.NotNull;
import ma.ac.emi.ginfo.restfull.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long > {
    List<Rental> findByUserId(Long userId);
    List<Rental> findByEndTimeBefore(LocalDateTime endDate);

    @NotNull
    Optional<Rental> findById(Long id ) ;
}
