package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun TariffsScreen(
    viewModel: TariffsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
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
                            navigator.popBackStack()
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
                        onClick = { /*TODO*/ }
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
            LazyRow(
                content = {
                    items(TariffType.values().size) { index ->
                        val backgroundColor by animateColorAsState(
                            if (selectedCategoryIndex == index) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primaryContainer,
                            label = "color"
                        )
                        val textColor by animateColorAsState(
                            if (selectedCategoryIndex == index) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onPrimaryContainer,
                            label = "color"
                        )
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    selectedCategoryIndex = index
                                }
                                .clip(RoundedCornerShape(10.dp))
                                .widthIn(min = 100.dp)
                                .background(backgroundColor)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = TariffType.values()[index].stringResourceId),
                                style = MaterialTheme.typography.bodyMedium,
                                color = textColor
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                }
            )
            FilterScreen(viewModel)
            if (viewModel.state.collectAsState().value.isLoading) {
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
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                ) {
                    when (selectedCategoryIndex) {
                        0 -> {
                            TariffsCard(
                                TariffType.Unlimited,
                                viewModel
                            )
                        }

                        1 -> {
                            TariffsCard(
                                TariffType.Limited,
                                viewModel
                            )
                        }

                        2 -> {
                            TariffsCard(
                                TariffType.Archive,
                                viewModel
                            )
                        }

                        else -> {
                            TariffsCard(
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

@Composable
private fun FilterScreen(
    viewModel: TariffsScreenViewModel
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
        val nameFilterText = viewModel.nameFilter.collectAsState()
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = nameFilterText.value,
                    onValueChange = { viewModel.updateNameFilter(it) },
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
                text = if (isExpanded) stringResource(id = R.string.app_hide_filters_button_text)
                else stringResource(id = R.string.app_show_filters_button_text)
            )
        }
    }
}

@Composable
private fun TariffsCard(
    tariffType: TariffType,
    viewModel: TariffsScreenViewModel
) {
    val tariffs = viewModel.filteredTariffs.collectAsState().value.filter {
        it.category == tariffType
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = tariffType.stringResourceId),
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = tariffType.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (tariffs.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.tariffs_not_found_text)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    content = {
                        items(
                            items = tariffs,
                            key = { it.id }
                        ) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleLarge
                            )
                            it.description?.let { description ->
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Text(
                                text = stringResource(id = R.string.tariff_speed_prefix) +
                                        " ${it.maxSpeed} " + stringResource(id = R.string.tariff_speed_suffix),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = stringResource(id = R.string.tariff_cost_prefix) + " " + pluralStringResource(
                                    id = R.plurals.tariff_cost_rubbles,
                                    count = it.costPerMonth,
                                    formatArgs = arrayOf(it.costPerMonth)
                                ),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                )
            }
        }
    }
}
