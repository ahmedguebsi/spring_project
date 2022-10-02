package com.bezkoder.spring.hibernate.manytomany.model;

import com.bezkoder.spring.hibernate.manytomany.model.Entry;
import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import java.lang.String;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


@Entity
@Table(name = "Entry")
@Audited
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

public class Entry extends Auditable<String> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "id_ext")
  private long idExt;

  @Column(name = "text")
  private String text;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "assoc", joinColumns = { @JoinColumn(name = "entry_id") }, inverseJoinColumns = {
    @JoinColumn(name = "label_id") })

  @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)

  private Set<Label> labels = new HashSet<>();
  
 
  public Entry() {
  }

  public Entry(String text, long idExt, long id) {
    this.text = text;
    this.idExt = idExt;
    this.id = id;
  }

  public Entry(String text2) {
    this.text = text2;
  }

  // getters and setters
  public long addLabel(Label label) {
    this.labels.add(label);
    label.getEntrys().add(this);
    return label.getId();
  }

  public void removeLabel(long LabelId) {
    Label label = this.labels.stream().filter(t -> t.getId() == LabelId).findFirst().orElse(null);
    if (label != null)
      this.labels.remove(label);
    label.getEntrys().remove(this);
  }

  public long getId() {
    return id;
  }

  public long getExtId() {
    return idExt;
  }

  public void setExtId(long idExt) {
    this.idExt = idExt;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Set<Label> getLabels() {
    return labels;
  }

  public void setLabels(Set<Label> labels) {
    this.labels = labels;
  }

  @Override
  public String toString() {
    return "id=" + id;
  }
  
 
}

