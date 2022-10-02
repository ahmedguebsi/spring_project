package com.bezkoder.spring.hibernate.manytomany.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.hibernate.manytomany.model.Entry;
import com.bezkoder.spring.hibernate.manytomany.model.EntryUpdatePair;

@Service
@Transactional
public class EntryRevisionService {

    private static final Logger logger = LoggerFactory.getLogger(EntryRevisionService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<EntryUpdatePair> getEntryUpdates() {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery query = auditReader.createQuery()
                .forRevisionsOfEntity(Entry.class, false, false)               
                .add(AuditEntity.revisionType().eq(RevisionType.MOD)); // we're only interested in MODifications

        List<Object[]> revisions = (List<Object[]>) query.getResultList();
        List<EntryUpdatePair> results = new ArrayList<>();

        for (Object[] result : revisions) {
            Entry entry = (Entry) result[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) result[1];

            logger.info("The content of the entry updated at {} was {}", revisionEntity.getRevisionDate(), entry.getText());
            results.add(new EntryUpdatePair(entry, revisionEntity.getRevisionDate()));
        }

        return results;
    }
}