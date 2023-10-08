package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
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
                        text = stringResource(id = R.string.tariff_screen_title)
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
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            var selectedCategoryIndex by remember{
                mutableIntStateOf(0)
            }
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState()),

            ) {
                Button(
                    onClick = { selectedCategoryIndex = 0 }
                ) {
                    Text(text = stringResource(id = R.string.tariff_type_unlimited))
                }
                Spacer(modifier = Modifier.width(24.dp))
                Button(
                    onClick = { selectedCategoryIndex = 1 }
                ) {
                    Text(text = stringResource(id = R.string.tariff_type_limited))
                }
                Spacer(modifier = Modifier.width(24.dp))
                Button(
                    onClick = { selectedCategoryIndex = 2 }
                ) {
                    Text(text = "Archive")
                }
            } //TODO("Add styles")
            FilterScreen(viewModel)
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

@Composable
private fun FilterScreen(
    viewModel: TariffsScreenViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        var isExpanded by remember {
            mutableStateOf(false)
        }
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = viewModel.nameFilter,
                    onValueChange = { viewModel.nameFilter = it },
                    singleLine = true,
                    label = {
                        Text(
                            text = "Tariffs name"
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
                text = if (isExpanded) "Hide filters"
                else "Show filters"
            )
        }
    }
}

@Composable
private fun TariffsCard(
    tariffType: TariffType,
    viewModel: TariffsScreenViewModel
) {
    val tariffs = viewModel.state.tariffsData.filter {
        it.category == tariffType && it.name.contains(viewModel.nameFilter)
    }
    if (tariffs.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
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
    } else {
        Text(
            text = "No tariffs found!"
        )
    }
}
