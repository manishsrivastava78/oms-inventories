package com.tcs.eas.rest.apis.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tcs.eas.rest.apis.db.InventoryDaoService;
import com.tcs.eas.rest.apis.exception.InventoryNotFound;
import com.tcs.eas.rest.apis.exception.InventoryProductException;
import com.tcs.eas.rest.apis.log.LoggingService;
import com.tcs.eas.rest.apis.model.Inventory;
import com.tcs.eas.rest.apis.utility.Utility;

/**
 * 
 * @author 44745
 *
 */
@RestController
@RequestMapping("/apis/v1")
public class InventoryController {

	@Autowired
	LoggingService loggingService;
	/**
	 * 
	 */
	@Autowired
	InventoryDaoService inventoryDaoService;

	/**
	 * 
	 * @param inventory
	 * @param headers
	 * @return
	 */
	@PostMapping("/inventories")
	public ResponseEntity<Object> createInventory(@Valid @RequestBody Inventory inventory,
			@RequestHeader Map<String, String> headers) {
		Inventory savedInventory = null;
		try {
			savedInventory = inventoryDaoService.save(inventory);
		} catch (DataIntegrityViolationException e) {
			throw new InventoryProductException("Product already exists");
		}
		loggingService.writeProcessLog("POST", "inventory", "createInventory", inventory);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{inventoryid}")
				.buildAndExpand(savedInventory.getInventoryid()).toUri();
		return ResponseEntity.created(location).headers(Utility.getCustomResponseHeaders(headers)).build();
	}

	/**
	 * 
	 * @param headers
	 * @return
	 */
	@GetMapping("/inventories")
	public ResponseEntity<List<Inventory>> getInventory(@RequestHeader Map<String, String> headers) {
		List<Inventory> inventory = inventoryDaoService.findAll();
		loggingService.writeProcessLog("GET", "inventory", "getInventory", inventory);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers)).body(inventory);
	}

	/**
	 * 
	 * @param inventoryid
	 * @param headers
	 * @return
	 */
	@GetMapping("/inventories/{inventoryid}")
	public ResponseEntity<Inventory> getInventory(@PathVariable int inventoryid,@RequestHeader Map<String, String> headers) {
		Inventory inventory = inventoryDaoService.getInventoryById(inventoryid);
		if (inventory == null) {
			throw new InventoryNotFound("inventoryid " + inventoryid+" does not exist");
		}
		loggingService.writeProcessLog("GET", "inventories", "getInventory by id", inventory);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers)).body(inventory);
		//return inventory;
	}

	/**
	 * 
	 * @param inventoryid
	 * @param headers
	 * @return
	 */
	@PutMapping("/inventories/{inventoryid}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable int inventoryid,@RequestHeader Map<String, String> headers) {
		Inventory inventory = inventoryDaoService.getInventoryById(inventoryid);
		if (inventory == null) {
			throw new InventoryNotFound("inventoryid " + inventoryid+" does not exist");
		}
		loggingService.writeProcessLog("PUT", "inventories", "updateinventory by id", inventory);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers)).body(inventory);
	}

	/**
	 * 
	 * @param productId
	 * @param headers
	 * @return
	 */
	@GetMapping("/inventories/products/{productId}")
	public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable int productId,@RequestHeader Map<String, String> headers) {
		Inventory inventory = inventoryDaoService.findByProductid(productId);
		if (inventory == null) {
			throw new InventoryNotFound("Product " + productId+" does not exist");
		}
		loggingService.writeProcessLog("Get", "products", "getInventoryByProductId", inventory);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers)).body(inventory);
		//return inventory;
	}
}
