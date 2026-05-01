package com.devsecops;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NumericController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String baseURL = "http://node-service:5000/plusone";
	//private static final String baseURL = "http://localhost:5001/plusone";
	
	RestTemplate restTemplate = new RestTemplate();
	
	@RestController
	public class compare{

		@GetMapping("/")
		public String welcome() {
			return "Kubernetes DevSecOps";
		}

		@GetMapping("/compare/{value}")
		public String compareToFifty(@PathVariable int value) {
			String message = "Could not determine comparison";
			if (value > 50) {
				message = "Greater than 50";
			} else {
				message = "Smaller than or equal to 50";
			}
			return message;
		}

		@GetMapping("/increment/{value}")
		public int increment(@PathVariable int value) {
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(baseURL + '/' + value, String.class);
			String response = responseEntity.getBody();
			logger.info("Value Received in Request - " + value);
			logger.info("Node Service Response - " + response);
			return Integer.parseInt(response);
		}

		// trying to fix the functionality
		// @GetMapping("/increment/{value}")
		// public int increment(@PathVariable int value) {
		// 	// 1. Log the intent
		// 	logger.info("Incrementing value: {}", value);

		// 	// 2. Use a Map to catch JSON fields (e.g., {"result": 6})
		// 	try {
		// 		ResponseEntity<Map> responseEntity = restTemplate.getForEntity(baseURL + "/" + value, Map.class);
				
		// 		if (responseEntity.getBody() != null) {
		// 			Object result = responseEntity.getBody().get("result"); // Change "result" to whatever your Node app sends
		// 			return Integer.parseInt(result.toString());
		// 		}
		// 	} catch (Exception e) {
		// 		logger.error("Failed to parse Node Service response", e);
		// 	}
			
		// 	return -1; // Or throw a custom Exception
		// }
	}

}