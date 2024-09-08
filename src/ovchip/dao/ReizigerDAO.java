package ovchip.dao;

import ovchip.domain.Reiziger;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(Date geboortedatum);

    List<Reiziger> findByGbdatum(LocalDate geboortedatum);

    List<Reiziger> findAll();
}
