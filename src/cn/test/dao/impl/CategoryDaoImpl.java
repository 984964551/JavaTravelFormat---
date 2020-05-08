package cn.test.dao.impl;

import cn.test.dao.CategoryDao;
import cn.test.domain.Category;
import cn.test.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private static JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findall() {
        String sql="select * from tab_category";
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
