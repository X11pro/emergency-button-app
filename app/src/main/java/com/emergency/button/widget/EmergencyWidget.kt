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
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        views.setOnClickPendingIntent(R.id.widgetEmergencyButton, emergencyPendingIntent)
        
        // Update widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
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
