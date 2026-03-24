package dev.fluxa.demo.work

import android.content.Context
import androidx.work.WorkerParameters
import dev.fluxa.work.FluxaTask
import dev.fluxa.work.FluxaTaskResult

/**
 * Periodic background sync task. In a real app this would
 * push local changes and pull remote updates.
 */
class SyncTask(
    context: Context,
    params: WorkerParameters,
) : FluxaTask(context, params) {

    override suspend fun execute(): FluxaTaskResult {
        // Simulate sync work
        kotlinx.coroutines.delay(500)
        println("[SyncTask] Background sync completed")
        return FluxaTaskResult.Success
    }
}
