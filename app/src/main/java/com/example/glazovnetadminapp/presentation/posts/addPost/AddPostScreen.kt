package com.example.glazovnetadminapp.presentation.posts.addPost

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
    var selectedPostTypeCode by remember{
        mutableIntStateOf(0)
    }
    val icon = if (isDropdownExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    var textFiledSize by remember {
        mutableStateOf(Size.Zero)
    }
    val viewModel: AddPostViewModel = viewModel()
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
            value = stringResource(id = PostType.fromPostTypeCode(selectedPostTypeCode).stringResourceId),
            onValueChange = {},
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
                    text = stringResource(id = R.string.add_post_screen_select_post_type)
                )
            },
            trailingIcon = {
                Icon(
                    icon,
                    "",
                    modifier = Modifier.clickable { isDropdownExpanded = !isDropdownExpanded }
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
            for (index in 0..3) {
                DropdownMenuItem(
                    text = {
                           Text(
                               text = stringResource(id = PostType.fromPostTypeCode(index).stringResourceId)
                           )
                    },
                    onClick = {
                        selectedPostTypeCode = index
                        isDropdownExpanded = false
                    }
                )
            }
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
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {
                viewModel.submitPost(
                    title = titleText,
                    shortDescription = shortDescription,
                    fullDescription = fullDescription,
                    postType = selectedPostTypeCode,
                    imageUrl = imageUrl,
                    videoUrl = videoUrl
                )
            },
            enabled = (titleText.isNotBlank() && fullDescription.isNotBlank())
        ) {
            Text(text = stringResource(id = R.string.add_post_screen_confirm_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddPostScreen() {
    AddPostScreen()
}