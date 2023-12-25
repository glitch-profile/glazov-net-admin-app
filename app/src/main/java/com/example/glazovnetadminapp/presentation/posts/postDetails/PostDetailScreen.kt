package com.example.glazovnetadminapp.presentation.posts.postDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.util.convertDaysOffsetToString
import com.example.glazovnetadminapp.presentation.posts.postsList.PostsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    navController: NavController,
    viewModel: PostsScreenViewModel
) {

    val post = viewModel.openedPostModel.collectAsState().value
    var showDeleteConfirmationDialog by remember {
        mutableStateOf(false)
    }
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior() //Поведение TopAppBar при прокрутке

    if (showDeleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deletePost(post!!)
                        showDeleteConfirmationDialog = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.app_button_dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteConfirmationDialog = false }
                ) {
                    Text(text = stringResource(id = R.string.app_button_dialog_cancel))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.app_post_delete_dialog_title))
            },
            text = {
                Text(text = stringResource(id = R.string.app_post_delete_dialog_text))
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection), //Связываем информацию о прокрутке со Scaffold
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_post_detail_screen_name))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    post?.let {
                        IconButton(onClick = {
                            viewModel.setPostToEdit(post)
                            navController.navigate("edit_post")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        }
                        IconButton(onClick = { showDeleteConfirmationDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
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
            if (post != null) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    softWrap = true,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
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
                    text = post.text,
                    style = MaterialTheme.typography.bodyMedium,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
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
                    )
                }
            } else {
                Text(
                    text = "no post found",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}