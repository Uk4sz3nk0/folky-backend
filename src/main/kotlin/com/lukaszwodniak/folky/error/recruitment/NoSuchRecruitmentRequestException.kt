package com.lukaszwodniak.folky.error.recruitment

/**
 * NoSuchRecruitmentRequestException
 *
 * Created on: 2024-10-20
 * @author Łukasz Wodniak
 */

class NoSuchRecruitmentRequestException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {
        fun buildMessage(id: Long): String {
            return "Recruitment request with id = $id does not exists"
        }
    }
}