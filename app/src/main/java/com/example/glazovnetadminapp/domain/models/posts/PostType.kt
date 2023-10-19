package com.example.glazovnetadminapp.domain.models.posts

import com.example.glazovnetadminapp.R

sealed class PostType(
    val description: String,
    val stringResourceId: Int
) {
    data object News: PostType(
        description = "News",
        stringResourceId = R.string.post_type_news
    )
    data object Announcement: PostType(
        description = "Announcement",
        stringResourceId = R.string.post_type_announcement
    )
    data object Greeting: PostType(
        description = "Greetings",
        stringResourceId = R.string.post_type_greetings
    )
    data object Advertisement: PostType(
        description = "Advertisement",
        stringResourceId = R.string.post_type_advertisement
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
        fun PostType.toPostTypeCode(): Int {
            return when (this) {
                News -> 0
                Announcement -> 1
                Greeting -> 2
                Advertisement -> 3
            }
        }
        fun values() = listOf(
            News,
            Announcement,
            Greeting,
            Advertisement
        )
    }
}


