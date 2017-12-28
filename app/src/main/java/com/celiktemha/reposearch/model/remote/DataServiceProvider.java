package com.celiktemha.reposearch.model.remote;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by protemha on 17/12/27.
 */

public class DataServiceProvider {


    private static GitHubServiceInterface serviceInterface;

    public static GitHubServiceInterface getInstance() {
        if (serviceInterface == null) {
            serviceInterface = new Retrofit.Builder()
                    .baseUrl(ServiceUrl.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build().create(GitHubServiceInterface.class);
        }

        return serviceInterface;
    }
}
