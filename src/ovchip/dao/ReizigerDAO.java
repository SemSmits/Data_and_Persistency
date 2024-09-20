package ovchip.dao;

import ovchip.domain.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(Date geboortedatum);

    List<Reiziger> findByGbdatum(LocalDate geboortedatum) throws SQLException;

    List<Reiziger> findAll() throws SQLException;
}
