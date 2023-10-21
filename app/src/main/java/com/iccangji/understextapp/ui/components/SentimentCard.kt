package com.iccangji.understextapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SentimentCard(
    modifier: Modifier = Modifier,
    value: Int,
    @StringRes title: Int,
    @DrawableRes image: Int
){
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(140.dp),
                contentScale = ContentScale.FillHeight,
                painter = painterResource(id = image),
                contentDescription = stringResource(id = title)
            )
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "${value}%",
            )
        }
    }
}