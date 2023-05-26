package ma.ac.emi.ginfo.restfull.repositories;

import ma.ac.emi.ginfo.restfull.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}