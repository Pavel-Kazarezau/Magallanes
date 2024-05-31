package io.kazarezau.magallanes.trip.input.mapper;

import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.input.command.CreateTripCommand;
import io.kazarezau.magallanes.trip.input.command.UpdateTripCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripEssenceMapper {

    Trip.Essence createTripCommandToTripEssence(CreateTripCommand command);

    Trip.Essence updateTripCommandToTripEssence(UpdateTripCommand command);
}
