package com.gerosprime.gylog.ui.workouts.session

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.timer.CountDownEntity
import com.gerosprime.gylog.models.timer.CountDownTimer
import com.gerosprime.gylog.models.timer.StopWatch
import com.gerosprime.gylog.models.timer.StopWatchEntity
import com.gerosprime.gylog.ui.workouts.R
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WorkoutSessionService : Service() {

    companion object {
        const val SERVICE_ID = 442
    }

    object Extras {
        const val COMMAND = "extra_command"
        const val COMMAND_ARGUMENT = "extra_command_argument"
    }

    object CommandValues {
        const val NORMAL_START = 0
        const val NORMAL_REST_DONE = 1
        const val REST = 2
    }

    var stopWatch : CountDownTimer = CountDownTimer()
    private var disposableTimer : Disposable? = null

    @Inject
    lateinit var modelsCache: ModelsCache

    private fun startTimer(durationSeconds : Int) {

        disposableTimer?.dispose()

        disposableTimer = stopWatch.create(CountDownEntity(durationSeconds))
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                run {
                    if (it.seconds <= 0) {
                        startForeground(SERVICE_ID, createNotificationNormalRestDone())
                        disposableTimer?.dispose()
                    }
                }
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null) {

            val command = commandValue(intent)

            when (command) {
                CommandValues.REST -> {

                    val durationSeconds = intent.getIntExtra(Extras.COMMAND_ARGUMENT, 0)
                    startForeground(
                        SERVICE_ID,
                        createNotificationRestTimer(durationSeconds)
                    )
                    startTimer(durationSeconds)

                }
                CommandValues.NORMAL_START -> startForeground(
                    SERVICE_ID,
                    createNotificationNormalStart(
                        intent.getLongExtra(Extras.COMMAND_ARGUMENT, -1)
                    )
                )
                CommandValues.NORMAL_REST_DONE -> startForeground(SERVICE_ID, createNotificationNormalRestDone())
            }

        }

        return super.onStartCommand(intent, flags, startId)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    private fun commandValue(intent : Intent) : Int {
        val command = intent.getIntExtra(Extras.COMMAND, 0)
        return command
    }
    private fun commandValueString(intent : Intent) : Int {
        return intent.getIntExtra(Extras.COMMAND, 0)
    }

    private fun createNotificationRestTimer(duration : Int)
            : Notification {

        val notificationId = baseContext.getString(R.string.notification_channel_id_workout_session)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationId, getString(R.string.workouts))
        }

        val workoutSessionIntent = Intent(applicationContext, WorkoutSessionActivity::class.java)
        workoutSessionIntent.putExtra(WorkoutSessionActivity.States.RESUME_WORKOUT, true)

        return NotificationCompat.Builder(applicationContext, notificationId)
            .setUsesChronometer(true)
            .setContentIntent(PendingIntent.getActivity(applicationContext,
                0, workoutSessionIntent, PendingIntent.FLAG_UPDATE_CURRENT))
            .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
            .setContentTitle(baseContext.getString(R.string.app_name))
            .build()

    }

    private fun createNotificationNormalStart(workoutId : Long)
            : Notification {

        val notificationId = baseContext.getString(R.string.notification_channel_id_workout_session)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationId, getString(R.string.workouts))
        }

        val workoutName = modelsCache.workoutsMap[workoutId]?.name
        val workoutSessionIntent = Intent(applicationContext, WorkoutSessionActivity::class.java)
        workoutSessionIntent.putExtra(WorkoutSessionActivity.States.RESUME_WORKOUT, true)

        val contentText = baseContext.getString(R.string.workout_name_just_started,
            workoutName)
        return NotificationCompat.Builder(applicationContext, notificationId)
                    .setContentIntent(PendingIntent.getActivity(applicationContext,
                        0, workoutSessionIntent, PendingIntent.FLAG_UPDATE_CURRENT))
            .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                    .setContentTitle(baseContext.getString(R.string.app_name))
                    .setContentText(contentText)
                    .build()

    }

    private fun createNotificationNormalRestDone()
            : Notification {

        val notificationId = baseContext.getString(R.string.notification_channel_id_workout_session)

        val workoutSessionIntent = Intent(applicationContext, WorkoutSessionActivity::class.java)
        workoutSessionIntent.putExtra(WorkoutSessionActivity.States.RESUME_WORKOUT, true)

        return NotificationCompat.Builder(applicationContext, notificationId)
                    .setContentIntent(PendingIntent.getActivity(applicationContext,
                        0, workoutSessionIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                    .setContentTitle(baseContext.getString(R.string.app_name))
                    .setContentText(baseContext.getString(R.string.rest_done_go_back_to_work))
                    .build()


    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
