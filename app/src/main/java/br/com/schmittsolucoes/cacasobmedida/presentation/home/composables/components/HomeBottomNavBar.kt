package br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.components

import android.content.res.Configuration
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
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme

@Composable
fun HomeBottomNavBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit = {},
    onWordSearchClick: () -> Unit = {},
    isHomeSelected: Boolean = true
) {
    NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
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
    CacaSobMedidaTheme {
        HomeBottomNavBar()
    }
}
