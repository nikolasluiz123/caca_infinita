package br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.presentation.theme.SecondaryTextColor

@Composable
fun WordSearchItem(
    puzzle: WordSearchPuzzleSummary,
    onClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    statusModifier: Modifier = Modifier,
    deleteModifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick(puzzle.id) },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PuzzleInfo(
                name = puzzle.name,
                wordsCount = puzzle.wordsCount,
                modifier = Modifier.weight(1f)
            )
            StatusIcon(
                hasUnfinishedWords = puzzle.hasUnfinishedWords,
                modifier = statusModifier
            )
            if (puzzle.hasUnfinishedWords) {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { onDeleteClick(puzzle.id) },
                    modifier = deleteModifier
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_24dp),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun PuzzleInfo(
    name: String,
    wordsCount: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            )
        )
        Text(
            text = stringResource(R.string.words_label, wordsCount),
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryTextColor
        )
    }
}

@Composable
private fun StatusIcon(
    hasUnfinishedWords: Boolean,
    modifier: Modifier = Modifier
) {
    if (hasUnfinishedWords) {
        Box(
            modifier = modifier
                .size(24.dp)
                .border(1.dp, SecondaryTextColor, CircleShape)
        )
    } else {
        Icon(
            painter = painterResource(R.drawable.ic_checked_filled_rounded_20dp),
            contentDescription = null,
            modifier = modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WordSearchItemPreview() {
    CacaInfinitaTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            WordSearchItem(
                puzzle = WordSearchPuzzleSummary(
                    id = "1",
                    name = "Animais",
                    wordsCount = 10,
                    hasUnfinishedWords = true
                ),
                onClick = {},
                onDeleteClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            WordSearchItem(
                puzzle = WordSearchPuzzleSummary(
                    id = "2",
                    name = "Cidades",
                    wordsCount = 15,
                    hasUnfinishedWords = false
                ),
                onClick = {},
                onDeleteClick = {}
            )
        }
    }
}
