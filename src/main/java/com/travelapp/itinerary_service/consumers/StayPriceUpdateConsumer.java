package com.travelapp.itinerary_service.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.itinerary_service.dtos.StayPriceUpdateDto;
import com.travelapp.itinerary_service.services.ItineraryService;

@Service
public class StayPriceUpdateConsumer {
	@Autowired
	ItineraryService itineraryService;

	@KafkaListener(topics = {
			"staypriceupdttopic" }, containerFactory = "kafkaListenerStringFactory", concurrency = "4", groupId = "stay-group")
	public void consumeStayPriceUpdates(@Payload String message) throws JsonMappingException, JsonProcessingException {
		System.out.println("stay price update consumer received message");
		StayPriceUpdateDto stayPriceUpdateDto = new ObjectMapper().readValue(message, StayPriceUpdateDto.class);
		itineraryService.updateStayPricesForItineraries(stayPriceUpdateDto);
	}
}
