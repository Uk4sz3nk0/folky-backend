package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.records.*
import com.lukaszwodniak.folky.rest.specification.models.ChangePasswordRequestDto
import com.lukaszwodniak.folky.rest.specification.models.LoginResponseDto
import com.lukaszwodniak.folky.rest.specification.models.RefreshTokenResponseDto
import com.lukaszwodniak.folky.rest.specification.models.RegisterDancingTeamAccountRequestDto
import com.lukaszwodniak.folky.rest.specification.models.RegisterUserRequestDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * SecurityMapper
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Mapper
interface SecurityMapper {

    fun mapRegisterRequest(request: RegisterUserRequestDto): RegisterUserRequest
    fun mapLoginResponse(response: LoginResponse): LoginResponseDto
    fun mapRefreshTokenResponse(response: RefreshTokenResponse): RefreshTokenResponseDto
    fun mapPasswordChangeRequestToDomain(changePasswordRequestDto: ChangePasswordRequestDto): PasswordChangeRequest

    @Mapping(target = "region", ignore = true)
    @Mapping(target = "files", ignore = true)
    fun mapRegisterDancingTeamRequest(request: RegisterDancingTeamAccountRequestDto): RegisterDancingTeamUserRequest

    companion object {
        val INSTANCE: SecurityMapper = Mappers.getMapper(SecurityMapper::class.java)
    }
}