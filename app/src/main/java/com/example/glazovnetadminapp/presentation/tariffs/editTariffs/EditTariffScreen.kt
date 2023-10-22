package com.example.glazovnetadminapp.presentation.tariffs.editTariffs

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType.Companion.toTariffTypeCode
import com.example.glazovnetadminapp.presentation.tariffs.tariffsList.TariffsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTariffScreen(
    navController: NavController,
    viewModel: TariffsScreenViewModel
) {
    val state = viewModel.editTariffState.collectAsState().value
    val tariff = state.tariff

    var name by remember{
        mutableStateOf(tariff?.name ?: "")
    }
    var description by remember{
        mutableStateOf(tariff?.description ?: "")
    }
    var tariffTypeCode by remember{
        mutableIntStateOf(tariff?.category?.toTariffTypeCode() ?: 0)
    }
    var speed by remember{
        mutableIntStateOf(tariff?.maxSpeed ?: 0)
    }
    var cost by remember{
        mutableIntStateOf(tariff?.costPerMonth ?: 0)
    }
    var isDropdownExpanded by remember {
        mutableStateOf(false)
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
        return ((name == tariff?.name) && (description == (tariff.description ?: ""))
                && (tariffTypeCode == tariff.category.toTariffTypeCode())
                && (speed == tariff.maxSpeed)
                && (cost == tariff.costPerMonth))
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
                        text = if (tariff == null) stringResource(id = R.string.app_add_tariff_screen_name)
                        else stringResource(id = R.string.app_edit_tariff_screen_name)
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
                value = name,
                onValueChange = { name = it },
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
                value = description,
                onValueChange = { description = it },
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = stringResource(id = TariffType.fromTariffTypeCode(tariffTypeCode).stringResourceId),
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
                            text = stringResource(id = R.string.edit_tariff_category_text)
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
                        .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
                ) {
                    TariffType.values().forEachIndexed { index, postType ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = postType.stringResourceId)
                                )
                            },
                            onClick = {
                                tariffTypeCode = index
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
                OutlinedTextField(
                    value = speed.toString(),
                    onValueChange = { speed = it.filter { it.isDigit() }.toIntOrNull() ?: 0 },
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(170.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.edit_tariff_max_speed_text)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = cost.toString(),
                    onValueChange = { cost = it.filter { it.isDigit() }.toIntOrNull() ?: 0 },
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(170.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.edit_tariff_cost_text)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {

                        fun clearInputData() {
                            name = tariff?.name ?: ""
                            description = tariff?.description ?: ""
                            tariffTypeCode = tariff?.category?.toTariffTypeCode() ?: 0
                            speed = tariff?.maxSpeed ?: 0
                            cost = tariff?.costPerMonth ?: 0
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
                        if (tariff == null) {
                            viewModel.addTariff(
                                TariffModel(
                                    id = "",
                                    name = name,
                                    description = description.ifBlank { null },
                                    category = TariffType.fromTariffTypeCode(tariffTypeCode),
                                    maxSpeed = speed,
                                    costPerMonth = cost
                                )
                            )
                        } else {
                            viewModel.updateTariff(
                                TariffModel(
                                    id = tariff.id,
                                    name = name,
                                    description = description.ifBlank { null },
                                    category = TariffType.fromTariffTypeCode(tariffTypeCode),
                                    maxSpeed = speed,
                                    costPerMonth = cost
                                )
                            )
                        }
                    },
                    enabled = (
                            name.isNotBlank()
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