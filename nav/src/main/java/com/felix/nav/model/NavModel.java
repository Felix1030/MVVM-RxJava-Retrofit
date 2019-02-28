package com.felix.nav.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.felix.nav.adapter.NavAdapter;

import java.util.List;

public class NavModel implements MultiItemEntity {

    /**
     * articles : []
     * cid : 275
     * name : 常用工具
     */

    private int cid;
    private String name;
    private List<NavItemModel> articles;

    public NavModel(String name) {
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NavItemModel> getArticles() {
        return articles;
    }

    public void setArticles(List<NavItemModel> articles) {
        this.articles = articles;
    }

    @Override
    public int getItemType() {
        return NavAdapter.TYPE_SECTION;
    }
}
