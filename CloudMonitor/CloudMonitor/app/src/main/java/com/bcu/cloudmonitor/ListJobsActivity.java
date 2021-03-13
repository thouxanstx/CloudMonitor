package com.bcu.cloudmonitor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.accounts.Account;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import java.util.List;

public class ListJobsActivity extends AppCompatActivity {
    private final static String TAG = "CloudMonitor";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Account mAccount;
    private static final int RC_RECOVERABLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jobs);
        mRecyclerView = findViewById(R.id.rvJobs);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getJobs();
    }


    private void getJobs() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mAccount = account.getAccount();
        if (mAccount == null) {
            Toast.makeText(ListJobsActivity.this,"getJobs: null account", Toast.LENGTH_LONG).show();
        }
        new GoogleCloudMlJobHelper().readJobDetails(this, mAccount);
    }

    protected void onLoadFinished(@Nullable List<Job> jobs) {
        if (jobs == null) {
            Log.d(TAG, "getJobs:jobs:null");
            Toast.makeText(ListJobsActivity.this,"getJobs:jobs:null", Toast.LENGTH_LONG).show();
            return;
        }
        mAdapter = new RecyclerViewJobAdapter(this, jobs);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void onRecoverableAuthException(UserRecoverableAuthIOException recoverableException) {
        Log.w(TAG, "onRecoverableAuthException", recoverableException);
        startActivityForResult(recoverableException.getIntent(), RC_RECOVERABLE);
    }
}
