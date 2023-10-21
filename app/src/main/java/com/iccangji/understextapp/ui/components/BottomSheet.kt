package com.iccangji.understextapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    @StringRes title: Int,
    @StringRes desc: Int
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            dismissRequest()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 24.dp,
                    bottom = 60.dp
                )
                .fillMaxWidth()
        ){
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = desc))
        }
    }
}
