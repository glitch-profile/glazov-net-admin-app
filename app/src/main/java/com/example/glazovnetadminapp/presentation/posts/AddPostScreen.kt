package com.example.glazovnetadminapp.presentation.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.posts.PostType

@Composable
fun AddPostScreen(
    modifier: Modifier = Modifier
) {
    var titleText by remember{
        mutableStateOf("")
    }
    var fullDescription by remember{
        mutableStateOf("")
    }
    var shortDescription by remember{
        mutableStateOf("")
    }
    var imageUrl by remember{
        mutableStateOf("")
    }
    var videoUrl by remember{
        mutableStateOf("")
    }
    var isDropdownExpanded by remember{
        mutableStateOf(false)
    }
    var selectedPostType by remember{
        mutableStateOf(PostType.News.description)
    }
    val icon = if (isDropdownExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    var textFiledSize by remember {
        mutableStateOf(Size.Zero)
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(id = R.string.add_post_screen_screen_name),
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = titleText,
            onValueChange = {titleText = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.add_post_screen_title)
                )
            },
            supportingText = {
                Text(
                    text = stringResource(id = R.string.app_text_field_required)
                )
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = fullDescription,
            onValueChange = {fullDescription = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.add_post_screen_full_description)
                )
            },
            supportingText = {
                Text(
                    text = stringResource(id = R.string.app_text_field_required)
                )
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = selectedPostType,
            onValueChange = {selectedPostType = it},
            readOnly = true,
            supportingText = {
                Text(
                    text = stringResource(id = R.string.app_text_field_required)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()
                },
            label = {
                Text(
                    text = "Select Post Type"
                )
            },
            trailingIcon = {
                Icon(
                    icon,
                    "",
                    Modifier.clickable { isDropdownExpanded = !isDropdownExpanded }
                )
            }
        )
        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = {
                isDropdownExpanded = false
            },
            modifier = Modifier
                .width(with(LocalDensity.current){ textFiledSize.width.toDp() })
            ) {
            DropdownMenuItem(
                text = {
                       Text(text = PostType.News.description)
                },
                onClick = {
                    selectedPostType = PostType.News.description
                    isDropdownExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = PostType.Announcement.description)
                },
                onClick = {
                    selectedPostType = PostType.Announcement.description
                    isDropdownExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = PostType.Greeting.description)
                },
                onClick = {
                    selectedPostType = PostType.Greeting.description
                    isDropdownExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = PostType.Advertisement.description)
                },
                onClick = {
                    selectedPostType = PostType.Advertisement.description
                    isDropdownExpanded = false
                }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = shortDescription,
            onValueChange = {shortDescription = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.add_post_screen_short_description)
                )
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = imageUrl,
            onValueChange = {imageUrl = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.add_post_screen_image_url)
                )
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = videoUrl,
            onValueChange = {videoUrl = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.add_post_screen_video_url)
                )
            }
        )
        //image preview
        //video preview
    }
}

@Preview(showBackground = true)
@Composable
fun previewAddPostScreen() {
    AddPostScreen()
}