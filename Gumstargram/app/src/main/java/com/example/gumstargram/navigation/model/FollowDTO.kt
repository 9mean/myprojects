package com.example.gumstargram.navigation.model

data class FollowDTO(
    var followingCount : Int=0,
    var followings: MutableMap<String,Boolean> = HashMap(),
    var followerCount : Int=0,
    var followers: MutableMap<String,Boolean> = HashMap()
)