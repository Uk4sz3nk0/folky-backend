package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * UserRoleRepository
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long> {
}