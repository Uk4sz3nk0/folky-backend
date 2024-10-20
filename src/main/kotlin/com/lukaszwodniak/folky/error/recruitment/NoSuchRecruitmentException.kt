package com.lukaszwodniak.folky.error.recruitment

/**
 * NoSuchRecruitmentException
 *
 * Created on: 2024-10-20
 * @author Łukasz Wodniak
 */

class NoSuchRecruitmentException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {
        fun buildMessage(id: Long): String {
            return "Recruitment with id = $id does not exist"
        }
    }
}