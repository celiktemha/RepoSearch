package com.celiktemha.reposearch.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celiktemha.reposearch.R;
import com.celiktemha.reposearch.data.entities.GitHubRepoListItem;

import java.util.List;

/**
 * Created by protemha on 17/12/27.
 */

public class GitHubRepoListViewAdapter extends BaseAdapter {
    List<GitHubRepoListItem> datasource;

    public GitHubRepoListViewAdapter(List<GitHubRepoListItem> datasource) {
        this.datasource = datasource;
    }

    @Override
    public int getCount() {
        if (datasource == null) return 0;
        return datasource.size();
    }

    @Override
    public GitHubRepoListItem getItem(int position) {
        return datasource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bindData(getItem(position));

        return convertView;
    }


    static class ViewHolder {
        TextView repoNameTextView;
        TextView repoCreateDateTextView;

        public ViewHolder(View itemView) {
            repoNameTextView = itemView.findViewById(R.id.repo_name_textview);
            repoCreateDateTextView = itemView.findViewById(R.id.repo_create_date_textview);
        }

        public void bindData(GitHubRepoListItem gitHubRepoListItem) {
            repoNameTextView.setText(gitHubRepoListItem.name);
            repoCreateDateTextView.setText(gitHubRepoListItem.created_at);
        }
    }
}
