package com.wisecrab.trackitems.jobs;

import android.content.Context;
import android.util.Log;

import com.activeandroid.query.Select;
import com.wisecrab.trackitems.application.CustomApplication;
import com.wisecrab.trackitems.dataclasses.ItemData;
import com.wisecrab.trackitems.postboy.PostBoy;
import com.wisecrab.trackitems.postboy.PostBoyException;
import com.wisecrab.trackitems.postboy.PostBoyListener;
import com.wisecrab.trackitems.postboy.RequestType;
import com.wisecrab.trackitems.rest.PostMaps;
import com.wisecrab.trackitems.rest.WebUrls;

import org.whispersystems.jobqueue.Job;
import org.whispersystems.jobqueue.JobParameters;
import org.whispersystems.jobqueue.dependencies.ContextDependent;
import org.whispersystems.jobqueue.requirements.NetworkRequirement;

import java.util.List;

public class UploadItemsJob  extends Job implements ContextDependent{
    private static final String TAG = "UploadItemsJob";

    private Context context;

    public UploadItemsJob(Context context) {
        super(JobParameters.newBuilder()
                .withPersistence()
                .withRequirement(new NetworkRequirement(context))
                .create());
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Exception {
        List<ItemData> items = new Select().from(ItemData.class).where(ItemData.COL_IS_SYNC+"=?",false).execute();
        for(ItemData item : items) {
            PostBoy pb = new PostBoy.Builder(RequestType.POST_X_WWW_FORM_URLENCODED, WebUrls.BASE_URL+WebUrls.ITEM).create();
            pb.setPOSTValues(PostMaps.postItem(item));
//            pb.addFile("image",new File(item.getImagePath()));
            pb.setListener(new PostBoyListener() {
                @Override
                public void onPostBoyConnecting() throws PostBoyException {

                }

                @Override
                public void onPostBoyAsyncConnected(String json, int responseCode) throws PostBoyException {

                }

                @Override
                public void onPostBoyConnected(String json, int responseCode) throws PostBoyException {
                    Log.e(TAG,"onPostBoyConnected: " + json);
                }

                @Override
                public void onPostBoyConnectionFailure() throws PostBoyException {
                    Log.e(TAG,"onPostBoyConnectionFailure");
                    if (context!=null && context.getApplicationContext() instanceof CustomApplication) {
                        ((CustomApplication)context).getNetworkJobManager().add(new UploadItemsJob(context));
                    }
                }

                @Override
                public void onPostBoyError(PostBoyException e) {

                }
            });
            pb.call();
        }
    }

    @Override
    public boolean onShouldRetry(Exception e) {
        return false;
    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
