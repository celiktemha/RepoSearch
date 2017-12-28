package com.celiktemha.reposearch.ui.main;

import com.celiktemha.reposearch.ui.base.BaseView;
import com.celiktemha.reposearch.model.entities.GitHubRepoListItem;

import java.util.List;

/**
 * Created by celiktemha on 17/12/28.
 */

public interface MainView extends BaseView {
    void showData(List<GitHubRepoListItem> result);
    void hideData();
    void showNoResultView();
    void hideNoResultView();
    void showEmptyUserNameError();
    void openCustomTab(String url);
}
