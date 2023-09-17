package com.example.glazovnetadminapp.presentation.posts.postDetails

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.posts.PostType
import com.example.glazovnetadminapp.domain.util.convertDaysOffsetToString
import java.time.OffsetDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    post: PostModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() //Поведение TopAppBar при прокрутке
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection), //Связываем информацию о прокрутке со Scaffold
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Post Screen")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                overflow = TextOverflow.Ellipsis
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

@Preview(showBackground = true)
@Composable
fun PreviewPostDetailScreen() {
    PostDetailScreen(
        post = PostModel(
            postId = "6506ba8452052d5e6b16023b",
            title = "Example post with some title",
            creationDate = OffsetDateTime.now(ZoneId.systemDefault()),
            fullDescription = "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas sit amet magna nec nulla tempor pellentesque non et nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Etiam id vehicula ipsum. Duis leo eros, pharetra nec justo ac, malesuada bibendum nunc. Vivamus blandit maximus pulvinar. Mauris maximus arcu sit amet dui dapibus, sollicitudin mattis dolor commodo. Nullam auctor consequat bibendum. Integer massa dolor, accumsan et molestie in, ultrices ac nunc. Curabitur vestibulum quis ante at tristique. Maecenas vel leo at sem posuere tempor in ac tellus. Duis sollicitudin non urna quis pellentesque. Nunc tristique, nulla at accumsan tincidunt, enim mauris rhoncus ex, quis mattis arcu magna sed nulla.",
            postType = PostType.News,
            imageUrl = "http://example.resorce.com/example-image-001.png"
        )
    )
}