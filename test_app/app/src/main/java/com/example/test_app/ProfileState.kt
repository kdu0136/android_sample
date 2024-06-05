package com.example.test_app

data class ProfileState(
    val profilePicUrl: String? = null,
    val userName: String? = null,
    val description: String? = null,
    val posts: List<Post> = emptyList(),
)
