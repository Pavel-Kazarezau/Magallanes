CREATE TABLE IF NOT EXISTS trips_attendees
(
    trip_entity_id UUID NOT NULL,
    attendees_id   UUID NOT NULL,
    FOREIGN KEY (trip_entity_id) REFERENCES trips (id),
    FOREIGN KEY (attendees_id) REFERENCES users (id)
);