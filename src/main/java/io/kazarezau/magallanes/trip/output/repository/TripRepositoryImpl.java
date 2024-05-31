package io.kazarezau.magallanes.trip.output.repository;

import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.TripStatus;
import io.kazarezau.magallanes.trip.output.mapper.TripMapper;
import io.kazarezau.magallanes.trip.output.model.AttendeeEntity;
import io.kazarezau.magallanes.trip.output.model.TripEntity;
import io.kazarezau.magallanes.trip.output.model.TripEntityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Repository
@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepository {

    @PersistenceContext
    private EntityManager em;
    private final TripMapper tripMapper;
    private final TripEntityRepository repository;

    @Override
    public Trip findById(final UUID id) {
        final TripEntity tripEntity = repository.findById(id).orElseThrow();
        final Trip trip = tripMapper.tripEntityToTrip(tripEntity);
        em.detach(tripEntity);

        return trip;
    }

    @Override
    public Trip save(final Trip trip) {
        final TripEntity entity = tripMapper.tripToTripEntity(trip);

        final TripEntity saved = repository.save(entity);
        return tripMapper.tripEntityToTrip(saved);
    }

    @Override
    public Trip update(final Trip trip) {
        final TripEntity entity = tripMapper.tripToTripEntity(trip);

        final TripEntity merged = em.merge(entity);
        return tripMapper.tripEntityToTrip(merged);
    }

    @Override
    public void update(final List<Trip> trips) {
        trips.forEach(this::update);
    }

    @Override
    public List<Trip> findAllPlannedWithCurrentDateAsStartDate() {
        return repository.findTripEntitiesByStartDateAndStatusIs(LocalDate.now(), TripStatus.PLANNED)
                .stream().map(tripMapper::tripEntityToTrip)
                .toList();
    }

    @Override
    public List<Trip> findAllOverdueActive() {
        return repository.findTripEntitiesByEndDateBeforeAndStatusIs(LocalDate.now(), TripStatus.ACTIVE)
                .stream().map(tripMapper::tripEntityToTrip)
                .toList();
    }

    @Override
    public List<Trip> findAllWithCriteria(TripFilteringCriteria criteria, Pageable pageable) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<TripEntity> query = criteriaBuilder.createQuery(TripEntity.class);
        final Root<TripEntity> tripRoot = query.from(TripEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(criteria.getAttendeesIds())) {
            Join<TripEntity, AttendeeEntity> attendeesJoin = tripRoot.join("attendees");
            final List<Predicate> attendeesPredicates = criteria.getAttendeesIds()
                    .stream()
                    .map(AttendeeEntity::new)
                    .map(attendee -> criteriaBuilder.equal(attendeesJoin, attendee))
                    .toList();
            predicates.add(criteriaBuilder.or(attendeesPredicates.toArray(new Predicate[0])));
        }

        Predicate startDate = null;
        final LocalDate startDateFrom = criteria.getStartDateFrom();
        final LocalDate startDateTo = criteria.getStartDateTo();
        if (startDateFrom != null && startDateTo != null) {
            startDate = criteriaBuilder.between(tripRoot.get("startDate"), startDateFrom, startDateTo);
        }

        Predicate endDate = null;
        final LocalDate endDateFrom = criteria.getEndDateFrom();
        final LocalDate endDateTo = criteria.getEndDateTo();
        if (endDateFrom != null && endDateTo != null) {
            endDate = criteriaBuilder.between(tripRoot.get("endDate"), endDateFrom, endDateTo);
        }

        final Predicate timePeriods;
        if (startDate != null && endDate == null) {
            timePeriods = startDate;
            predicates.add(timePeriods);
        } else if (startDate == null && endDate != null) {
            timePeriods = endDate;
            predicates.add(timePeriods);
        } else if (startDate != null && endDate != null) {
            timePeriods = criteriaBuilder.or(startDate, endDate);
            predicates.add(timePeriods);
        }

        Optional.ofNullable(criteria.getCountry())
                .map(country -> criteriaBuilder.like(tripRoot.get("country"), country))
                .ifPresent(predicates::add);

        query.where(predicates.toArray(new Predicate[0]));

        final TypedQuery<TripEntity> typedQuery = em.createQuery(query);

        return typedQuery.getResultStream().map(tripMapper::tripEntityToTrip).toList();
    }
}
