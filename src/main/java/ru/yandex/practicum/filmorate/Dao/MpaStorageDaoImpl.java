package ru.yandex.practicum.filmorate.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.Interface.MpaStorageDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaStorageDaoImpl implements MpaStorageDao {
    private final Logger log = LoggerFactory.getLogger(FilmStorageDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public MpaStorageDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa addMpa(Mpa mpa) {
        String request = "insert into mpa values (?, ?)";
        jdbcTemplate.update(request,
                mpa.getId(),
                mpa.getName());
        log.info("Рейтинг {} добавлен в таблицу", mpa);
        return mpa;
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "select * from mpa";
        List<Mpa> mpa = jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
        log.info("Получен список рейтингов");
        if(mpa.isEmpty())
            log.info("Список рейтингов пуст");
        return mpa;
    }

    @Override
    public Mpa getMpaId(int id) {
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("select * from mpa where id = ?", id);
        Mpa mpa;
        if(mpaRows.next()) {
            mpa = new Mpa(
                    mpaRows.getInt("id"),
                    mpaRows.getString("name"));
            if(mpa.getId() == id){
                log.info("Найден рейтинг: {} {}", mpa.getId(), mpa.getName());
            }
        } else {
            log.info("Рейтинг с идентификатором {} не найден.", id);
            return null;
        }
        return mpa;
    }

    private Mpa makeMpa(ResultSet res) throws SQLException {
        Integer id = res.getInt("id");
        String name = res.getString("name");
        return new Mpa(id, name);
    }
}
