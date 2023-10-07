package com.example.glazovnetadminapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.presentation.destinations.AddPostScreenDestination
import com.example.glazovnetadminapp.presentation.destinations.PostsScreenDestination
import com.example.glazovnetadminapp.presentation.tariffs.tariffsList.TariffsList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize()
//    ) {
//        Text(
//            text = "Api.Glazov.Net",
//            style = MaterialTheme.typography.displayMedium
//        )
//        Text(
//            text = "Admin App",
//            style = MaterialTheme.typography.displaySmall
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        Divider(modifier = Modifier.fillMaxWidth())
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Text(
//            text = "Posts",
//            style = MaterialTheme.typography.displaySmall
//        )
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            OutlinedButton(
//                onClick = {
//                    navigator.navigate(PostsScreenDestination, onlyIfResumed = true)
//                },
//                modifier = Modifier
//                    .padding(5.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "All posts"
//                )
//            }
//            OutlinedButton(
//                onClick = {
//                    navigator.navigate(AddPostScreenDestination, onlyIfResumed = true)
//                },
//                modifier = Modifier
//                    .padding(5.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Add post"
//                )
//            }
//        }
//    }
    TariffsList()
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
//    HomeScreen()
}