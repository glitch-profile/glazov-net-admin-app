package com.example.glazovnetadminapp.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                }
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .fillMaxWidth()
                .animateContentSize()
        ) {
            if (viewModel.isApiKeyEmpty) {
                WarningCard(
                    navController = navController,
                    title = "API key not found",
                    description = stringResource(id = R.string.settings_api_key_description),
                    screenRouteToOpen = "settings"
                )
            }
            Text(
                text = stringResource(id = R.string.menu_posts_title_text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MenuButton(
                text = stringResource(id = R.string.app_posts_screen_name),
                onClick = {
                    navController.navigate("posts")
                },
                iconVector = Icons.Default.DateRange
            )
            MenuButton(
                text = stringResource(id = R.string.app_add_post_screen_name),
                onClick = {
                    navController.navigate("edit_post")
                },
                iconVector = Icons.Default.Add
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.menu_tariffs_title_text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MenuButton(
                text = stringResource(id = R.string.app_tariffs_list_screen_name),
                onClick = {
                    navController.navigate("tariffs")
                },
                iconVector = Icons.Default.List
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.menu_details_title_text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MenuButton(
                text = stringResource(id = R.string.app_settings_screen_name),
                onClick = {
                    navController.navigate("settings")
                },
                iconVector = Icons.Default.Settings
            )
        }
    }
}

@Composable
private fun MenuButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
    iconVector: ImageVector? = null,
    iconDescription: String = "",
    iconTint: Color = MaterialTheme.colorScheme.primary,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 16.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable(enabled = isEnable, onClick = onClick)
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
        ) {
            if (iconVector !== null) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = iconDescription,
                    tint = iconTint
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
            Text(
                text = text,
                style = textStyle
            )
        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun WarningCard(
    navController: NavController,
    title: String,
    description: String,
    screenRouteToOpen: String
) {
    var isNeedToShowCard by rememberSaveable {
        mutableStateOf(true)
    }
    AnimatedVisibility(visible = isNeedToShowCard) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            isNeedToShowCard = false
                        }
                    ) {
                        Text(
                            text = "Close"
                        )
                    }
                    TextButton(
                        onClick = {
                            navController.navigate(screenRouteToOpen)
                            isNeedToShowCard = false
                        }
                    ) {
                        Text(
                            text = "Fix now"
                        )
                    }
                }
            }
        }
    }
}