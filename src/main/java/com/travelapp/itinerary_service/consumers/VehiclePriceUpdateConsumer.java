package com.travelapp.itinerary_service.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.itinerary_service.dtos.VehiclePriceUpdateDto;
import com.travelapp.itinerary_service.services.ItineraryService;

@Service
public class VehiclePriceUpdateConsumer {
	@Autowired
	ItineraryService itineraryService;

	@KafkaListener(topics = {
			"vehpriceupdttopic" }, containerFactory = "kafkaListenerStringFactory", concurrency = "4", groupId = "vehicle-group")
	public void cosumesVehiclePriceUpdates(@Payload String message)
			throws JsonMappingException, JsonProcessingException {
		System.out.println("vehicle price update consumer received message");
		VehiclePriceUpdateDto vehiclePriceUpdateDto = new ObjectMapper().readValue(message,
				VehiclePriceUpdateDto.class);
		itineraryService.updateVehiclePriceForItineraries(vehiclePriceUpdateDto);
	}
}
