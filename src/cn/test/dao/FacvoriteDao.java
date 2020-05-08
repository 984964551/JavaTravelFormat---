package cn.test.dao;

import cn.test.domain.Favorite;
import cn.test.domain.Route;

import java.util.List;

public interface FacvoriteDao {
    public Favorite findfavorite(int cid,int uid);

    void savefavorite(int rid, int uid);

    public List<Route> findfavoriterid(int uid);

    Route findallfavorite(int uid);
}
