package org.third.testdata.user.domain;

public class AddressDM {

    private int id;
    private String country;
    private String city;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressDM() {
        this.id = 0;
        this.country = "China";
        this.city = "Shanghai";
    }

    @Override
    public String toString() {

        return "[Address:[country=" + this.country + ",city=" + this.city + ",description=" + this.description + "]]";
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
