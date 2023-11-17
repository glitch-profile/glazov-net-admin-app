package com.example.glazovnetadminapp.presentation.announcements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.models.posts.PostType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnnouncementScreen(
    navController: NavController,
    viewModel: AnnouncementsViewModel
) {
    var announcement = viewModel.announcementToEdit.collectAsState().value
    var title by remember {
        mutableStateOf(announcement?.title ?: "")
    }
    var text by remember {
        mutableStateOf(announcement?.text ?: "")
    }
    var filters by rememberSaveable {
        mutableStateOf(announcement?.filters ?: emptyList())
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
                        text = stringResource(id = R.string.app_add_announcement_screen_name)
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
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.edit_tariff_name_text)
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
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.edit_tariff_description_text)
                    )
                },
                supportingText = {
                    Text(
                        text = stringResource(id = R.string.app_text_field_required)
                    )
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))
            AddressSearchScreen(
                onTextChanged = { citySearch, streetSearch ->
                    viewModel.updateSearch(citySearch, streetSearch)

                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            FiltersScreen(
                filters = filters,
                onRemoveFilter = { filter ->
                    val newFilters = filters.toMutableList()
                    newFilters.remove(filter)
                    filters = newFilters
                }
            )
        }
    }
}

@Composable
private fun AddressSearchScreen(
    onTextChanged: (String, String) -> Unit
) {
    var city by remember {
        mutableStateOf("")
    }
    val cityList by remember {
        mutableStateOf(listOf("Glazov", "Glazov", "Glazov"))
    }
    var street by remember {
        mutableStateOf("")
    }
    var houseNumber by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                val isTextFocused = interactionSource.collectIsFocusedAsState()
                val focusManager = LocalFocusManager.current
                var textFieldSize by remember {
                    mutableStateOf(Size.Zero)
                }
                OutlinedTextField(
                    value = city,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    label = {
                        Text(
                            text = "Select city"
                        )
                    },
                    interactionSource = interactionSource
                )
                DropdownMenu(
                    expanded = isTextFocused.value,
                    onDismissRequest = {
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    cityList.forEachIndexed { index, cityString ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = cityString
                                )
                            },
                            onClick = {
                                city = cityString
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = street,
                onValueChange = {
                    street = it
                    onTextChanged.invoke(city, street)
                },
                label = {
                    Text(
                        text = "Street"
                    )
                },
                singleLine = true
            )
        }
    }
}

@Composable
private fun FiltersScreen(
    filters: List<AddressFilterElement>,
    onRemoveFilter: (AddressFilterElement) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        filters.forEach { filter ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    text = "${filter.city}, ${filter.street}, ${filter.houseNumber}",
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(
                    modifier = Modifier
                        .size(35.dp),
                    onClick = {
                        onRemoveFilter.invoke(filter)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "remove filter"
                    )
                }
            }
        }
    }
}