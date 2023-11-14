package com.example.glazovnetadminapp.presentation.announcements

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement

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
                onAddFilter = { filter ->
                    val newFiltersList = filters.toMutableList()
                    newFiltersList.add(filter)
                    filters = newFiltersList.sortedBy {
                        "${it.city}${it.street}${it.houseNumber / 1000f}"
                    }
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
    onAddFilter: (AddressFilterElement) -> Unit = {}
) {
    var city by remember {
        mutableStateOf("")
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
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.4f),
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    Text(
                        text = "City"
                    )
                },
                singleLine = true
            )
//            TextFieldWithCompletionDropdown(
//                onTextChanges = {},
//                label = "city",
//                dropdownElements = listOf("123", "345", "678", "343", "334", "3434")
//            )
            Spacer(modifier = Modifier.width(4.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = street,
                onValueChange = {
                    street = it
                },
                label = {
                    Text(
                        text = "Street"
                    )
                },
                singleLine = true
            )
        }
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            TextButton(
//                onClick = {
//                    city = ""
//                    street = ""
//                    houseNumber = ""
//                }
//            ) {
//                Text(text = "Clear all")
//            }
//            Button(
//                onClick = {
//                    onAddFilter.invoke(
//                        AddressFilterElement(
//                            city = city,
//                            street = street,
//                            houseNumber = houseNumber.toInt()
//                        )
//                    )
//                }
//            ) {
//                Text(text = "Add filter")
//            }
//        }

    }
}

//@Composable
//private fun TextFieldWithCompletionDropdown(
//    modifier: Modifier = Modifier,
//    defaultTextFieldValue: String = "",
//    onTextChanges: (String) -> Unit,
//    onTextComplete: (String) -> Unit = {},
//    isEnabled: Boolean = true,
//    label: String,
//    singleLine: Boolean = true,
//    dropdownElements: List<String>
//) {
//    var textFieldSize by remember {
//        mutableStateOf(Size.Zero)
//    }
//    var textFieldValue by remember {
//        mutableStateOf(defaultTextFieldValue)
//    }
//    var isDropdownExpanded by remember {
//        mutableStateOf(false)
//    }
//    val interactionSource = remember { MutableInteractionSource() }
//    val isFocused = interactionSource.collectIsFocusedAsState().value
//    val focusManager = LocalFocusManager.current
//
//    Column(
//        modifier = modifier
//    ) {
//        OutlinedTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    textFieldSize = coordinates.size.toSize()
//                } ,
//            value = textFieldValue,
//            onValueChange = {
//                textFieldValue = it
//                onTextChanges.invoke(it)
//            },
//            label = {
//                Text(text = label)
//            },
//            singleLine = singleLine,
//            readOnly = !isEnabled,
//            interactionSource = interactionSource
//        )
//        DropdownMenu(
//            modifier = Modifier
//                .width(with(LocalDensity.current) {textFieldSize.width.toDp()} ),
//            expanded = isFocused && isEnabled,
//            onDismissRequest = {},
//            properties = PopupProperties(
//                focusable = false
//            )
//        ) {
//            dropdownElements.forEach { element ->
//                DropdownMenuItem(
//                    text = {
//                           Text(text = element)
//                    },
//                    onClick = {
//                        textFieldValue = element
//                        onTextComplete.invoke(element)
//                        focusManager.clearFocus()
//                    }
//                )
//            }
//        }
//    }
//}

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