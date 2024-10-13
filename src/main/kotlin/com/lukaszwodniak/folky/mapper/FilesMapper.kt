package com.lukaszwodniak.folky.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * FilesMapper
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Mapper
interface FilesMapper {

    companion object {
        val INSTANCE: FilesMapper = Mappers.getMapper(FilesMapper::class.java)
    }
}