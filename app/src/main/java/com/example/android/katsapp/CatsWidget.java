package com.example.android.katsapp;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class CatsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.cats_widget);

            Intent launchBreedListIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchBreedListIntent, 0);

            views.setOnClickPendingIntent(R.id.widgetContainer, pendingIntent);

            Intent intent = new Intent(context, WidgetRemoteViewsService.class);

            views.setRemoteAdapter(R.id.widgetListView, intent);

            PendingIntent pendingIntent1 = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(launchBreedListIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setPendingIntentTemplate(R.id.widgetListView, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }

    }

    public static void sendRefreshBroadcast(Context context){

        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        intent.setComponent(new ComponentName(context, CatsWidget.class));

        context.sendBroadcast(intent);

    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        final String action = intent.getAction();

        assert action != null;
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            // refresh all your widgets
            AppWidgetManager mngr = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, CatsWidget.class);
            mngr.notifyAppWidgetViewDataChanged(mngr.getAppWidgetIds(componentName), R.id.widgetListView);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

