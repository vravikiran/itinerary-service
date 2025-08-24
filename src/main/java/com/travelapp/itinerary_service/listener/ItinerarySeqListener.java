package com.travelapp.itinerary_service.listener;

import com.travelapp.itinerary_service.entities.Itinerary;
import com.travelapp.itinerary_service.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ItinerarySeqListener extends AbstractMongoEventListener<Itinerary> {

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ItinerarySeqListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Itinerary> event) {
        if (event.getSource().getId() == null) { // check for null instead of 0
            event.getSource().setId(sequenceGenerator.generateSequence(Itinerary.SEQUENCE_NAME));
        }
    }

}
