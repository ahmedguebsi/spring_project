package com.bezkoder.spring.hibernate.manytomany.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.hibernate.manytomany.model.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findLabelById(long entryId);

    List<Label> findLabelsByEntrysId(long entryId);

    Optional<Label> findByIdExt(long idExt);

    List <Label> findByNameContaining(String name);

}
