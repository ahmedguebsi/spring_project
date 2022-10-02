package com.bezkoder.spring.hibernate.manytomany.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.*;


@Entity
@Table(name = "Label")

public class Label extends Auditable<String> {

  public static List<Label> getAllLabels;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "id_ext")
  private long idExt;

  @Column(name = "name")
  private String name;
  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })


  private Set<Entry> entrys = new HashSet<>();

  public Label() {

  }

  public Label(String name, long idExt, long id) {
    this.name = name;
    this.idExt = idExt;
    this.id = id;

  }

  public long getId() {
    return id;
  }

  public long getIdExt() {
    return idExt;
  }

  public void setIdExt(long idExt) {
    this.idExt = idExt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Entry> getEntrys() {
    return entrys;
  }

  public void setEntrys(Set<Entry> entrys) {
    this.entrys = entrys;
  }

  public List<Label> getAllLabels() {
    return getAllLabels;
  }

  @Override
  public String toString() {
    return "'"+name+"'";
  }
}