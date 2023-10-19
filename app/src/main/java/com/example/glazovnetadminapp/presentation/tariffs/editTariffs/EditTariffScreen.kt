package com.example.glazovnetadminapp.presentation.tariffs.editTariffs

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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType.Companion.toTariffTypeCode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTariffScreen(
    tariffId: String? = null,
    tariffName: String = "",
    tariffDescription: String = "",
    tariffType: TariffType = TariffType.Unlimited,
    maxSpeed: Int = 0,
    costPerMonth: Int = 0
) {
    var name by remember{
        mutableStateOf(tariffName)
    }
    var description by remember{
        mutableStateOf(tariffDescription)
    }
    var tariffTypeCode by remember{
        mutableIntStateOf(tariffType.toTariffTypeCode())
    }
    var speed by remember{
        mutableIntStateOf(maxSpeed)
    }
    var cost by remember{
        mutableIntStateOf(costPerMonth)
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
        return name == tariffName && description == tariffDescription
                && tariffTypeCode == tariffType.toTariffTypeCode()
                && speed == maxSpeed
                && cost == costPerMonth
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
                        text = if (tariffId == null) stringResource(id = R.string.app_add_tariff_screen_name)
                        else stringResource(id = R.string.app_edit_tariff_screen_name)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            //navigator.popBackStack() TODO
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
                value = description,
                onValueChange = { description = it },
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
                        .width(150.dp) ,
                    label = {
                        Text(
                            text = "Max speed"
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
                        .width(150.dp) ,
                    label = {
                        Text(
                            text = "Cost per month"
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
                            name = tariffName
                            description = tariffDescription
                            tariffTypeCode = tariffType.toTariffTypeCode()
                            speed = maxSpeed
                            cost = costPerMonth
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

                    },
                    enabled = (
                            name.isNotBlank()
                                    && description.isNotBlank()
                                    //&& viewModel.state.isLoading.not()
                                    && checkDataTheSame().not()
                            )
                ) {
                    Text(text = stringResource(id = R.string.add_post_screen_confirm_button))
                }
            }
        }
    }
}