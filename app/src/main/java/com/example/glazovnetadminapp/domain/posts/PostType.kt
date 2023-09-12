package com.example.glazovnetadminapp.domain.posts

sealed class PostType(
    val description: String
) {
    object News: PostType(
        description = "News"
    )
    object Announcement: PostType(
        description = "Announcement"
    )
    object Greeting: PostType(
        description = "Greetings"
    )
    object Advertisement: PostType(
        description = "Advertisement"
    )

    companion object {
        fun fromPostTypeCode(code: Int): PostType {
            return when (code) {
                0 -> News
                1 -> Announcement
                2 -> Greeting
                3 -> Advertisement
                else -> News
            }
        }
        fun toPostTypeCode(postType: PostType): Int {
            return when (postType) {
                News -> 0
                Announcement -> 1
                Greeting -> 2
                Advertisement -> 3
            }
        }
    }
}


