package com.example.indoorlocalizationcleancoders.components


import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource

import com.example.indoorlocalizationcleancoders.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderComponent(
    title: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back"
                )
            }
        },
        modifier = modifier,
    )
}

