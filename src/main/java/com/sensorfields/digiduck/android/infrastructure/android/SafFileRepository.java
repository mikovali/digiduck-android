package com.sensorfields.digiduck.android.infrastructure.android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.provider.DocumentsContract;

import com.sensorfields.android.ActivityService;
import com.sensorfields.digiduck.android.model.File;
import com.sensorfields.digiduck.android.model.FileRepository;

import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

public class SafFileRepository implements FileRepository {

    private final ActivityService activityService;
    private final ContentResolver contentResolver;

    public SafFileRepository(ActivityService activityService, ContentResolver contentResolver) {
        this.activityService = activityService;
        this.contentResolver = contentResolver;
    }

    @Override
    public Observable<File> get() {
        Activity activity = activityService.getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("*/*");
            activity.startActivityForResult(intent, 3);
        }
        return activityService.getActivityResult()
                .map(new Func1<ActivityService.ActivityResult, File>() {
                    @Override
                    public File call(ActivityService.ActivityResult activityResult) {
                        Timber.e("CALL: %s" , (Looper.myLooper() == Looper.getMainLooper()));

                        String fileName = null;

                        if (activityResult.requestCode == 3
                                && activityResult.resultCode == Activity.RESULT_OK) {
                            Uri uri = activityResult.data.getData();
                            Cursor cursor = contentResolver.query(uri,
                                    new String[]{DocumentsContract.Document.COLUMN_DISPLAY_NAME},
                                    null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                fileName = cursor.getString(cursor.getColumnIndex(
                                        DocumentsContract.Document.COLUMN_DISPLAY_NAME));
                            }
                            cursor.close();
                        }

                        return new File(fileName);
                    }
                });
    }
}
