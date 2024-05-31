package io.kazarezau.magallanes.trip.output.mapper;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.output.model.AttendeeEntity;
import io.kazarezau.magallanes.trip.output.model.TripEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "creatorId", source = "creatorId.id")
    @Mapping(target = "country", source = "point.country")
    @Mapping(target = "city", source = "point.city")
    TripEntity tripToTripEntity(Trip trip);

    @Mapping(target = "id.id", source = "id")
    @Mapping(target = "creatorId.id", source = "creatorId")
    @Mapping(target = "point.country", source = "country")
    @Mapping(target = "point.city", source = "city")
    Trip tripEntityToTrip(TripEntity tripEntity);

    TripEntity mapEntity(TripEntity tripEntity);

    default AttendeeEntity userIdToAttendeeEntity(User.UserId word) {
        final AttendeeEntity entity = new AttendeeEntity();
        entity.setId(word.id());
        return entity;
    }

    default User.UserId attendeeEntityToUserId(AttendeeEntity word) {
        return new User.UserId(word.getId());
    }
}
