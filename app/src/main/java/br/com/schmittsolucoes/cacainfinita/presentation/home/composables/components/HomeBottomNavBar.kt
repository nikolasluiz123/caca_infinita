package br.com.schmittsolucoes.cacainfinita.presentation.home.composables.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseIds
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.showcaseTarget
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme

@Composable
fun HomeBottomNavBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit = {},
    onWordSearchClick: () -> Unit = {},
    isHomeSelected: Boolean = true
) {
    NavigationBar(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal))
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .showcaseTarget(ShowcaseIds.BOTTOM_NAV_BAR),
        windowInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)
    ) {
        NavigationBarItem(
            selected = isHomeSelected,
            onClick = onHomeClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = null,
                )
            },
            label = { Text(stringResource(R.string.home_tab)) },
        )
        NavigationBarItem(
            selected = !isHomeSelected,
            onClick = onWordSearchClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_puzzle_list),
                    contentDescription = null,
                )
            },
            label = { Text(stringResource(R.string.word_search_tab)) },
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeBottomNavBarPreview() {
    CacaInfinitaTheme {
        HomeBottomNavBar()
    }
}
