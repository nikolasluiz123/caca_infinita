package br.com.schmittsolucoes.cacainfinita.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

@Composable
fun <T : Any> PagedGrid(
    items: LazyPagingItems<T>,
    columns: GridCells,
    emptyContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable (index: Int, item: T?) -> Unit
) {
    if (items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading) {
        emptyContent()
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (items.loadState.refresh) {
            is LoadState.Loading if items.itemCount == 0 -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            is LoadState.Error if items.itemCount == 0 -> {
                val error = (items.loadState.refresh as LoadState.Error).error

                ErrorDialog(
                    message = error.localizedMessage ?: "Unknown Error",
                    onDismiss = { items.retry() }
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = columns,
                    state = state,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding,
                    verticalArrangement = verticalArrangement,
                    horizontalArrangement = horizontalArrangement
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey(key),
                        contentType = items.itemContentType()
                    ) { index ->
                        itemContent(index, items[index])
                    }

                    if (items.loadState.append is LoadState.Loading) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
