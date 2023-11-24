package com.example.glazovnetadminapp.presentation.announcements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.util.convertDaysOffsetToString

@Composable
fun AnnouncementCard(
    modifier: Modifier = Modifier,
    announcement: AnnouncementModel,
    onDeleteAnnouncement: (AnnouncementModel) -> Unit,
) {
    var isOptionsButtonsExpanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { isOptionsButtonsExpanded = !isOptionsButtonsExpanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = announcement.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${announcement.creationDate!!.convertDaysOffsetToString()}, ID: ${announcement.id}", //Конвертируем к локальному времени
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = announcement.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Marking a ${announcement.filters.size} addresses", //TODO:Rework the addresses text
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            //TODO:Rework the buttons. Implement it into card, add delete confirmation
            AnimatedVisibility(visible = isOptionsButtonsExpanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            onDeleteAnnouncement.invoke(announcement)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_delete_button),
                            color = Color.Red
                        )
                    }
                    TextButton(
                        enabled = false,
                        onClick = {

                        }
                    ) {
                        Text(
                            text = "Use as base"
                        )
                    }
                }
            }
        }
    }
}