package dev.fluxa.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

/**
 * Base class for Fluxa background tasks.
 * Extend this instead of CoroutineWorker directly.
 */
abstract class FluxaTask(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    abstract suspend fun execute(): FluxaTaskResult

    override suspend fun doWork(): Result {
        return when (execute()) {
            FluxaTaskResult.Success -> Result.success()
            FluxaTaskResult.Retry -> Result.retry()
            is FluxaTaskResult.Failure -> Result.failure()
        }
    }
}

sealed class FluxaTaskResult {
    object Success : FluxaTaskResult()
    object Retry : FluxaTaskResult()
    data class Failure(val reason: String) : FluxaTaskResult()
}

/**
 * Scheduler for enqueuing Fluxa tasks.
 */
class FluxaScheduler(@PublishedApi internal val context: Context) {

    /**
     * Run a one-time task.
     */
    inline fun <reified T : FluxaTask> runOnce(
        tag: String,
        requiresNetwork: Boolean = false,
        requiresCharging: Boolean = false,
    ) {
        val constraints = buildConstraints(requiresNetwork, requiresCharging)
        val request = OneTimeWorkRequestBuilder<T>()
            .setConstraints(constraints)
            .addTag(tag)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE, request)
    }

    /**
     * Schedule a periodic task.
     */
    inline fun <reified T : FluxaTask> runPeriodic(
        tag: String,
        intervalMinutes: Long = 15,
        requiresNetwork: Boolean = false,
        requiresCharging: Boolean = false,
    ) {
        val constraints = buildConstraints(requiresNetwork, requiresCharging)
        val request = PeriodicWorkRequestBuilder<T>(intervalMinutes, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(tag)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.UPDATE, request)
    }

    /**
     * Cancel a task by tag.
     */
    fun cancel(tag: String) {
        WorkManager.getInstance(context).cancelUniqueWork(tag)
    }

    /**
     * Cancel all tasks.
     */
    fun cancelAll() {
        WorkManager.getInstance(context).cancelAllWork()
    }

    @PublishedApi
    internal fun buildConstraints(
        requiresNetwork: Boolean,
        requiresCharging: Boolean,
    ): Constraints = Constraints.Builder()
        .setRequiredNetworkType(
            if (requiresNetwork) NetworkType.CONNECTED else NetworkType.NOT_REQUIRED,
        )
        .setRequiresCharging(requiresCharging)
        .build()
}

/**
 * Create a FluxaScheduler from a Context.
 */
fun Context.fluxaScheduler(): FluxaScheduler = FluxaScheduler(this)
