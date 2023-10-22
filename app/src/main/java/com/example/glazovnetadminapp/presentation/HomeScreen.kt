package com.example.glazovnetadminapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
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
        ) {
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