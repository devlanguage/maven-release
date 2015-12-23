package org.basic.xml.dom.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

class BasketBall extends Hobby {
  private String diameter = "33 CM";
  private String weight = "0.444 KG";

  public String getDiameter() {
    return diameter;
  }

  public void setDiameter(String diameter) {
    this.diameter = diameter;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }
}

class Movie extends Hobby {
  private String length = "2.4 Hour";
  private String director = "Jia Zhangke";

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }
}

@XmlSeeAlso(value={Movie.class, BasketBall.class})
public class Hobby {

}
