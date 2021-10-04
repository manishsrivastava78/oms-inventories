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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tcs.eas.rest.apis.exception.OrderNotFound;
import com.tcs.eas.rest.apis.log.LoggingService;
import com.tcs.eas.rest.apis.model.Inventory;
import com.tcs.eas.rest.apis.model.PurchaseOrder;
import com.tcs.eas.rest.apis.utility.Utility;

/**
 * 
 * @author 44745
 *
 */
@RestController
@RequestMapping("/apis/v1/orders")
public class OrderController {

	@Autowired
	LoggingService loggingService;
	/**
	 * 
	 */


	/**
	 * 
	 * @param PurchaseOrder
	 * @param headers
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Object> createOrder(@Valid @RequestBody PurchaseOrder order,
			@RequestHeader Map<String, String> headers) {
		//get inventory for product
		//reserve item in inventory by setting available=available-1;
		//if minimum <=available
		//	{save order in db
		//	create message and put in shipping topic}
		//else{
			//release item
		    //throw inventorynotavailable
			//}
		//
		//
		if(isInventory(order.getProductid())) {
			System.out.println("save order");
			String orderData = "{\r\n" + 
					"	\"orderid\": 123,\r\n" + 
					"	\"customerid\": 223,\r\n" + 
					"	\"productid\": 22,\r\n" + 
					"	\"dop\": \"2021-09-29\"\r\n" + 
					"}";
		}
		//Order savedOrder = null;
			//check inventory first
			//if(available>min)
			//then create order in db
			//crate message for shipment process and put into topic
			//else throw error inventory not availabe for the product 
		loggingService.writeProcessLog("POST", "Order", "createOrder", order);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{Orderid}")
				.buildAndExpand(1).toUri();
		return ResponseEntity.created(location).headers(Utility.getCustomResponseHeaders(headers)).build();
	}

	/**
	 * 
	 * @param headers
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<PurchaseOrder>> getOrder(@RequestHeader Map<String, String> headers) {
		List<PurchaseOrder> Order = null;//OrderDaoService.findAll();
		loggingService.writeProcessLog("GET", "Order", "getOrder", Order);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers)).body(Order);
	}

	/**
	 * 
	 * @param Orderid
	 * @param headers
	 * @return
	 */
	@GetMapping("/{orderid}")
	public ResponseEntity<PurchaseOrder> getOrder(@PathVariable int Orderid,@RequestHeader Map<String, String> headers) {
		PurchaseOrder Order = null;//OrderDaoService.getOrderById(Orderid);
		if (Order == null) {
			throw new OrderNotFound("Orderid " + Orderid+" does not exist");
		}
		loggingService.writeProcessLog("GET", "inventories", "getOrder by id", Order);
		return ResponseEntity.ok().headers(Utility.getCustomResponseHeaders(headers)).body(Order);
		//return Order;
	}
	
	private boolean isInventory(int productId) {
		boolean found = false;
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/apis/v1/inventories/products/"+ productId;
		try {
			ResponseEntity<Inventory> response = restTemplate.getForEntity(url, Inventory.class);
			Inventory inventory = response.getBody();
			inventory.setAvailablequantity(inventory.getAvailablequantity()-1);
			updateInventory(inventory);
			//reserve quantity
			//inventory.setAvailablequantity(inventory.getAvailablequantity()-1);
			//url = "http://localhost:8080/apis/v1/inventories/"+inventory.getInventoryid();
			//restTemplate.put(url,inventory);
			if (inventory.getAvailablequantity() >= inventory.getMinquantity()) {
				found = true;
			}else {
				found = false;
				inventory.setAvailablequantity(inventory.getAvailablequantity()+1);
				updateInventory(inventory);
				//url = "http://localhost:8080/apis/v1/inventories/"+inventory.getInventoryid();
				//restTemplate.put(url,inventory);
			}
		} catch (Exception e) {
			loggingService.logError(e.getMessage());
		}
		return found;
	}
	
	/**
	 * 
	 * @param inventory
	 */
	private void updateInventory(Inventory inventory) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/apis/v1/inventories/"+inventory.getInventoryid();
		restTemplate.put(url,inventory);
	}
	
}
