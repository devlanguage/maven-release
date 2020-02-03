package org.basic.db.domain;

@javax.persistence.Table(name = "dm_product")
@javax.persistence.Entity
public class ProductDM extends AbstractNamedObject {

	private static final long serialVersionUID = -3855616824834403782L;
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}