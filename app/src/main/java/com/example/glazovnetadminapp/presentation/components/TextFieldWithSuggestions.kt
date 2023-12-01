package com.example.glazovnetadminapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldWithCompleteSuggestions(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanges: (String) -> Unit,
    suggestions: List<String>,
    label: String,
    focusManager: FocusManager = LocalFocusManager.current,
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    Column(
        modifier = modifier
            .animateContentSize()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChanges.invoke(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {
                Text(
                    text = label
                )
            },
            singleLine = true,
            interactionSource = interactionSource
        )
        val isTextFieldFocused = interactionSource.collectIsFocusedAsState().value
        AnimatedVisibility(
            visible = isTextFieldFocused && suggestions.isNotEmpty()
        ) {
            Column(
                Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    content = {
                        items(
                            count = suggestions.size,
                            key = { it }
                        ) { index ->
                            Spacer(modifier = Modifier.width(16.dp))
                            SelectionChipButton(
                                text = suggestions[index],
                                onClick = {
                                    onTextChanges.invoke(suggestions[index])
                                    focusManager.clearFocus()
                                }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                )
            }
        }

    }
}

