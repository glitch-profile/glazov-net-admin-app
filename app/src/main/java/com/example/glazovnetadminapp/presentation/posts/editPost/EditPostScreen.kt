package com.example.glazovnetadminapp.presentation.posts.editPost

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.example.glazovnetadminapp.domain.models.posts.PostType.Companion.toPostTypeCode
import com.example.glazovnetadminapp.presentation.posts.postsList.PostsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPostScreen(
    navController: NavController,
    viewModel: PostsScreenViewModel,
    context: Context = LocalContext.current
) {
    val state = viewModel.editPostState.collectAsState().value
    val post = state.post
    var titleText by remember {
        mutableStateOf(post?.title ?: "")
    }
    var fullDescription by remember {
        mutableStateOf(post?.text ?: "")
    }
    var imageUri by remember {
        mutableStateOf(post?.image?.imageUrl ?: "")
    }
    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }
    var selectedPostTypeCode by remember {
        mutableIntStateOf(post?.postType?.toPostTypeCode() ?: 0)
    }
    val icon = if (isDropdownExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it?.toString() ?: imageUri
    }

    fun checkDataTheSame(): Boolean {
        return ((titleText == post?.title) && (fullDescription == post.text)
                    && (imageUri == (post.image?.imageUrl ?: ""))
                    && (selectedPostTypeCode == post.postType.toPostTypeCode()))
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (post == null) stringResource(id = R.string.app_add_post_screen_name)
                        else stringResource(id = R.string.app_edit_post_screen_name)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
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
                            textFieldSize = coordinates.size.toSize()
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
                            modifier = Modifier.clickable {
                                isDropdownExpanded = !isDropdownExpanded
                            }
                        )
                    }
                )
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = {
                        isDropdownExpanded = false
                    },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    PostType.values().forEachIndexed { index, postType ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = postType.stringResourceId)
                                )
                            },
                            onClick = {
                                selectedPostTypeCode = index
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { imageUri = "" }
                ) {
                    Text(text = "Remove image")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    onClick = { launcher.launch("image/*") }
                ) {
                    Text(text = "Select image")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (imageUri.isNotBlank()) {
                    "Image - $imageUri"
                } else {
                    "No image attached"
                },
                maxLines = 2,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {

                        fun clearInputData() {
                            titleText = post?.title ?: ""
                            fullDescription = post?.text ?: ""
                            selectedPostTypeCode = post?.postType?.toPostTypeCode() ?: 0
                            imageUri = post?.image?.imageUrl ?: ""
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
                        if (post == null) {
                            viewModel.addNewPost(
                                context,
                                titleText,
                                fullDescription,
                                selectedPostTypeCode,
                                imageUri
                            )
                        } else {
                            viewModel.editPost(
                                context,
                                titleText,
                                fullDescription,
                                selectedPostTypeCode,
                                imageUri,
                            )
                        }
                    },
                    enabled = (
                            titleText.isNotBlank()
                                    && fullDescription.isNotBlank()
                                    && state.isLoading.not()
                                    && checkDataTheSame().not()
                            )
                ) {
                    Text(text = stringResource(id = R.string.add_post_screen_confirm_button))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(
                visible = state.message !== null,
                enter = slideInVertically()
            ) {
                Text(
                    text = state.message ?: "",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}