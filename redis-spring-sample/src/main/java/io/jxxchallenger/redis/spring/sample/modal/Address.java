package io.jxxchallenger.redis.spring.sample.modal;

public class Address {

    String city;
    
    String country;

    public Address() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Address(String city, String country) {
        super();
        this.city = city;
        this.country = country;
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

    @Override
    public String toString() {
        return "Address [city=" + city + ", country=" + country + "]";
    }
}
