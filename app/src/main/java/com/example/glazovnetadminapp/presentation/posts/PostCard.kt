package com.example.glazovnetadminapp.presentation.posts

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.domain.posts.PostType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PostCard(
    postId: String = "",
    title: String,
    creationDate: LocalDateTime,
    shortDescription: String? = null,
    fullDescription: String,
    postType: PostType,
    imageUrl: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(16.dp)

    ) {
        Column(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                softWrap = true,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = creationDate.format(
                    DateTimeFormatter.ofPattern("dd.MM.YY HH:mm")
                ),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = fullDescription,
                style = MaterialTheme.typography.bodyMedium,
                softWrap = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    PostCard(
        title = "Example Post",
        creationDate = LocalDateTime.now(),
        fullDescription = "This post is testing querys from server database",
        postType = PostType.News
    )
}