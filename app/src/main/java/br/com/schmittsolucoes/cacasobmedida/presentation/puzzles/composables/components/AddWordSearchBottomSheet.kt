package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordSearchBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onOpenCameraClick: () -> Unit,
    onLoadImageClick: () -> Unit,
    onLoadPdfClick: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.add_word_search_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            BottomSheetOption(
                iconResId = R.drawable.ic_camera_20dp,
                title = stringResource(R.string.add_word_search_open_camera_title),
                subtitle = stringResource(R.string.add_word_search_open_camera_subtitle),
            ) {
                onOpenCameraClick()
                onDismissRequest()
            }

            Spacer(modifier = Modifier.height(16.dp))

            BottomSheetOption(
                iconResId = R.drawable.ic_file_upload_20dp,
                title = stringResource(R.string.add_word_search_load_image_title),
                subtitle = stringResource(R.string.add_word_search_load_image_subtitle),
            ) {
                onLoadImageClick()
                onDismissRequest()
            }

            Spacer(modifier = Modifier.height(16.dp))

            BottomSheetOption(
                iconResId = R.drawable.ic_pdf_file_20dp,
                title = stringResource(R.string.add_word_search_load_pdf_title),
                subtitle = stringResource(R.string.add_word_search_load_pdf_subtitle),
            ) {
                onLoadPdfClick()
                onDismissRequest()
            }
        }
    }
}

@Composable
private fun BottomSheetOption(
    @DrawableRes iconResId: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddWordSearchBottomSheetPreview() {
    CacaSobMedidaTheme {
        AddWordSearchBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onDismissRequest = {},
            onOpenCameraClick = {},
            onLoadImageClick = {},
            onLoadPdfClick = {}
        )
    }
}

