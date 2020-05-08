package cn.test.dao;

import cn.test.domain.Seller;

public interface SellerDao {
    //通过sid来查询商家信息
    public Seller findSeller(int sid);
}
