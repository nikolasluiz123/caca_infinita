package br.com.schmittsolucoes.cacainfinita.presentation.home.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacainfinita.domain.model.User
import br.com.schmittsolucoes.cacainfinita.presentation.formatters.formatToClock
import br.com.schmittsolucoes.cacainfinita.presentation.theme.Bronze
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.presentation.theme.Gold
import br.com.schmittsolucoes.cacainfinita.presentation.theme.SecondaryTextColor
import br.com.schmittsolucoes.cacainfinita.presentation.theme.Silver
import kotlin.time.Duration.Companion.seconds

@Composable
fun PersonalRecordsSection(
    records: List<PuzzleRecord>,
    modifier: Modifier = Modifier,
    user: User?
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.personal_records),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AchievementsSection(
            puzzlesCompleted = user?.puzzlesCompleted ?: 0,
            totalWordsFound = user?.totalWordsFound ?: 0,
            fastestFirstWordMs = user?.fastestFirstWordMs
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (records.isEmpty()) {
            RecordsEmptyState()
        } else {
            records.forEachIndexed { index, record ->
                val iconColor = when (index) {
                    0 -> Gold
                    1 -> Silver
                    2 -> Bronze
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }

                RecordItem(
                    title = record.puzzleName,
                    words = record.wordsCount,
                    time = record.duration.formatToClock(),
                    iconColor = iconColor
                )

                if (index < records.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun RecordsEmptyState(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_tomorrow_27dp),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = SecondaryTextColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.no_records_message),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = SecondaryTextColor,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RecordItem(
    title: String,
    words: Long,
    time: String,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_trophy),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
                Text(
                    text = "${stringResource(R.string.words_label, words)} - $time",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PersonalRecordsSectionPreview() {
    CacaInfinitaTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            PersonalRecordsSection(
                records = listOf(
                    PuzzleRecord("1", "Exploração Submarina", 15, 272.seconds),
                    PuzzleRecord("2", "Cidades do Mundo", 12, 315.seconds),
                    PuzzleRecord("3", "Animais Selvagens", 10, 230.seconds)
                ),
                user = User(
                    id = "1",
                    actualExperience = 500,
                    maxLevelExperience = 1000,
                    level = 5,
                    puzzlesCompleted = 10,
                    totalWordsFound = 150,
                    fastestFirstWordMs = 2000
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            PersonalRecordsSection(
                records = emptyList(),
                user = null
            )
        }
    }
}
