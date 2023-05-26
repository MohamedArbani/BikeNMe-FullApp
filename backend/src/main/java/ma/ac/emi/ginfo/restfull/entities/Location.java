package ma.ac.emi.ginfo.restfull.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String zip;
    private String city;
    private String name;
    private String country;

    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bike> bikes_location = new ArrayList<>();

    @OneToMany(mappedBy = "destLocation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rental> rentals = new ArrayList<>();


    // Constructor

    public Location() {
    }

    public Location(String name, String city, String country, double longitude, double latitude, String zip) {
        this.zip = zip;
        this.city = city;
        this.name = name;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public boolean removeBike(Bike bike){
        return bikes_location.remove(bike);
    }

    public boolean removeRental(Rental rental){
        return rentals.remove(rental);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", location= (" + longitude+","+latitude + ") "+
                '}';
    }

// getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<Bike> getBikes_location() {
        return bikes_location;
    }

    public void setBikes_location(List<Bike> bikes_location) {
        this.bikes_location = bikes_location;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}