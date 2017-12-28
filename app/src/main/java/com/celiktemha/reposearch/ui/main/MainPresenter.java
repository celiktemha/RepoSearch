package com.celiktemha.reposearch.ui.main;

import android.text.TextUtils;

import com.celiktemha.reposearch.ui.base.BasePresenter;
import com.celiktemha.reposearch.model.entities.GitHubRepoListItem;
import com.celiktemha.reposearch.model.remote.DataServiceProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by protemha on 17/12/28.
 */

public class MainPresenter extends BasePresenter<MainView> {

    Call<List<GitHubRepoListItem>> gitHubRepoListCall;

    public void onSearchClicked(String username) {
        if (isViewAttach()) {
            if (TextUtils.isEmpty(username)) {
                view.showEmptyUserNameError();
            } else {
                getGitHubRepoList(username);
            }
        }
    }

    private void getGitHubRepoList(String username) {
        view.showLoading();
        view.hideError();
        view.hideData();
        view.hideNoResultView();

        gitHubRepoListCall = DataServiceProvider.getInstance().getGitHubRepoListByUserName(username);

        gitHubRepoListCall.enqueue(new Callback<List<GitHubRepoListItem>>() {
            @Override
            public void onResponse(Call<List<GitHubRepoListItem>> call, Response<List<GitHubRepoListItem>> response) {
                if (isViewAttach()) {
                    view.hideLoading();

                    if (response.isSuccessful()) {
                        List<GitHubRepoListItem> result = response.body();
                        if (result == null || result.size() == 0) {
                            view.showNoResultView();
                        } else {
                            view.showData(result);
                        }
                    } else {
                        view.showError();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepoListItem>> call, Throwable t) {
                if (isViewAttach()) {
                    view.hideLoading();
                    view.showError();
                }
            }
        });
    }


    public void onItemClicked(GitHubRepoListItem gitHubRepoListItem) {
        if (isViewAttach()) {
            view.openCustomTab(gitHubRepoListItem.html_url);
        }
    }


    @Override
    public void detach() {
        if (gitHubRepoListCall != null) {
            gitHubRepoListCall.cancel();
        }
    }
}
