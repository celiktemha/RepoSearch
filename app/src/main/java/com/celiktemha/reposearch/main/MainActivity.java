package com.celiktemha.reposearch.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.celiktemha.reposearch.R;
import com.celiktemha.reposearch.data.entities.GitHubRepoListItem;
import com.celiktemha.reposearch.data.remote.DataServiceProvider;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userNameEditText;
    Button searchButton;
    ListView gitHubRepoListView;
    TextView noResultTextView;
    ProgressBar progressBar;

    Call<List<GitHubRepoListItem>> gitHubRepoListCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.usernameEditText);
        searchButton = findViewById(R.id.searchButton);
        gitHubRepoListView = findViewById(R.id.gitHublistView);
        noResultTextView = findViewById(R.id.noresultTextView);
        progressBar = findViewById(R.id.progressBar);

        searchButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(userNameEditText.getText())) {
            progressBar.setVisibility(View.VISIBLE);
            noResultTextView.setVisibility(View.GONE);

            gitHubRepoListCall = DataServiceProvider.getInstance().getGitHubRepoListByUserName(userNameEditText.getText().toString());

            gitHubRepoListCall.enqueue(new Callback<List<GitHubRepoListItem>>() {
                @Override
                public void onResponse(Call<List<GitHubRepoListItem>> call, Response<List<GitHubRepoListItem>> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        List<GitHubRepoListItem> result = response.body();
                        if (result == null || result.size() == 0) {
                            noResultTextView.setVisibility(View.VISIBLE);
                        } else {
                            noResultTextView.setVisibility(View.GONE);
                            gitHubRepoListView.setAdapter(new GitHubRepoListViewAdapter(result));
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<GitHubRepoListItem>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    noResultTextView.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        if (gitHubRepoListCall != null) {
            gitHubRepoListCall.cancel();
        }
        super.onDestroy();
    }
}
