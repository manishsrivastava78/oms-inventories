package com.tcs.eas.rest.apis.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.eas.rest.apis.model.Inventory;

/**
 * 
 * @author 44745
 *
 */
@Component
public class InventoryDaoService {

	@Autowired
	InventoryRepository inventoryRepository;

	

	/**
	 * 
	 * @param book
	 * @return
	 */
	public Inventory save(Inventory inventory) {
		inventoryRepository.save(inventory);
		return inventory;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Inventory> findAll(){
		return inventoryRepository.findAll();
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Inventory getInventoryById(Integer id) {
		
		Optional<Inventory> optional = inventoryRepository.findById(id);
		if(optional.isPresent())
			return  optional.get();
		else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param productid
	 * @return
	 */
	public Inventory findByProductid(int productid) {
		return inventoryRepository.findByProductid(productid);
	}
}
