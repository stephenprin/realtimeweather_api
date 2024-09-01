package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.commonn.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, String>{
    @Query("SELECT l FROM Location l WHERE l.trashed = false")
    public List<Location> findUntrashed();
    @Query("SELECT l FROM Location l WHERE l.code = ?1 AND l.trashed = false")
    public Location findByCode(String code);

    @Modifying
    @Query("UPDATE Location SET trashed = true WHERE code = ?1")
    public void trashedByCode(String code);

}
