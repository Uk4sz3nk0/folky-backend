package com.lukaszwodniak.folky.service.events.impl

import com.lukaszwodniak.folky.enums.EventConnectionType
import com.lukaszwodniak.folky.enums.EventTime
import com.lukaszwodniak.folky.enums.EventType
import com.lukaszwodniak.folky.enums.UserType
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Event
import com.lukaszwodniak.folky.model.EventTeamInstitution
import com.lukaszwodniak.folky.records.CreatorResponse
import com.lukaszwodniak.folky.records.EventAdditionalData
import com.lukaszwodniak.folky.repository.AddressRepository
import com.lukaszwodniak.folky.repository.EventTeamInstitutionRepository
import com.lukaszwodniak.folky.repository.EventsRepository
import com.lukaszwodniak.folky.service.address.AddressService
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.events.EventsService
import com.lukaszwodniak.folky.service.files.FilesService
import com.lukaszwodniak.folky.service.users.UserService
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

/**
 * EventsServiceImpl
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

@Service
class EventsServiceImpl(
    private val eventsRepository: EventsRepository,
    private val filesService: FilesService,
    private val addressRepository: AddressRepository,
    private val etiRepository: EventTeamInstitutionRepository,
    private val userService: UserService,
    private val addressService: AddressService,
    private val dancingTeamService: DancingTeamService,
) : EventsService {

    @Transactional
    override fun addEvent(event: Event, eventAdditionalData: EventAdditionalData?) {
        // TODO: Add more data on adding by adding by institution or dancing team
        val addressToSave = event.address?.copy(id = null)
        val savedAddress = addressToSave?.let { addressRepository.saveAndFlush(it) }
        val eventToSave = event.copy(
            filesUUID = UUID.randomUUID(),
            id = null,
            address = savedAddress,
            ticketPrice = if (eventAdditionalData?.isFreeEntry == true) null else event.ticketPrice,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
        val savedEvent = eventsRepository.saveAndFlush(eventToSave)

        val contextUser = userService.getUserFromContext()
        val eti = EventTeamInstitution(
            event = savedEvent,
            connectionType = EventConnectionType.CREATOR
        )
        contextUser?.let { user ->
            val team = when {
                user.userType == UserType.DANCING_TEAM -> user.dancingTeams[0]
                userService.isContextAdmin() && eventAdditionalData?.creatorId != null -> dancingTeamService.getById(
                    eventAdditionalData.creatorId
                )

                else -> null
            }
            team?.let {
                val dancingTeamEti = eti.copy(team = it)
                etiRepository.saveAndFlush(dancingTeamEti)
                if (eventAdditionalData?.isParticipantToo == true) {
                    etiRepository.saveAndFlush(
                        dancingTeamEti.copy(
                            connectionType = EventConnectionType.PARTICIPANT,
                            id = null
                        )
                    )
                }
            }
        }

    }

    @Transactional
    override fun deleteEvent(id: Long) {
        val event = eventsRepository.findById(id)
        event.ifPresent {
            etiRepository.deleteByEvent(it)
            eventsRepository.delete(it)
        }
    }

    override fun deleteEventPoster(id: Long) {
        val event = getEvent(id, null)
        event.poster?.let {
            filesService.deleteFile(event.filesUUID!!, it)
        }
    }

    override fun getEvent(id: Long, team: DancingTeam?): Event {
        val event = eventsRepository.findById(id).orElseThrow { NoSuchElementException("No such event with id $id") }
        return if (team != null) {
            val etiList = etiRepository.findAllByEventAndTeam(event, team)
            val connectionsByEvent = etiList.groupBy { it.event?.id }

            val connections = connectionsByEvent[event.id] ?: emptyList()
            val types = connections.map { it.connectionType }

            event.copy(connectionTypes = types)
        } else {
            event
        }
    }

    override fun getEvents(page: Int, size: Int, phrase: String?): Page<Event> {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))
        val phrasePredicate = phrase?.let {
            Specification { root: Root<Event>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                cb.like(cb.lower(root.get("name")), it.lowercase())
            }
        }
        val finalPredicate = phrasePredicate ?: Specification.where(null)
        return eventsRepository.findAll(finalPredicate, pageRequest)
    }

    override fun getPoster(id: Long): Resource? {
        val event = getEvent(id)
        return event.poster?.let {
            filesService.getImageFile(event.filesUUID!!, it)
        }
    }

    @Transactional
    override fun updateEvent(id: Long, event: Event, additionalData: EventAdditionalData?) {
        // TODO: Add more data on adding by adding by institution or dancing team
        val existingEvent: Event = getEvent(id)
        val existingAddress = event.address?.let { addressService.getAddress(it.id!!) }
        val addressToSave = existingAddress?.copy(
            street = event.address.street,
            postalCode = event.address.postalCode,
            city = event.address.city,
            country = event.address.country,
            latitude = event.address.latitude,
            longitude = event.address.longitude,
        )
        val savedAddress = addressToSave?.let { addressRepository.saveAndFlush(it) }
        val eventToUpdate = existingEvent.copy(
            title = event.title,
            description = event.description,
            startDate = event.startDate,
            endDate = event.endDate,
            address = savedAddress,
            ticketPrice = if (additionalData?.isFreeEntry == true) null else event.ticketPrice,
            type = event.type ?: EventType.FESTIVAL,
            poster = event.poster,
        )
        val savedEvent = eventsRepository.saveAndFlush(eventToUpdate)

        // TODO: Handle edit as admin
        additionalData?.let {
            val user = userService.getUserFromContext()
            val team = when {
                user?.userType == UserType.DANCING_TEAM -> user.dancingTeams[0]
                userService.isContextAdmin() && it.creatorId != null -> dancingTeamService.getById(it.creatorId)
                else -> null
            }

            team?.let { dancingTeam ->
                if (it.isParticipantToo == true) {
                    addParticipant(savedEvent, dancingTeam)
                } else if (it.isParticipantToo == false) {
                    deleteParticipant(savedEvent, dancingTeam)
                }

                val existingCreatorEti =
                    etiRepository.findByEventAndConnectionType(savedEvent, EventConnectionType.CREATOR)
                existingCreatorEti.ifPresent { existingEti ->
                    if (existingEti.team != null && existingEti.team != dancingTeam) {
                        etiRepository.deleteByTeamAndEventAndConnectionType(
                            existingEti.team,
                            event,
                            EventConnectionType.CREATOR
                        )
                        val eti = EventTeamInstitution(
                            event = event,
                            team = dancingTeam,
                            connectionType = EventConnectionType.CREATOR
                        )
                        etiRepository.saveAndFlush(eti)
                    }
                }
            }
        }
    }

    override fun updateOrAddPoster(id: Long, poster: MultipartFile) {
        // TODO: Add more data on adding by adding by institution or dancing team
        TODO("Not yet implemented")
    }

    /**
     * In this method, assigning connection type made in current way is in my opinion very bad,
     * this should be rewritten in the future.
     */
    override fun getTeamEvents(
        team: DancingTeam,
        page: Int,
        size: Int,
        connectionTypes: List<EventConnectionType>,
        eventTime: List<EventTime>
    ): Page<Event> {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))
        val eventTimePredicate = prepareEventTimePredicate(eventTime)
        val events = if (connectionTypes.isEmpty()) {
            var predicate = byTeam(team.id!!)
            if (eventTime.isNotEmpty()) {
                predicate = predicate.and(eventTimePredicate)
            }
            eventsRepository.findAll(predicate, pageRequest)
        } else {
            var predicate = byTeam(team.id!!).and(byConnectionTypes(connectionTypes))
            if (eventTime.isNotEmpty()) {
                predicate = predicate.and(eventTimePredicate)
            }
            eventsRepository.findAll(predicate, pageRequest)
        }

        val eventIds = events.content.mapNotNull { it.id }
        val allConnections = etiRepository.findAllByEventIdInAndTeamId(eventIds, team.id)

        val connectionsByEvent = allConnections.groupBy { it.event?.id }

        val mappedEvents = events.content.map { event ->
            val connections = connectionsByEvent[event.id] ?: emptyList()
            val types = connections.map { it.connectionType }.toSet().toList()
            event.copy(connectionTypes = types)
        }
        return PageImpl(mappedEvents, events.pageable, events.totalElements)

    }

    override fun addParticipant(event: Event, team: DancingTeam, withoutCheck: Boolean) {
        if (!etiRepository.existsByTeamAndEventAndConnectionType(
                team,
                event,
                EventConnectionType.PARTICIPANT
            ) || withoutCheck
        ) {
            val eti = EventTeamInstitution(
                event = event,
                team = team,
                connectionType = EventConnectionType.PARTICIPANT
            )
            etiRepository.saveAndFlush(eti)
        }
    }

    @Transactional
    override fun deleteParticipant(event: Event, team: DancingTeam) {
        etiRepository.deleteByTeamAndEventAndConnectionType(team, event, EventConnectionType.PARTICIPANT)
    }

    override fun getParticipants(event: Event): MutableList<DancingTeam> {
        val participants = etiRepository.findAllParticipantsByEvent(event.id!!, listOf(EventConnectionType.PARTICIPANT))
        return participants.toMutableList()
    }

    override fun getCreator(eventId: Long): CreatorResponse {
        val event = getEvent(eventId)
        val etiOptional = etiRepository.findByEventAndConnectionType(event, EventConnectionType.CREATOR)
        return etiOptional.map {
            when {
                it.team != null -> CreatorResponse(UserType.DANCING_TEAM, it.team.name, it.team.id ?: 0)
                it.institution != null -> CreatorResponse(
                    UserType.INSTITUTION,
                    it.institution.name,
                    it.institution.id
                )

                else -> CreatorResponse(UserType.UNKNOWN, "", 0)
            }
        }.orElse(CreatorResponse(UserType.UNKNOWN, "", 0))
    }

    private fun prepareEventTimePredicate(eventTimes: List<EventTime>): Specification<Event>? {
        return if (eventTimes.isEmpty()) {
            null
        } else {
            val specs = eventTimes.map {
                when (it) {
                    EventTime.PAST -> Specification<Event> { root, _, cb ->
                        cb.lessThan(root.get(END_DATE_FIELD), LocalDate.now())
                    }

                    EventTime.FUTURE -> Specification<Event> { root, _, cb ->
                        cb.greaterThan(root.get(START_DATE_FIELD), LocalDate.now())
                    }

                    EventTime.CURRENT -> Specification<Event> { root, _, cb ->
                        val currentDate = LocalDate.now()
                        cb.between(
                            cb.literal(currentDate),
                            root.get(START_DATE_FIELD),
                            root.get(END_DATE_FIELD)
                        )
                    }
                }
            }

            specs.reduceOrNull { acc, spec -> acc.or(spec) }
        }
    }

    private fun byTeam(teamId: Long): Specification<Event> = Specification { root, query, cb ->
        val etiJoin = root.join<Event, EventTeamInstitution>("eti")
        query.distinct(true).orderBy(cb.asc(etiJoin.get<Long>("id")))
        cb.equal(root.get<Event>("eti").get<EventTeamInstitution>("team").get<Long>("id"), teamId)
    }

    private fun byConnectionTypes(connectionTypes: List<EventConnectionType>): Specification<Event> =
        Specification { root, _, _ ->
            val etiJoin = root.join<Event, EventTeamInstitution>("eti")
            etiJoin.get<EventConnectionType>("connectionType").`in`(connectionTypes)
        }

    companion object {
        private val logger = LoggerFactory.getLogger(EventsServiceImpl::class.java.name)
        private const val START_DATE_FIELD: String = "startDate"
        private const val END_DATE_FIELD: String = "endDate"
    }
}