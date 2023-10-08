package com.example.glazovnetadminapp.presentation.posts.editPost

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.OffsetDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun EditPostScreen(
    postId: String,
    postTitle: String,
    postFullDescription: String,
    postCreationDate: OffsetDateTime,
    postShortDescription: String,
    postTypeCode: Int,
    postImageUrl: String = "",
    postImageWidth: Int? = null,
    postImageHeight: Int? = null,
    navigator: DestinationsNavigator,
    viewModel: EditPostViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    var titleText by remember {
        mutableStateOf(postTitle)
    }
    var fullDescription by remember {
        mutableStateOf(postFullDescription)
    }
    var shortDescription by remember {
        mutableStateOf(postShortDescription)
    }
    var imageUrl by remember {
        mutableStateOf(postImageUrl)
    }
    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }
    var selectedPostTypeCode by remember {
        mutableIntStateOf(postTypeCode)
    }
    val icon = if (isDropdownExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    var textFiledSize by remember {
        mutableStateOf(Size.Zero)
    }

    fun checkDataTheSame(): Boolean {
        return titleText == postTitle && fullDescription == postFullDescription
                && shortDescription == postShortDescription
                && imageUrl == postImageUrl
                && selectedPostTypeCode == postTypeCode
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_update_post_screen_name))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(values)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = titleText,
                onValueChange = { titleText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
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
                onValueChange = { fullDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
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
                    .padding(top = 4.dp)
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
            DropdownMenu( //TODO("Rework the dropdown menu")
                expanded = isDropdownExpanded,
                onDismissRequest = {
                    isDropdownExpanded = false
                },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
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
                onValueChange = { shortDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.add_post_screen_short_description)
                    )
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(id = R.string.add_post_screen_image_url)
                    )
                }
            )
            //TODO("image preview")
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {

                        fun clearInputData() {
                            titleText = postTitle
                            fullDescription = postFullDescription
                            shortDescription = postShortDescription
                            selectedPostTypeCode = postTypeCode
                            imageUrl = postImageUrl
                            isDropdownExpanded = false
                        }

                        clearInputData()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.app_button_reset_data)
                    )
                }
                Button(
                    onClick = {
                        viewModel.editPost(
                            context,
                            imageUrl !== postImageUrl,
                            postId,
                            titleText,
                            postCreationDate,
                            fullDescription,
                            shortDescription,
                            selectedPostTypeCode,
                            imageUrl,
                            postImageWidth,
                            postImageHeight
                        )
                    },
                    enabled = (
                            titleText.isNotBlank()
                                    && fullDescription.isNotBlank()
                                    && viewModel.state.isLoading.not()
                                    && checkDataTheSame().not()
                            )
                ) {
                    Text(text = stringResource(id = R.string.add_post_screen_confirm_button))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(
                visible = viewModel.state.message !== null,
                enter = slideInVertically()
            ) {
                Text(
                    text = viewModel.state.message ?: "",
                    color = if (viewModel.state.isError) Color.Red else LocalTextStyle.current.color
                )
            }
        }
    }
}