package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType

@Composable
fun TariffsList() {
    LazyColumn(
        content = {
            for (i in 0..10) {
                item {
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
                        cardMode = TariffCardState.SHOW
                    )
                }
            }
        }
    )

}