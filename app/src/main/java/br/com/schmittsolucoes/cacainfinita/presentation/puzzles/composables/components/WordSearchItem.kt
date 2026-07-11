package br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.presentation.theme.SecondaryTextColor
import br.com.schmittsolucoes.cacainfinita.presentation.theme.UnfinishedPuzzleStatusColor
import br.com.schmittsolucoes.cacainfinita.presentation.theme.WordSelectionColor

@Composable
fun WordSearchItem(
    puzzle: WordSearchPuzzleSummary,
    onClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    statusModifier: Modifier = Modifier,
    deleteModifier: Modifier = Modifier,
) {
    Card(
        onClick = { onClick(puzzle.id) },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = statusModifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(
                        if (puzzle.hasUnfinishedWords) UnfinishedPuzzleStatusColor
                        else WordSelectionColor
                    )
            )
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PuzzleInfo(
                    name = puzzle.name,
                    wordsCount = puzzle.wordsCount,
                    languages = puzzle.languages,
                    orientation = puzzle.orientation,
                    modifier = Modifier.weight(1f)
                )
                if (puzzle.hasUnfinishedWords) {
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
}

@Composable
private fun PuzzleInfo(
    name: String,
    wordsCount: Int,
    languages: List<Language>,
    orientation: GridOrientation,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.weight(1f, fill = false)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OrientationLabel(orientation)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.words_label, wordsCount),
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryTextColor
            )
            Text(
                text = " • ",
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryTextColor
            )
            LanguagesLabel(languages)
        }
    }
}

@Composable
private fun OrientationLabel(orientation: GridOrientation) {
    val textRes = when (orientation) {
        GridOrientation.PORTRAIT -> R.string.puzzle_config_orientation_portrait
        GridOrientation.LANDSCAPE -> R.string.puzzle_config_orientation_landscape
    }
    Text(
        text = "(${stringResource(textRes)})",
        style = MaterialTheme.typography.labelMedium,
        color = SecondaryTextColor
    )
}

@Composable
private fun LanguagesLabel(languages: List<Language>) {
    val label = languages.map { language ->
        stringResource(
            when (language) {
                Language.PORTUGUESE -> R.string.puzzle_item_language_portuguese
                Language.ENGLISH -> R.string.puzzle_item_language_english
                Language.OTHER -> R.string.puzzle_item_language_other
            }
        )
    }.joinToString()

    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        color = SecondaryTextColor
    )
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
                    hasUnfinishedWords = true,
                    languages = listOf(Language.PORTUGUESE),
                    orientation = GridOrientation.PORTRAIT
                ),
                onClick = {},
                onDeleteClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            WordSearchItem(
                puzzle = WordSearchPuzzleSummary(
                    id = "2",
                    name = "Cities",
                    wordsCount = 15,
                    hasUnfinishedWords = false,
                    languages = listOf(Language.ENGLISH, Language.PORTUGUESE),
                    orientation = GridOrientation.LANDSCAPE
                ),
                onClick = {},
                onDeleteClick = {}
            )
        }
    }
}
