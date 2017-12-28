package com.celiktemha.reposearch.data.remote;

import com.celiktemha.reposearch.data.entities.GitHubRepoListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by protemha on 17/12/27.
 */

public interface GitHubServiceInterface {
    @GET(ServiceUrl.USER_URL)
    Call<List<GitHubRepoListItem>> getGitHubRepoListByUserName(@Path("USERNAME") String username);
}
