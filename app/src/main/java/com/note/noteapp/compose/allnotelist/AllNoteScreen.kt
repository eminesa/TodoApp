package com.note.noteapp.compose.allnotelist

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
 import androidx.compose.ui.unit.dp
 import com.note.noteapp.R


@Composable
fun AllNoteScreen(
    modifier: Modifier = Modifier,
    onAddNoteClick: () -> Unit = {},
 ) {

    EmptyGarden(onAddNoteClick, modifier)

}


@Composable
private fun EmptyGarden(onAddNoteClick: () -> Unit, modifier: Modifier = Modifier) {
    // Calls reportFullyDrawn when this composable is composed.
    ReportDrawn()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "This screen will show all notes")

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 12.dp,
                bottomStart = 12.dp,
                bottomEnd = 0.dp,
            ),
            onClick = onAddNoteClick
        ) {
            Text(
                color = MaterialTheme.colors.primary,
                text = stringResource(id = R.string.add_note)
            )
        }
    }
}

