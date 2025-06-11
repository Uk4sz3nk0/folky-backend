package com.lukaszwodniak.folky.service.dancingTeam.impl

import com.lukaszwodniak.folky.error.DancingTeamWithGivenNameExistsException
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.model.*
import com.lukaszwodniak.folky.records.DancingTeamFiles
import com.lukaszwodniak.folky.records.FilterTeamsObject
import com.lukaszwodniak.folky.repository.AchievementsRepository
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.PeopleRepository
import com.lukaszwodniak.folky.repository.SubscriptionRepository
import com.lukaszwodniak.folky.service.dance.DanceService
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.files.FilesService
import com.lukaszwodniak.folky.service.people.PeopleService
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.LocalDate
import java.util.*
import kotlin.io.path.Path

/**
 * DancingTeamServiceImpl
 *
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Service
@RequiredArgsConstructor
class DancingTeamServiceImpl(
    private val dancingTeamRepository: DancingTeamRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val filesService: FilesService,
    private val achievementsRepository: AchievementsRepository,
    private val peopleRepository: PeopleRepository,
    private val dancesService: DanceService,
    private val peopleService: PeopleService
) : DancingTeamService {

    override fun addTeam(team: DancingTeam, user: User?): DancingTeam {
        // TODO: Implement additional logic if needed
        if (dancingTeamRepository.existsByNameIgnoreCase(team.name)) {
            throw DancingTeamWithGivenNameExistsException("Dancing team with name \"${team.name}\" already exists")
        }
        val mappedTeam = team.copy(
            director = user,
            accountUser = user,
            filesUUID = UUID.randomUUID()
        )
        mappedTeam.socialMedia?.dancingTeam = mappedTeam
        return dancingTeamRepository.saveAndFlush(mappedTeam)
    }

    override fun addTeam(team: DancingTeam, files: DancingTeamFiles): DancingTeam {
        if (dancingTeamRepository.existsByNameIgnoreCase(team.name)) {
            throw DancingTeamWithGivenNameExistsException("Dancing team with name \"${team.name}\" already exists")
        }
        var dancingTeam = team
        files.logo?.let {
            storeDancingTeamFile(team.filesUUID, it)
            dancingTeam = dancingTeam.copy(logoFilename = it.originalFilename)
        }
        files.banner?.let {
            storeDancingTeamFile(team.filesUUID, it)
            dancingTeam = dancingTeam.copy(bannerFilename = it.originalFilename)
        }
        return dancingTeamRepository.saveAndFlush(dancingTeam)
    }

    override fun updateTeam(team: DancingTeam): DancingTeam {
        val existingTeam = getById(team.id!!)
        val updatedTeam = updateExistingDancingTeam(existingTeam, team)
        return dancingTeamRepository.save(updatedTeam)
    }

    override fun deleteTeam(teamId: Long) {
        val dancingTeam = getById(teamId)
        filesService.deleteTeamDirectory(dancingTeam.filesUUID)
        dancingTeamRepository.deleteById(teamId)
    }

    override fun getById(teamId: Long): DancingTeam {
        return dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
    }

    override fun getTeamDances(teamId: Long, pageRequest: PageRequest): Page<Dance> {
        val dancingTeam = getById(teamId)
        val dances = dancingTeam.dances ?: emptyList()
        val start = pageRequest.offset.toInt()
        val end = (start + pageRequest.pageSize).coerceAtMost(dances.size)

        val pagedDances = if (start >= dances.size) {
            emptyList()
        } else {
            dances.subList(start, end)
        }
        val translatedDances = dancesService.assignTranslatedNames(pagedDances)
        return PageImpl(translatedDances, pageRequest, dances.size.toLong())
    }

    override fun getTeams(pageRequest: PageRequest, searchPhrase: String?): Page<DancingTeam> {
        return if (!searchPhrase.isNullOrBlank()) {
            dancingTeamRepository.findAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(
                searchPhrase,
                searchPhrase,
                pageRequest
            )
        } else {
            dancingTeamRepository.findAll(pageRequest)
        }
    }

    override fun getTeams(
        pageRequest: PageRequest,
        searchPhrase: String?,
        filterTeamsObject: FilterTeamsObject?
    ): Page<DancingTeam> {
        if (filterTeamsObject != null) {
            var filterSpecification: Specification<DancingTeam> = Specification.where(null)


            val searchPhrasePredicate = searchPhrase?.let {
                Specification { root: Root<DancingTeam>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                    cb.like(root.get("name"), "%$searchPhrase%")
                }.or { root: Root<DancingTeam>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                    cb.like(root.get("description"), "%$searchPhrase%")
                }
            }
            if (searchPhrasePredicate != null) {
                filterSpecification = filterSpecification.and(searchPhrasePredicate)
            }

            val creationYearPredicate = filterTeamsObject.creationDate?.let {
                Specification { root: Root<DancingTeam>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                    val startDate = LocalDate.of(it.start, java.time.Month.JANUARY, 1)
                    val endDate = LocalDate.of(it.end, java.time.Month.DECEMBER, 31)

                    cb.between(root.get("creationDate"), startDate, endDate)
                }
            }
            if (creationYearPredicate != null) {
                filterSpecification = filterSpecification.and(creationYearPredicate)
            }

            val dancersAmountPredicate = filterTeamsObject.dancersAmount?.let {
                Specification { root: Root<DancingTeam>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
                    if (it.end <= MORE_PLUS_RANGE) {
                        criteriaBuilder.between(root.get("dancersAmount"), it.start, it.end)
                    } else {
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dancersAmount"), it.start)
                    }
                }
            }
            if (dancersAmountPredicate != null) {
                filterSpecification = filterSpecification.and(dancersAmountPredicate)
            }

            val musicianAmountPredicate = filterTeamsObject.musiciansAmount?.let {
                Specification { root: Root<DancingTeam>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
                    if (it.end <= MORE_PLUS_RANGE) {
                        criteriaBuilder.between(root.get("musiciansAmount"), it.start, it.end)
                    } else {
                        criteriaBuilder.greaterThanOrEqualTo(root.get("musiciansAmount"), it.start)
                    }
                }
            }
            if (musicianAmountPredicate != null) {
                filterSpecification = filterSpecification.and(musicianAmountPredicate)
            }

            val regionsPredicate = filterTeamsObject.selectedRegions.takeIf { it.isNotEmpty() }?.let {
                Specification { root: Root<DancingTeam>, _: CriteriaQuery<*>, _: CriteriaBuilder ->
                    root.get<Any>("region").get<Long>("id").`in`(it)
                }
            }
            if (regionsPredicate != null) {
                filterSpecification = filterSpecification.and(regionsPredicate)
            }

            val socialMediaPredicate = filterTeamsObject.ownedSocialMedia
                .map {
                    if (it == TIKTOK_FILTER_NAME) CORRECT_TIKTOK_FILTER_NAME else it
                }
                .takeIf { it.isNotEmpty() }
                ?.let { socialPlatforms ->
                    Specification { root: Root<DancingTeam>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->

                        val predicates = socialPlatforms.map { platform ->
                            criteriaBuilder.isNotNull(
                                root.get<SocialMedia>("socialMedia").get<String>("${platform}Url")
                            )
                        }

                        criteriaBuilder.or(*predicates.toTypedArray())
                    }
                }
            if (socialMediaPredicate != null) {
                filterSpecification = filterSpecification.and(socialMediaPredicate)
            }

            return dancingTeamRepository.findAll(filterSpecification, pageRequest)
        } else {
            return getTeams(pageRequest, searchPhrase)
        }
    }

    override fun getSubscribedTeams(user: User, pageRequest: PageRequest): Page<DancingTeam> {
        return subscriptionRepository.findAllDancingTeamsByUser(user, pageRequest)
    }

    override fun addSubscription(team: DancingTeam, user: User) {
        if (!subscriptionRepository.existsByUserAndDancingTeam(user, team)) {
            val newSubscription = Subscription(user = user, dancingTeam = team)
            subscriptionRepository.saveAndFlush(newSubscription)
        }
    }

    override fun deleteSubscription(team: DancingTeam, user: User) {
        if (subscriptionRepository.existsByUserAndDancingTeam(user, team)) {
            val optionalSub = subscriptionRepository.findByUserAndDancingTeam(user, team)
            optionalSub.ifPresent { subscriptionRepository.deleteById(it.id!!) }
        }
    }

    override fun getTeamAchievements(teamId: Long, pageRequest: PageRequest): Page<Achievement> {
        val team = getById(teamId)
        return achievementsRepository.findByDancingTeam(team, pageRequest)
    }

    override fun getTeamPeople(teamId: Long, pageRequest: PageRequest, phrase: String?): Page<Person> {
        val predicate = Specification.where { root: Root<Person>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
            cb.equal(root.get<DancingTeam>("dancingTeam").get<Long>("id"), teamId)
        }
        val phrasePredicate = phrase?.let {
            Specification { root: Root<Person>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                val phraseLike = "%${it.lowercase()}%"
                cb.or(
                    cb.like(cb.lower(root.get("firstName")), phraseLike),
                    cb.like(cb.lower(root.get("lastName")), phraseLike)
                )
            }
        }
        val finalPredicate = predicate.and(phrasePredicate)
        return peopleRepository.findAll(finalPredicate, pageRequest)
    }

    override fun updateTeamDances(teamId: Long, dances: List<Dance>) {
        val dancingTeam = getById(teamId)
        dancingTeam.dances = dances.toMutableList()
        dancingTeamRepository.saveAndFlush(dancingTeam)
    }

    override fun setTeamPeople(teamId: Long, people: List<Person>) {
        val team = getById(teamId)
        val teamAssignedPeople = people.map { it.copy(dancingTeam = team) }
        val mappedPeople = peopleService.updatedPeople(teamAssignedPeople)
        peopleRepository.saveAllAndFlush(mappedPeople)
    }

    private fun updateExistingDancingTeam(existingTeam: DancingTeam, newTeamData: DancingTeam): DancingTeam {
        val socialMedia = newTeamData.socialMedia
        socialMedia?.dancingTeam = existingTeam
        return existingTeam.copy(
            name = newTeamData.name,
            description = newTeamData.description,
            creationDate = newTeamData.creationDate,
            region = newTeamData.region,
            city = newTeamData.city,
            street = newTeamData.street,
            homeNumber = newTeamData.homeNumber,
            flatNumber = newTeamData.flatNumber,
            zipCode = newTeamData.zipCode,
            dances = newTeamData.dances,
            dancers = newTeamData.dancers,
            socialMedia = socialMedia,
            accountUser = newTeamData.accountUser,
            director = newTeamData.director,
            isRecruitmentOpened = newTeamData.isRecruitmentOpened,
            isVerified = newTeamData.isVerified,
            ageCategories = newTeamData.ageCategories
        )
    }

    private fun storeDancingTeamFile(directoryUUID: UUID, file: MultipartFile) {
        val basePath =
            Path("${UPLOADS_DIRECTORY}${File.separator}${directoryUUID}${File.separator}${file.originalFilename}")
        try {
            Files.copy(file.inputStream, basePath, StandardCopyOption.REPLACE_EXISTING)
        } catch (exception: Exception) {
            logger.error("Error during storing file. Reason: ${exception.localizedMessage}")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DancingTeamServiceImpl::class.java)
        private const val TIKTOK_FILTER_NAME: String = "tik-tok"
        private const val CORRECT_TIKTOK_FILTER_NAME: String = "tikTok"
        private const val MORE_PLUS_RANGE: Int = 50
        const val UPLOADS_DIRECTORY: String = "storage"
    }
}