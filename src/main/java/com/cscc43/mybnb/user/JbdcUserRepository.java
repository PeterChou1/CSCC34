package com.cscc43.mybnb.user;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JbdcUserRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(User book) {
        return jdbcTemplate.update("INSERT INTO User(userName, name, postalCode, city, country, sin, dob) VALUES(?,?,?,?,?,?,?)",
                new Object[] { book.getUserName(), book.getName(), book.getPostCode(), book.getCity(), book.getCountry(), book.getSin(), book.getDate() });
    }

    @Override
    public int update(User book) {
        return jdbcTemplate.update("UPDATE User SET name=?, postalCode=?, city=?, country=?, sin=?, dob=? WHERE userName=?",
                new Object[] { book.getName(), book.getPostCode(), book.getCity(), book.getCountry(), book.getSin(), book.getDate(), book.getUserName() });
    }

    @Override
    public User findByUsername(String id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM User WHERE userName=?", new Object[] { id }, new BeanPropertyRowMapper<>(User.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE FROM User WHERE userName=?", id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM User", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM User");
    }
}
