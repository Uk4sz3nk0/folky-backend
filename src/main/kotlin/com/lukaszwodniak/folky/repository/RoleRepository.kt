package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * RoleRepository
 * Created on: 2024-07-28
 * @author Łukasz Wodniak
 */

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
}