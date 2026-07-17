package br.com.schmittsolucoes.cacainfinita.presentation.home.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.presentation.formatters.formatToClock
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.presentation.theme.SecondaryTextColor
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AchievementsSection(
    puzzlesCompleted: Long,
    totalWordsFound: Long,
    fastestFirstWordMs: Long?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AchievementCard(
            modifier = Modifier.weight(1f),
            icon = painterResource(R.drawable.ic_checked_filled_rounded_20dp),
            value = puzzlesCompleted.toString(),
            label = stringResource(R.string.achievements_resolved)
        )
        AchievementCard(
            modifier = Modifier.weight(1f),
            icon = painterResource(R.drawable.ic_search_36dp),
            value = totalWordsFound.toString(),
            label = stringResource(R.string.achievements_words)
        )
        AchievementCard(
            modifier = Modifier.weight(1f),
            icon = painterResource(R.drawable.ic_flash_on_24dp),
            value = fastestFirstWordMs?.milliseconds?.formatToClock() ?: "--:--",
            label = stringResource(R.string.achievements_first_word)
        )
    }
}

@Composable
private fun AchievementCard(
    icon: Painter,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = SecondaryTextColor
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AchievementsSectionPreview() {
    CacaInfinitaTheme {
        AchievementsSection(
            puzzlesCompleted = 12,
            totalWordsFound = 145,
            fastestFirstWordMs = 4500L
        )
    }
}
