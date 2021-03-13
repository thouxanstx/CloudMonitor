package com.bcu.cloudmonitor;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.ml.v1.CloudMachineLearningEngine;
import com.google.api.services.ml.v1.model.GoogleCloudMlV1Job;
import com.google.api.services.ml.v1.model.GoogleCloudMlV1ListJobsResponse;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GoogleCloudMlJobHelper {

    private static final String AUTH_SCOPE = "https://www.googleapis.com/auth/cloud-platform";
    private final static String TAG = "CloudMonitor";
    private static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static List<Job> jobs = new ArrayList<>();

    public void readJobDetails(ListJobsActivity activity, Account account) {
        new GetJobTask(activity).execute(account);
    }
    static class GetJobTask extends AsyncTask<Account, Void, List<Job>> {
        private WeakReference<ListJobsActivity> mActivityRef;

        public GetJobTask(ListJobsActivity activity) {
            mActivityRef = new WeakReference<>(activity);
        }
        @Override
        protected List<Job> doInBackground(Account... accounts) {
            if (mActivityRef.get() == null) {
                return null;
            }
            jobs.clear();
            Context context = mActivityRef.get().getApplicationContext();
            try {
                GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                        context,
                        Collections.singleton(AUTH_SCOPE));
                credential.setSelectedAccount(accounts[0]);
                CloudMachineLearningEngine service = new CloudMachineLearningEngine.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                        .setApplicationName("Cloud Monitor")
                        .build();
                GoogleCloudMlV1ListJobsResponse response = service
                        .projects()
                        .jobs()
                        .list("projects/deft-station-259308")
                        .execute();
                for(int k = 0; k < response.getJobs().size(); k++) {
                    GoogleCloudMlV1Job cloudMlV1Job = response.getJobs().get(k);
                    Job job = new Job();
                    job.setId(cloudMlV1Job.getJobId());
                    job.setState(cloudMlV1Job.getState());
                    job.setStartTime(cloudMlV1Job.getStartTime());
                    job.setEndTime(cloudMlV1Job.getEndTime());
                    jobs.add(job);
                }
                return jobs;
            } catch (UserRecoverableAuthIOException recoverableException) {
                if (mActivityRef.get() != null) {
                    mActivityRef.get().onRecoverableAuthException(recoverableException);
                }
            } catch (IOException e) {
                Log.w(TAG, "getJobs:exception", e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<Job> jobs) {
            super.onPostExecute(jobs);
            if (mActivityRef.get() != null) {
                mActivityRef.get().onLoadFinished(jobs);
            }
        }
    }





}






































