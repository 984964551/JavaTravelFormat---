package cn.test.service.impl;

import cn.test.dao.CategoryDao;
import cn.test.dao.impl.CategoryDaoImpl;
import cn.test.domain.Category;
import cn.test.service.CategoryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findall() {
        //1.从jedis中查询
        //获取jedis客户端
        Jedis jedis = new Jedis();
        //用sortedset方法查询
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        //查询sortedset的cid和cname
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        //2.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {
            //第一次查询，先从数据库中查询
            list = categoryDao.findall();
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category", list.get(i).getCid(), list.get(i).getCname());
            }
        } else {
            //4.如果不为空，则直接返回
            //将set的数据存入list
            list = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);
            }
        }
        return list;
    }
}
