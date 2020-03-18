package com.baeldung.algorithms.ga.jenetics;

import static java.util.Objects.requireNonNull;

import io.jenetics.util.ISeq;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
public class SpringsteenRecord {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ISeq<String> getSongs() {
        return songs;
    }

    public void setSongs(ISeq<String> songs) {
        this.songs = songs;
    }

    String name;
    double price;
    ISeq<String> songs;

    public SpringsteenRecord(String name, double price, ISeq<String> songs) {
        this.name = requireNonNull(name);
        this.price = price;
        this.songs = requireNonNull(songs);
    }

}
