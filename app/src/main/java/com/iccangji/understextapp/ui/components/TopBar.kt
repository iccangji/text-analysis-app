package com.iccangji.understextapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = {
                onClickMenu()
            },
            modifier = Modifier.height(32.dp)
        ) {
            Icon(Icons.Default.Menu, contentDescription = "")
        }
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = {
              onClickInfo()
            },
            modifier = Modifier.height(32.dp)
        ) {
            Icon(Icons.Default.Info, contentDescription = "")
        }
    }
}