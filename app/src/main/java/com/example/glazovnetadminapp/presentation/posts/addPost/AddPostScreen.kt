package com.example.glazovnetadminapp.presentation.posts.addPost

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun AddPostScreen(
    navigator: DestinationsNavigator,
    viewModel: AddPostViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    var titleText by remember {
        mutableStateOf("")
    }
    var fullDescription by remember {
        mutableStateOf("")
    }
    var shortDescription by remember {
        mutableStateOf("")
    }
    var imageUrl by remember {
        mutableStateOf("")
    }
    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }
    var selectedPostTypeCode by remember {
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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_add_post_screen_name))
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
                onValueChange = { fullDescription = it },
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
                onValueChange = { imageUrl = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.add_post_screen_image_url)
                    )
                }
            )
            //TODO("image preview")
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {

                        fun clearInputData() {
                            titleText = ""
                            fullDescription = ""
                            shortDescription = ""
                            selectedPostTypeCode = 0
                            imageUrl = ""
                            isDropdownExpanded = false
                        }

                        clearInputData()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.app_button_clear_data)
                    )
                }
                Button(
                    onClick = {
                        viewModel.submitPost(
                            context = context,
                            title = titleText,
                            shortDescription = shortDescription,
                            fullDescription = fullDescription,
                            postType = selectedPostTypeCode,
                            imageUrl = imageUrl
                        )
                    },
                    enabled = (
                        titleText.isNotBlank()
                                && fullDescription.isNotBlank()
                                && viewModel.state.isLoading.not()
                    )
                ) {
                    Text(text = stringResource(id = R.string.add_post_screen_confirm_button))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(
                visible = viewModel.state.message !== null,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                Text(
                    text = viewModel.state.message ?: "",
                    color = if (viewModel.state.isError) Color.Red else LocalTextStyle.current.color
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddPostScreen() {
//    AddPostScreen(navigator = )
}