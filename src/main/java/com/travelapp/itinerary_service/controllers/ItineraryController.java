package com.travelapp.itinerary_service.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travelapp.itinerary_service.dtos.ItineraryDto;
import com.travelapp.itinerary_service.dtos.StayPriceUpdateDto;
import com.travelapp.itinerary_service.dtos.VehiclePriceUpdateDto;
import com.travelapp.itinerary_service.entities.Itinerary;
import com.travelapp.itinerary_service.entities.StayInfo;
import com.travelapp.itinerary_service.entities.Transfer;
import com.travelapp.itinerary_service.entities.VehicleInfo;
import com.travelapp.itinerary_service.producers.ProducerService;
import com.travelapp.itinerary_service.services.ItineraryService;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {
	@Autowired
	ItineraryService itineraryService;
	@Autowired
	ProducerService producerService;

	@PostMapping
	public ResponseEntity<Itinerary> createItinerary(@RequestBody Itinerary itinerary) {
		Itinerary createdItinerary = itineraryService.createItinerary(itinerary);
		return ResponseEntity.ok(createdItinerary);
	}

	@GetMapping
	public ResponseEntity<ItineraryDto> getItineraryById(@RequestParam String id) {
		ItineraryDto itineraryDto = itineraryService.getItineraryById(id);
		return ResponseEntity.ok(itineraryDto);
	}

	@PatchMapping
	public ResponseEntity<String> deactivateItineraryById(@RequestParam String id) {
		itineraryService.deactivateItineraryById(id);
		return ResponseEntity.ok().body("Itinerary deactivated successfully");
	}

	@GetMapping("/search")
	public ResponseEntity<List<Itinerary>> fetchItinerariesByLocation(@RequestParam String location,
			@RequestParam(defaultValue = "false") boolean isVehicleIncluded,
			@RequestParam(defaultValue = "false") boolean isStayIncluded) {
		List<Itinerary> itineraries = itineraryService.fetchItinerariesBySearchCriteria(location, isVehicleIncluded,
				isStayIncluded);
		return ResponseEntity.ok(itineraries);
	}

	@GetMapping("/search/destinationType")
	public ResponseEntity<List<Itinerary>> fetchItinerariesByDestinationType(@RequestParam String destinationType) {
		List<Itinerary> itineraries = itineraryService.fetchItinerariesByDestinationType(destinationType);
		return ResponseEntity.ok(itineraries);
	}

	@GetMapping("/vehicleid")
	public ResponseEntity<List<Itinerary>> fetchItinerariesByVehicle(@RequestParam String vehicleId) {
		List<Itinerary> itineraries = itineraryService.fetchItinerariesByVehicle(vehicleId);
		return ResponseEntity.ok(itineraries);
	}

	@GetMapping("/stayinfo")
	public ResponseEntity<List<Itinerary>> findItinerariesByStayIdAndRoomId(@RequestParam String roomId,
			@RequestParam String stayId) {
		List<Itinerary> itineraries = itineraryService.findItinerariesByStayIdAndRoomId(roomId, stayId);
		return ResponseEntity.ok(itineraries);
	}

	@PostMapping("/vehicleprice/update")
	public ResponseEntity<String> updateVehiclePricesForItineraries(
			@RequestBody VehiclePriceUpdateDto vehiclePriceUpdateDto) throws JsonProcessingException {
		producerService.publishVehiclPriceUpdate(vehiclePriceUpdateDto);
		return ResponseEntity.ok("vehicle price updated successfully");
	}

	@PostMapping("/stayprice/update")
	public ResponseEntity<String> updateStayPriceForItineraries(@RequestBody StayPriceUpdateDto stayPriceUpdateDto)
			throws JsonProcessingException {
		producerService.publishStayPriceUpdate(stayPriceUpdateDto);
		return ResponseEntity.ok("stay price updated successfully");
	}

	@PostMapping("/update")
	public ResponseEntity<Itinerary> updateItinerary(@RequestParam String id, Map<String, Object> updatedFields) {
		Itinerary itinerary = itineraryService.updateItinerary(id, updatedFields);
		return ResponseEntity.ok(itinerary);
	}

	@PostMapping("/customize/stay")
	public ResponseEntity<Itinerary> customizeItineraryWithupdatedStay(@RequestParam String itineraryId,
			@RequestParam long mobileno, @RequestBody StayInfo stayInfo) throws Exception {
		Itinerary itinerary = itineraryService.customizeItineraryWithupdatedStay(stayInfo, mobileno, itineraryId);
		return ResponseEntity.ok(itinerary);
	}

	@PostMapping("/customize/vehicle")
	public ResponseEntity<Itinerary> customizeItineraryWithUpdatedVehicleInfo(@RequestParam String itineraryId,
			@RequestParam long mobileno, @RequestBody VehicleInfo vehicleInfo) throws Exception {
		Itinerary itinerary = itineraryService.customizeItineraryWithUpdatedVehicleInfo(vehicleInfo, itineraryId,
				mobileno);
		return ResponseEntity.ok(itinerary);
	}

	@GetMapping("/search/customerid")
	public ResponseEntity<List<Itinerary>> fetchItinerariesOfCustomer(@RequestParam long customerId) {
		List<Itinerary> itineraries = itineraryService.fetchItinerariesOfCustomer(customerId);
		return ResponseEntity.ok(itineraries);
	}

	@PostMapping("/customize/transfers")
	public ResponseEntity<Itinerary> addTransfers(@RequestParam String itineraryId, @RequestParam long mobileno,
			@RequestBody Set<Transfer> transfers) throws Exception {
		Itinerary itinerary = itineraryService.addTransfersToItinerary(itineraryId, mobileno, transfers);
		return ResponseEntity.ok(itinerary);
	}

}
