package com.streetreview.file.repository;

import com.streetreview.file.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByTargetIdAndType(String targetId, String type);
    void deleteByPhotoId(Long photoId);

    Optional<Photo> findByUuId(String uuId);
}
