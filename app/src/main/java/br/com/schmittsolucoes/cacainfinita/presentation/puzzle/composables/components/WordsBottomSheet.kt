package br.com.schmittsolucoes.cacainfinita.presentation.puzzle.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Direction
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.presentation.theme.SecondaryTextColor
import br.com.schmittsolucoes.cacainfinita.presentation.theme.WordSelectionColor
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsBottomSheet(
    words: List<Word>,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(R.string.puzzle_words_bottom_sheet_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(words) { word ->
                    WordItem(word = word)
                }
            }
        }
    }
}

@Composable
private fun WordItem(word: Word) {
    val isFound = word.foundDate != null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isFound) {
            Icon(
                painter = painterResource(R.drawable.ic_checked_filled_rounded_20dp),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = WordSelectionColor
            )
        } else {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .border(1.dp, SecondaryTextColor, CircleShape)
            )
        }
        Text(
            text = word.text,
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = if (isFound) TextDecoration.LineThrough else TextDecoration.None
            ),
            color = SecondaryTextColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WordsBottomSheetPreview() {
    val words = listOf(
        Word("1", "p1", "KOTLIN", 0, 0, Direction.HORIZONTAL, Instant.now()),
        Word("2", "p1", "COMPOSE", 1, 0, Direction.VERTICAL),
        Word("3", "p1", "ANDROID", 2, 0, Direction.DIAGONAL_DOWN_RIGHT),
        Word("4", "p1", "ROOM", 3, 0, Direction.DIAGONAL_UP_RIGHT, Instant.now()),
        Word("5", "p1", "HILT", 4, 0, Direction.HORIZONTAL),
        Word("6", "p1", "RETROFIT", 5, 0, Direction.VERTICAL)
    )
    CacaInfinitaTheme {
        WordsBottomSheet(
            words = words,
            sheetState = rememberModalBottomSheetState(),
            onDismissRequest = {}
        )
    }
}
