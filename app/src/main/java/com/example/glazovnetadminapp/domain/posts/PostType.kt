package com.example.glazovnetadminapp.domain.posts

import com.example.glazovnetadminapp.R

sealed class PostType(
    val description: String,
    val stringResourceId: Int
) {
    object News: PostType(
        description = "News",
        stringResourceId = R.string.post_type_news
    )
    object Announcement: PostType(
        description = "Announcement",
        stringResourceId = R.string.post_type_announcement
    )
    object Greeting: PostType(
        description = "Greetings",
        stringResourceId = R.string.post_type_greetings
    )
    object Advertisement: PostType(
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


