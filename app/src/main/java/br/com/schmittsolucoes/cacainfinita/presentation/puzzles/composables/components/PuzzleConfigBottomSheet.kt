package br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.presentation.theme.SecondaryTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleConfigBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onConfirm: (PuzzleGenerationConfig) -> Unit,
) {
    var isPortugueseSelected by remember { mutableStateOf(true) }
    var isEnglishSelected by remember { mutableStateOf(true) }

    val isConfirmEnabled = isPortugueseSelected || isEnglishSelected

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.puzzle_config_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.puzzle_config_language_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = SecondaryTextColor,
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                LanguageOption(
                    label = stringResource(R.string.puzzle_config_portuguese),
                    isSelected = isPortugueseSelected,
                    onSelectedChange = { isPortugueseSelected = it },
                    modifier = Modifier.weight(1f)
                )

                LanguageOption(
                    label = stringResource(R.string.puzzle_config_english),
                    isSelected = isEnglishSelected,
                    onSelectedChange = { isEnglishSelected = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val selection = when {
                        isPortugueseSelected && isEnglishSelected -> LanguageSelection.BOTH
                        isPortugueseSelected -> LanguageSelection.PORTUGUESE_ONLY
                        else -> LanguageSelection.ENGLISH_ONLY
                    }
                    onConfirm(PuzzleGenerationConfig(selection))
                },
                enabled = isConfirmEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.puzzle_config_confirm))
            }
        }
    }
}

@Composable
private fun LanguageOption(
    label: String,
    isSelected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onSelectedChange(!isSelected) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelectedChange
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PuzzleConfigBottomSheetPreview() {
    CacaInfinitaTheme {
        PuzzleConfigBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onDismissRequest = {},
            onConfirm = {}
        )
    }
}
