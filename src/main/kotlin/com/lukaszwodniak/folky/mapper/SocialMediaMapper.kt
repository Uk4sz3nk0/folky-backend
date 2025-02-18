package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.SocialMedia
import com.lukaszwodniak.folky.rest.specification.models.SocialMediaDto
import org.mapstruct.Mapper

/**
 * SocialMediaMapper
 *
 *  Created on: 2025-02-21
 *  @author Łukasz Wodniak
 */

@Mapper
interface SocialMediaMapper {

    fun mapFromDto(socialMedia: SocialMediaDto): SocialMedia
    fun mapToDto(socialMedia: SocialMedia): SocialMediaDto
}