package com.emergency.button.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.emergency.button.MainActivity
import com.emergency.button.R
import com.emergency.button.utils.EmergencyManager

class EmergencyWidget : AppWidgetProvider() {
    
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateEmergencyWidget(context, appWidgetManager, appWidgetId)
        }
    }
    
    private fun updateEmergencyWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        try {
            val views = RemoteViews(context.packageName, R.layout.widget_emergency)
            
            // Create intent for emergency button click
            val emergencyIntent = Intent(context, MainActivity::class.java).apply {
                action = "EMERGENCY_WIDGET_CLICK"
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            
            val emergencyPendingIntent = PendingIntent.getActivity(
                context,
                0,
                emergencyIntent,
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
            )
            
            views.setOnClickPendingIntent(R.id.widgetEmergencyButton, emergencyPendingIntent)
            
            // Update widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
            
        } catch (e: Exception) {
            android.util.Log.e("EmergencyWidget", "Error updating widget", e)
        }
    }
    
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        // Widget enabled
    }
    
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        // Widget disabled
    }
}
