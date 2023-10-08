package com.example.glazovnetadminapp.presentation.posts.postsList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.example.glazovnetadminapp.domain.util.convertDaysOffsetToString
import com.example.glazovnetadminapp.presentation.destinations.AddPostScreenDestination
import com.example.glazovnetadminapp.presentation.destinations.HomeScreenDestination
import com.example.glazovnetadminapp.presentation.destinations.PostDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PostCard(
    postModel: PostModel?,
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator
) {
    postModel?.let { post ->
        val descriptionMaxLines = if (post.image == null) 10
        else 2
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clickable {
                    navigator.navigate(
                        PostDetailScreenDestination(postId = post.postId),
                        onlyIfResumed = true
                    )
                }
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(8.dp),
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
                    text = post.shortDescription ?: post.fullDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = descriptionMaxLines
                )
                post.image?.let { image ->
                    val imageAspectRatio = image.imageWidth.toFloat() / image.imageHeight.toFloat()
                    Spacer(modifier = Modifier.height(10.dp))
                    AsyncImage(
                        model = image.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .aspectRatio(imageAspectRatio),
                        contentScale = ContentScale.Crop,
                        filterQuality = FilterQuality.Medium
//                        onSuccess = {
//                            println("image width: ${it.painter.intrinsicSize.width}, image height: ${it.painter.intrinsicSize.height}")
//                        }
                    )
                    Text(
                        textAlign = TextAlign.End,
                        text = "${stringResource(id = R.string.posts_source_text)}: ${image.imageUrl}",
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

}