package com.example.glazovnetadminapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.presentation.destinations.AddPostScreenDestination
import com.example.glazovnetadminapp.presentation.destinations.PostsScreenDestination
import com.example.glazovnetadminapp.presentation.destinations.TariffsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
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
                    navigator.navigate(PostsScreenDestination, onlyIfResumed = true)
                }
            )
            MenuButton(
                text = stringResource(id = R.string.app_add_post_screen_name),
                onClick = {
                    navigator.navigate(AddPostScreenDestination, onlyIfResumed = true)
                }
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
                    navigator.navigate(TariffsScreenDestination, onlyIfResumed = true)
                }
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
                text = stringResource(id = R.string.menu_about_app_button_text),
                isEnable = false,
                onClick = {

                }
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
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 16.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable(enabled = isEnable, onClick = onClick)
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
        ) {
            Text(
                text = text,
                style = textStyle
            )
        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}