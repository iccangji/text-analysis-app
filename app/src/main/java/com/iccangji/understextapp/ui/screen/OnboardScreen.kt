package com.iccangji.understextapp.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iccangji.understextapp.R
import com.iccangji.understextapp.ui.theme.AppTheme

@Composable
fun OnboardScreen(
    modifier: Modifier = Modifier,
    setOnBoard: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(R.string.desc2_app),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.drawkit_grape_pack_illustration_6),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Button(
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    setOnBoard()
                },
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Text(text = stringResource(R.string.get_started))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OnboardScreenPreview(
    modifier: Modifier = Modifier
){
    AppTheme {
        OnboardScreen(
            setOnBoard = {},
        )
    }
}