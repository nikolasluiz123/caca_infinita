package br.com.schmittsolucoes.cacainfinita.data.datasource.remote

import br.com.schmittsolucoes.cacainfinita.data.provider.ActivityProvider
import br.com.schmittsolucoes.cacainfinita.data.model.dto.CloudSaveDTO
import br.com.schmittsolucoes.cacainfinita.domain.service.GameSnapshotRemoteDataSource
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.SnapshotsClient
import com.google.android.gms.games.snapshot.SnapshotMetadataChange
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GooglePlayGamesRemoteDataSource @Inject constructor(
    private val activityProvider: ActivityProvider
) : GameSnapshotRemoteDataSource {

    private val snapshotName = "caca_infinita_progress"

    override suspend fun saveProgress(data: CloudSaveDTO): Boolean {
        val activity = activityProvider.getActivity() ?: return false
        val snapshotsClient = PlayGames.getSnapshotsClient(activity)

        return try {
            val dataOrConflict = snapshotsClient.open(
                snapshotName,
                true,
                SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED
            ).await()

            val snapshot = dataOrConflict.data ?: return false
            val json = Json.encodeToString(data)
            snapshot.snapshotContents.writeBytes(json.toByteArray())

            val metadataChange = SnapshotMetadataChange.Builder()
                .setDescription("Game progress for ${data.user.id}")
                .setProgressValue(data.user.experience)
                .build()

            snapshotsClient.commitAndClose(snapshot, metadataChange).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun loadProgress(): CloudSaveDTO? {
        val activity = activityProvider.getActivity() ?: return null
        val snapshotsClient = PlayGames.getSnapshotsClient(activity)

        return try {
            val dataOrConflict = snapshotsClient.open(
                snapshotName,
                false,
                SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED
            ).await()

            val snapshot = dataOrConflict.data ?: return null
            val bytes = snapshot.snapshotContents.readFully() ?: return null
            val json = String(bytes)
            Json.decodeFromString<CloudSaveDTO>(json)
        } catch (e: Exception) {
            null
        }
    }
}
