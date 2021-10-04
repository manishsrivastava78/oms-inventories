package com.tcs.eas.rest.apis.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @author 44745
 *
 */
@ApiModel
@Entity(name = "PurchaseOrder")
public class PurchaseOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6820844333408498994L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderid;

	@Column(unique = true)
	@NotNull(message = "productid field is missing")
	private int productid;

	@NotNull(message = "customerid field is missing")
	private int customerid;

	@NotNull(message="dop field is missing")
	private Date dop;
	
	
	
	/**
	 * @return the dop
	 */
	public Date getDop() {
		return dop;
	}

	/**
	 * @param dop the dop to set
	 */
	public void setDop(Date dop) {
		this.dop = new Date(System.currentTimeMillis());
	}

	public PurchaseOrder() {

	}

	public PurchaseOrder(@NotNull(message = "productid field is missing") int productid,
			@NotNull(message = "customerid field is missing") int customerid) {
		super();
		this.productid = productid;
		this.customerid = customerid;
	}

	/**
	 * @return the orderid
	 */
	public int getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	/**
	 * @return the productid
	 */
	public int getProductid() {
		return productid;
	}

	/**
	 * @param productid the productid to set
	 */
	public void setProductid(int productid) {
		this.productid = productid;
	}

	/**
	 * @return the customerid
	 */
	public int getCustomerid() {
		return customerid;
	}

	/**
	 * @param customerid the customerid to set
	 */
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	
	
}
