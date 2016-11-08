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

import io.reactivex.Single;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class SafFileRepository implements FileRepository {

    private final ActivityService activityService;
    private final ContentResolver contentResolver;

    public SafFileRepository(ActivityService activityService, ContentResolver contentResolver) {
        this.activityService = activityService;
        this.contentResolver = contentResolver;
    }

    @Override
    public Single<File> get(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("*/*");
        activityService.getCurrentActivity().startActivityForResult(intent, requestCode);

        return activityService.getActivityResult()
                .map(new Function<ActivityService.ActivityResult, File>() {
                    @Override
                    public File apply(ActivityService.ActivityResult activityResult) throws Exception {
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
                            if (cursor != null) {
                                cursor.close();
                            }
                        }

                        return new File(fileName);
                    }
                }).singleOrError();
    }
}
