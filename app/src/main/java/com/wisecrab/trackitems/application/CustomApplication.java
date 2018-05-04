package com.wisecrab.trackitems.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import org.whispersystems.jobqueue.JobManager;
import org.whispersystems.jobqueue.dependencies.DependencyInjector;
import org.whispersystems.jobqueue.persistence.JavaJobSerializer;
import org.whispersystems.jobqueue.requirements.NetworkRequirementProvider;

public class CustomApplication extends Application implements DependencyInjector {

    private JobManager networkJobManager;
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        this.networkJobManager = JobManager.newBuilder(this)
                .withName("ItemsUploadJobManager")
                .withConsumerThreads(1)
                .withJobSerializer(new JavaJobSerializer())
                .withRequirementProviders(new NetworkRequirementProvider(this))
                .withDependencyInjector(this)
                .build();
    }

    public JobManager getNetworkJobManager() {
        return networkJobManager;
    }

    @Override
    public void injectDependencies(Object o) {

    }
}
