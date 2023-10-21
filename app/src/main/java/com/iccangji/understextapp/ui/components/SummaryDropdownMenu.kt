package com.iccangji.understextapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.iccangji.understextapp.data.utils.SummaryLevel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryDropdownMenu(
    modifier: Modifier = Modifier,
    selectedOption: SummaryLevel,
    onSelectOption: (SummaryLevel) -> Unit
){
    val options = listOf(
        SummaryLevel.LOW,
        SummaryLevel.MEDIUM,
        SummaryLevel.HIGH,
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedOption.title,
            onValueChange = {  },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                disabledContainerColor = MaterialTheme.colorScheme.surfaceTint.copy(0.1f),
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            ),
            enabled = false
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = opt.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onSelectOption(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}