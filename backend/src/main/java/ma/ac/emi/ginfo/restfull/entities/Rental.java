package ma.ac.emi.ginfo.restfull.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike_id")
    private Bike bikeRentals;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Location destLocation ;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime  endTime;

    public Rental() {
    }

    public Rental(Bike bikeRentals, User user, LocalDateTime startTime, Location destLocation, LocalDateTime endTime) {
        this.bikeRentals = bikeRentals;
        this.user = user;
        this.startTime = startTime;
        this.destLocation = destLocation;
        this.endTime = endTime;
    }

    // Getters and setters ...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Bike getBikeRentals() {
        return bikeRentals;
    }

    public void setBikeRentals(Bike bikeRentals) {
        this.bikeRentals = bikeRentals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Location getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(Location destLocation) {
        this.destLocation = destLocation;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", bikeRentals=" + bikeRentals +
                ", user=" + user +
                ", startTime=" + startTime +
                ", destLocation=" + destLocation +
                ", endTime=" + endTime +
                '}';
    }
}
