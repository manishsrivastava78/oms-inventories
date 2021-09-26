package com.tcs.eas.rest.apis.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.eas.rest.apis.model.Inventory;


/**
 * 
 * @author 44745
 *
 */
public interface InventoryRepository extends JpaRepository<Inventory,Integer>{
	/**
	 * 
	 * @param productid
	 * @return
	 */
	public Inventory findByProductid(int productid);
}
