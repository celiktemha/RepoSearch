package com.celiktemha.reposearch.ui.main;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celiktemha.reposearch.R;
import com.celiktemha.reposearch.model.entities.GitHubRepoListItem;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener, AdapterView.OnItemClickListener {

    EditText userNameEditText;
    Button searchButton;
    ListView gitHubRepoListView;
    TextView noResultTextView;
    ProgressBar progressBar;

    Toast errorToastMessage;

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.usernameEditText);
        searchButton = findViewById(R.id.searchButton);
        gitHubRepoListView = findViewById(R.id.gitHublistView);
        noResultTextView = findViewById(R.id.noResultTextView);
        progressBar = findViewById(R.id.progressBar);

        searchButton.setOnClickListener(this);

        gitHubRepoListView.setOnItemClickListener(this);

        presenter = new MainPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        closeKeyboard();
        presenter.onSearchClicked(userNameEditText.getText().toString());
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        errorToastMessage = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT);
        errorToastMessage.show();
    }

    @Override
    public void hideError() {
        if (errorToastMessage != null) {
            errorToastMessage.cancel();
            errorToastMessage = null;
        }
    }

    @Override
    public void showData(List<GitHubRepoListItem> result) {
        gitHubRepoListView.setAdapter(new GitHubRepoListViewAdapter(result));
        gitHubRepoListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideData() {
        gitHubRepoListView.setVisibility(View.GONE);
    }

    @Override
    public void showNoResultView() {
        noResultTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResultView() {
        noResultTextView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyUserNameError() {
        Toast.makeText(this, getString(R.string.empty_username), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openCustomTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userNameEditText.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onItemClicked((GitHubRepoListItem) parent.getItemAtPosition(position));
    }

}
