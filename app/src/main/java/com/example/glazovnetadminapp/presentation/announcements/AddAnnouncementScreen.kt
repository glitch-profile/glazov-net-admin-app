package com.example.glazovnetadminapp.presentation.announcements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.presentation.ScreenState
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnnouncementScreen(
    navController: NavController,
    viewModel: AnnouncementsViewModel
) {
    val state = viewModel.announcementToEdit.collectAsState().value
    var title by remember {
        mutableStateOf(state.data.firstOrNull()?.title ?: "")
    }
    var text by remember {
        mutableStateOf(state.data.firstOrNull()?.text ?: "")
    }
    val citiesList = viewModel.citiesList.collectAsState().value
    val addresses = viewModel.addressesState.collectAsState().value

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 4.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.add_announcement_title_placeholer)
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
                        .padding(horizontal = 16.dp)
                        .padding(top = 4.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.add_announcement_text_placeholder)
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
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    citiesList = citiesList,
                    onTextChanged = { citySearch, streetSearch ->
                        viewModel.updateSearch(citySearch, streetSearch)

                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                AddressesScreen(
                    addresses = addresses.data,
                    onSelectionChange = { addressElement ->
                        viewModel.changeSelectionOfAddressElement(addressElement)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            val selectedAddresses = viewModel.selectedAddresses.collectAsState().value
            val bottomBarCornerRadius = animateDpAsState(
                targetValue = if(selectedAddresses.isNotEmpty()) 16.dp else 0.dp,
                label = "bottomBarCornerRadius"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        shape = RoundedCornerShape(
                            topStart = bottomBarCornerRadius.value,
                            topEnd = bottomBarCornerRadius.value
                        )
                    )
            ) {
                Column {
                    AnimatedVisibility(visible = selectedAddresses.isNotEmpty()) {
                        Column {
                            val clampedSelectedAddressesCount = max(selectedAddresses.size, 1)
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                text = pluralStringResource(
                                    id = R.plurals.add_announcement_addresses_counter,
                                    count = clampedSelectedAddressesCount,
                                    formatArgs = arrayOf(clampedSelectedAddressesCount)
                                ),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Divider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.clearSelectedAddresses()
                            }
                        ) {
                            Text(text = stringResource(id = R.string.add_announcement_clear_filters_button))
                        }
                        Button(
                            onClick = {
                                viewModel.createAnnouncement(title, text) { result ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            if (result) "Announcement added"
                                            else "Error occurred"
                                        )
                                    }
                                }
                            },
                            enabled = title.isNotBlank() && text.isNotBlank() && !state.isLoading
                        ) {
                            Text(text = stringResource(id = R.string.app_button_dialog_confirm))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressSearchScreen(
    modifier: Modifier = Modifier,
    citiesList: ScreenState<String>,
    onTextChanged: (String, String) -> Unit
) {
    var city by remember {
        mutableStateOf("")
    }
    var street by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
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
                            text = stringResource(id = R.string.add_announcement_city_placeholder)
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
                    if (citiesList.isLoading) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.app_text_loading)
                                )
                            },
                            onClick = {
                                focusManager.clearFocus()
                            },
                            enabled = false
                        )
                    } else {
                        if (citiesList.data.isNotEmpty()) {
                            citiesList.data.forEach { cityString ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = cityString
                                        )
                                    },
                                    onClick = {
                                        city = cityString
                                        onTextChanged.invoke(city, street)
                                        focusManager.clearFocus()
                                    }
                                )
                            }
                        } else {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = citiesList.message.toString()
                                    )
                                },
                                onClick = {
                                    focusManager.clearFocus()
                                },
                                enabled = false
                            )
                        }
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
                        text = stringResource(id = R.string.add_announcement_street_placeholder)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            street = ""
                            onTextChanged.invoke(city, street)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "clear"
                        )
                    }
                },
                singleLine = true
            )
        }
    }
}

@Composable
private fun AddressesScreen(
    addresses: List<AddressFilterElement>,
    onSelectionChange: (AddressFilterElement) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        AnimatedVisibility(visible = addresses.isEmpty()) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = stringResource(id = R.string.add_announcement_instruction_text),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        addresses.forEach { addressElement ->
            val backgroundColor by animateColorAsState(
                if (addressElement.isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.background,
                label = "AddressRowBackgroundColor"
            )
            Row(
                modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .clickable {
                        onSelectionChange.invoke(addressElement)
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "${addressElement.city}, ${addressElement.street}, ${addressElement.houseNumber}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}