package ma.ac.emi.ginfo.restfull.repositories;

import ma.ac.emi.ginfo.restfull.entities.Bike;
import ma.ac.emi.ginfo.restfull.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, CrudRepository<User,Long> {
    User getById(long id);
    Optional<User> findUserByFirstName(String firstName);
    User findByEmailAndPassword(String email , String password) ;
    //List<User> findByFirstNameContainingOrSecondNameIgnoreCase(String firstName,String secondName);
    //List<User> findByFirstNameContainingOrSecondNameContainingIgnoreCase(String firstName,String secondName);
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    List<User> findByLastNameContainingIgnoreCase(String secondName);
    List<User> findByEmailContainingIgnoreCase(String email);

    User findByEmail(String email);

//    @Query(nativeQuery = true, value = "SELECT B.* FROM user U, bike B WHERE U.id = B.owner_id AND (B.name LIKE )")
//    List<Bike> searchBikesUser(String searchString);


}
