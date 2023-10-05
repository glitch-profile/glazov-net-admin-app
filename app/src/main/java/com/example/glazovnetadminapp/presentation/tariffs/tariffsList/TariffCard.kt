package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TariffCard(
    tariffModel: TariffModel?,
    modifier: Modifier = Modifier,
    cardMode: TariffCardState = TariffCardState.SHOW
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    tariffModel?.let { tariff ->
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .animateContentSize(),
            onClick = {
            isExpanded = !isExpanded
            }
        ) {
            var name by remember{
                mutableStateOf(tariff.name)
            }
            var description by remember{
                mutableStateOf(tariff.description)
            }
            var speed by remember{
                mutableIntStateOf(tariff.maxSpeed)
            }
            var cost by remember{
                mutableIntStateOf(tariff.costPerMonth)
            }
            var mode by remember{
                mutableStateOf(cardMode)
            }

            when (mode) {
                TariffCardState.SHOW -> ShowTariff(
                    name = name,
                    description = description,
                    speed = speed,
                    cost = cost
                )
                TariffCardState.EDIT -> EditTariff(
                    name = name,
                    description = description,
                    speed = speed,
                    cost = cost
                )
                TariffCardState.NEW -> TODO()
            }

            AnimatedVisibility(visible = isExpanded) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            mode = TariffCardState.EDIT
                        },
                    ) {
                        Text(text = "Edit")
                    }
                    Button(
                        onClick = {
                            mode = TariffCardState.SHOW
                        },
                    ) {
                        Text(text = "Save")
                    }
                    Button(
                        onClick = {
                            mode = TariffCardState.SHOW
                        },
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun ShowTariff(
    name: String,
    description: String?,
    speed: Int,
    cost: Int
) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Divider(
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Max speed:",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$speed Мбит/с",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Cost per month:",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$cost Рублей",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Composable
fun EditTariff(
    name: String,
    description: String?,
    speed: Int,
    cost: Int
) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Tariff name"
                )
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Divider(
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = description ?: "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Tariff description"
                )
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Max speed:",
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedTextField(
                    value = speed.toString(),
                    onValueChange = {},
                    suffix = {
                        Text(
                            text = "Мбит/с"
                        )
                    },
                    modifier = Modifier.width(150.dp),
                    label = {
                        Text(
                            text = "Speed"
                        )
                    }
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Cost per month:",
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedTextField(
                    value = cost.toString(),
                    onValueChange = {},
                    suffix = {
                        Text(
                            text = "Рублей"
                        )
                    },
                    modifier = Modifier.width(150.dp),
                    label = {
                        Text(
                            text = "Cost"
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTariffCard() {
    TariffCard(
        tariffModel = TariffModel(
            id = "1234567890",
            name = "Like-100",
            description = null,
            category = TariffType.Unlimited,
            maxSpeed = 100,
            costPerMonth = 600,
            isActive = true
        ),
        cardMode = TariffCardState.EDIT
    )
}