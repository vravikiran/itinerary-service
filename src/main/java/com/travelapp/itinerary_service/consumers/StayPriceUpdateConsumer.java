package com.travelapp.itinerary_service.consumers;

import com.travelapp.itinerary_service.dtos.StayRoomPriceUpdateDto;
import com.travelapp.itinerary_service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.itinerary_service.services.ItineraryService;

@Service
public class StayPriceUpdateConsumer {
	@Autowired
	ItineraryService itineraryService;

	@KafkaListener(topics = {
            Constants.STAY_ROOM_PRICE_UPDATES_TOPIC}, containerFactory = "kafkaListenerStringFactory", concurrency = "4", groupId = "stay-group")
	public void consumeStayPriceUpdates(@Payload String message) throws JsonMappingException, JsonProcessingException {
		System.out.println("stay price update consumer received message");
		StayRoomPriceUpdateDto stayRoomPriceUpdateDto = new ObjectMapper().readValue(message, StayRoomPriceUpdateDto.class);
		itineraryService.updateStayPricesForItineraries(stayRoomPriceUpdateDto);
	}
}
