package com.example.glazovnetadminapp.presentation.posts.postsList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.posts.PostType
import com.example.glazovnetadminapp.domain.util.convertDaysOffsetToString
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun PostCard(
    postModel: PostModel?,
    modifier: Modifier = Modifier
) {
    postModel?.let {post ->
        val descriptionMaxLines = if (post.imageUrl == null) 10
        else 2
        Card(

            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(8.dp)
                .clickable { }
                .fillMaxWidth()

        ) {
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    softWrap = true,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${post.creationDate.convertDaysOffsetToString()}, ID: ${post.postId}", //Конвертируем к локальному времени
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(5.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier
                        .animateContentSize(),
                    text = post.shortDescription ?: post.fullDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = descriptionMaxLines
                )
                if (post.imageUrl != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {}
                    Text(
                        textAlign = TextAlign.End,
                        text = "${stringResource(id = R.string.posts_source_text)}: ${post.imageUrl}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    PostCard(
        PostModel(
            postId = "131414124",
            title = "Example Post",
            creationDate = OffsetDateTime.parse(
                "2023-09-17T17:15:00+05:00",
                DateTimeFormatter.ISO_DATE_TIME
            ),
            fullDescription = "This post is testing querys from server database",
            postType = PostType.News,
            imageUrl = "https://rubygarage.s3.amazonaws.com/uploads/article_image/file/2060/Artboard_15587.png",
            videoUrl = null,
            shortDescription = null
        )
    )
}