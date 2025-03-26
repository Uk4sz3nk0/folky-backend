package com.lukaszwodniak.folky.records

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * UserData
 *
 * Created on: 2025-02-09
 * @author Łukasz Wodniak
 */

class UserData {
    @JsonProperty("id")
    var id: Long? = null
    @JsonProperty("firstName")
    var firstName: String? = null
    @JsonProperty("lastName")
    var lastName: String? = null
    @JsonProperty("wantReceiveEmailNotifications")
    var wantReceiveEmailNotifications: Boolean = false
    @JsonProperty("wantReceivePushNotifications")
    var wantReceivePushNotifications: Boolean = false
}