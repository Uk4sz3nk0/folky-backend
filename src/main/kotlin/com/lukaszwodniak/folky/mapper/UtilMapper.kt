package com.lukaszwodniak.folky.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * UtilMapper
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Mapper
interface UtilMapper {

    fun mapStrings(values: List<String>): MutableList<String>

    companion object {
        val INSTANCE: UtilMapper = Mappers.getMapper(UtilMapper::class.java)
    }
}