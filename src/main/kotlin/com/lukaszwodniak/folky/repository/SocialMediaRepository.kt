package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.SocialMedia
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialMediaRepository : JpaRepository<SocialMedia, Long> {
}