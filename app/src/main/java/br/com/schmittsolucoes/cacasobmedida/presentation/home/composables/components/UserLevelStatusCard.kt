package br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CardAccentColor
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.SecondaryTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLevelStatusCard(
    level: Long,
    xp: Long,
    xpToNextLevel: Long,
    modifier: Modifier = Modifier
) {
    val accentColor = CardAccentColor

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.6f),
                                accentColor.copy(alpha = 0.4f),
                                accentColor.copy(alpha = 0.2f),
                                Color.Transparent
                            ),
                            center = Offset(size.width, 0f),
                            radius = size.width / 2,
                        )
                    )
                }
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.user_level_status_card_label_current_status),
                    style = MaterialTheme.typography.labelLarge.copy(color = SecondaryTextColor),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.user_level_status_card_level_label, level),
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { xp.toFloat() / xpToNextLevel.toFloat() },
                    drawStopIndicator = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(
                        R.string.user_level_status_card_xp_to_next_level,
                        xp,
                        xpToNextLevel
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryTextColor),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Preview(name = "With XP Light Mode")
@Preview(name = "With XP Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserLevelStatusCardWithXPPreview() {
    CacaSobMedidaTheme {
        UserLevelStatusCard(
            level = 1,
            xp = 100,
            xpToNextLevel = 200
        )
    }
}

@Preview(name = "Without XP Light Mode")
@Preview(name = "Without XP Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserLevelStatusCardWithoutXPPreview() {
    CacaSobMedidaTheme {
        UserLevelStatusCard(
            level = 1,
            xp = 0,
            xpToNextLevel = 200
        )
    }
}
