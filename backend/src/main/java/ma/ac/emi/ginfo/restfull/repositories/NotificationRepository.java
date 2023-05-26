package ma.ac.emi.ginfo.restfull.repositories;

import ma.ac.emi.ginfo.restfull.entities.Notification;
import ma.ac.emi.ginfo.restfull.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long>{
    List<Notification> findByNotOppend(boolean b);
    List<Notification> findByDestinataire(User d);
    List<Notification> findByUser(User d);
    List<Notification> findByNotOppendAndDestinataire(boolean b, Optional<User> destinataire);

    List<Notification> searchByTypeIgnoreCase(String type);
}
