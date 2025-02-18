package com.lukaszwodniak.folky.service.dancingTeam.impl

import com.lukaszwodniak.folky.error.DancingTeamWithGivenNameExistsException
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.model.*
import com.lukaszwodniak.folky.records.DancingTeamFiles
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.SubscriptionRepository
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.files.impl.FilesServiceImpl
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.io.path.Path

/**
 * DancingTeamServiceImpl
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Service
@RequiredArgsConstructor
class DancingTeamServiceImpl(
    private val dancingTeamRepository: DancingTeamRepository,
    private val subscriptionRepository: SubscriptionRepository
) : DancingTeamService {

    override fun addTeam(team: DancingTeam, user: User?): DancingTeam {
        // TODO: Implement additional logic if needed
        if (dancingTeamRepository.existsByNameIgnoreCase(team.name)) {
            throw DancingTeamWithGivenNameExistsException("Dancing team with name \"${team.name}\" already exists")
        }
        val mappedTeam = team.copy(
            director = user,
            accountUser = user,
            filesUUID = FilesServiceImpl.generateTeamDirectory()
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
        val existingTeam =
            dancingTeamRepository.findById(team.id ?: -1).orElseThrow { NoSuchDancingTeamException(team.id ?: -1) }
        updateExistingDancingTeam(existingTeam, team)
        return dancingTeamRepository.save(existingTeam)
    }

    override fun deleteTeam(teamId: Long) {
        dancingTeamRepository.deleteById(teamId)
    }

    override fun getById(teamId: Long): DancingTeam {
        return dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
    }

    override fun getByRegion(region: Region): List<DancingTeam> {
        return dancingTeamRepository.findAllByRegion(region).orElse(emptyList())
    }

    override fun getTeamDances(teamId: Long): List<Dance> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.dances ?: emptyList()
    }

    override fun getTeamDancers(teamId: Long): List<User> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.dancers ?: emptyList()
    }

    override fun getTeamMusicians(teamId: Long): List<User> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.musicians ?: emptyList()
    }

    override fun getTeams(pageRequest: PageRequest, searchPhrase: String?): Page<DancingTeam> {
        return if (!searchPhrase.isNullOrBlank()) {
            dancingTeamRepository.findAllByNameContainsIgnoreCase(searchPhrase, pageRequest)
        } else {
            dancingTeamRepository.findAll(pageRequest)
        }
    }

    override fun getTeamsByName(phrase: String): List<DancingTeam> {
        return dancingTeamRepository.findAllByNameContainsIgnoreCase(phrase).orElse(emptyList())
    }

    override fun getSubscribedTeams(user: User): List<DancingTeam> {
        return subscriptionRepository.findAllByUser(user).map { sub -> sub.dancingTeam }
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

    private fun updateExistingDancingTeam(existingTeam: DancingTeam, newTeamData: DancingTeam) {
        existingTeam.name = newTeamData.name
        existingTeam.description = newTeamData.description
        existingTeam.creationDate = newTeamData.creationDate
        existingTeam.region = newTeamData.region
        existingTeam.city = newTeamData.city
        existingTeam.street = newTeamData.street
        existingTeam.homeNumber = newTeamData.homeNumber
        existingTeam.flatNumber = newTeamData.flatNumber
        existingTeam.zipCode = newTeamData.zipCode
        existingTeam.dances = newTeamData.dances
        existingTeam.dancers = newTeamData.dancers
        existingTeam.musicians = newTeamData.musicians
        val socialMedia = newTeamData.socialMedia
        socialMedia?.dancingTeam = existingTeam
        existingTeam.socialMedia = socialMedia
        existingTeam.accountUser = newTeamData.accountUser
        existingTeam.director = newTeamData.director
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
        const val UPLOADS_DIRECTORY: String = "storage"
        const val IMAGES_DIRECTORY: String = "images"
    }
}