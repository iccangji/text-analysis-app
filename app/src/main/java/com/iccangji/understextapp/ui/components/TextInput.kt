package com.iccangji.understextapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onTextChange: (String) -> Unit
){
    BoxWithConstraints(
        modifier = modifier.padding(0.dp)
    ) {
        val inputModifier = if(maxWidth >= 400.dp) Modifier.fillMaxWidth().height(120.dp) else Modifier.fillMaxWidth().height(300.dp)
        val containerColor = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.1f)
        TextField(
            value = textValue,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(text = "Insert your text...")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
            ),
            modifier = inputModifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large,
                )
        )
    }
}