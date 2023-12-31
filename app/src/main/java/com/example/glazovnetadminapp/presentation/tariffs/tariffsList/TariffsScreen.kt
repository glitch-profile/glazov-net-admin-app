package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType
import com.example.glazovnetadminapp.presentation.components.RequestErrorScreen
import com.example.glazovnetadminapp.presentation.components.SelectionChipButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TariffsScreen(
    navController: NavController,
    viewModel: TariffsScreenViewModel,
) {

    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_tariffs_list_screen_name)
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
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.setTariffToEdit(null)
                            navController.navigate("edit_tariff")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add tariff"
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.loadTariffs()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh tariffs"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            var selectedCategoryIndex by remember {
                mutableIntStateOf(0)
            }
            val state = viewModel.state.collectAsState()
            val filteredTariffs = viewModel.filteredTariffs.collectAsState()
            LazyRow(
                content = {
                    items(TariffType.values().size) { index ->
                        Spacer(modifier = Modifier.width(16.dp))
                        SelectionChipButton(
                            text = stringResource(id = TariffType.values()[index].stringResourceId),
                            onClick = { selectedCategoryIndex = index },
                            isSelected = selectedCategoryIndex == index
                        )
                    }
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                }
            )
            FilterScreen(
                onTariffNameTextChanging = {filterString ->
                    viewModel.updateNameFilter(filterString)
                }
            )
            if (state.value.isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.app_text_loading),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                if (state.value.message != null) {
                    RequestErrorScreen(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize(),
                        errorText = state.value.message.toString(),
                        onActionButtonClick = {
                            viewModel.loadTariffs()
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                    ) {
                        when (selectedCategoryIndex) {
                            0 -> {
                                TariffsCard(
                                    navController,
                                    filteredTariffs.value,
                                    TariffType.Unlimited,
                                    viewModel
                                )
                            }

                            1 -> {
                                TariffsCard(
                                    navController,
                                    filteredTariffs.value,
                                    TariffType.Limited,
                                    viewModel
                                )
                            }

                            2 -> {
                                TariffsCard(
                                    navController,
                                    filteredTariffs.value,
                                    TariffType.Archive,
                                    viewModel
                                )
                            }

                            else -> {
                                TariffsCard(
                                    navController,
                                    filteredTariffs.value,
                                    TariffType.Unlimited,
                                    viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterScreen(
    onTariffNameTextChanging: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .animateContentSize()
    ) {
        var isExpanded by remember {
            mutableStateOf(false)
        }
        var nameFilterText by remember{
            mutableStateOf("")
        }
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = nameFilterText,
                    onValueChange = {
                        nameFilterText = it
                        onTariffNameTextChanging.invoke(it)
                    },
                    singleLine = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.tariff_name_filter_placeholder)
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
        TextButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = if (isExpanded) stringResource(id = R.string.app_hide_filters_button)
                else stringResource(id = R.string.app_show_filters_button)
            )
        }
    }
}

@Composable
private fun TariffsCard(
    navController: NavController,
    tariffs: List<TariffModel>,
    tariffType: TariffType,
    viewModel: TariffsScreenViewModel
) {
    val filteredTariffs = remember(tariffs) {
        tariffs.filter { it.category == tariffType }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .animateContentSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            if (filteredTariffs.isEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = stringResource(id = R.string.tariffs_not_found_text),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyColumn(
                    content = {
                        items(
                            items = filteredTariffs,
                            key = { it.id }
                        ) {tariff ->
                            var isOptionsButtonsExpanded by remember {
                                mutableStateOf(false)
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        isOptionsButtonsExpanded = !isOptionsButtonsExpanded
                                    }
                                    .padding(horizontal = 8.dp)
                            ) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = tariff.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                                tariff.description?.let { description ->
                                    Text(
                                        text = description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.tariff_speed_prefix) +
                                            " ${tariff.maxSpeed} " + stringResource(id = R.string.tariff_speed_suffix),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                                Text(
                                    text = stringResource(id = R.string.tariff_cost_prefix) + " " + pluralStringResource(
                                        id = R.plurals.tariff_cost_rubbles,
                                        count = tariff.costPerMonth,
                                        formatArgs = arrayOf(tariff.costPerMonth)
                                    ),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                                AnimatedVisibility(visible = isOptionsButtonsExpanded) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        TextButton(
                                            onClick = {
                                                viewModel.removeTariff(tariff)
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.app_delete_button),
                                                color = Color.Red
                                            )
                                        }
                                        TextButton(
                                            onClick = {
                                                viewModel.setTariffToEdit(tariff)
                                                navController.navigate("edit_tariff")
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.app_edit_button)
                                            )
                                        }
                                        if (tariff.category !== TariffType.Archive) {
                                            TextButton(
                                                onClick = {
                                                    viewModel.updateTariff(
                                                        tariff.copy(
                                                            category = TariffType.Archive
                                                        )
                                                    )
                                                }
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.tariffs_move_to_archive_button)
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}