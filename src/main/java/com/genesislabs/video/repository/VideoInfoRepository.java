package com.genesislabs.video.repository;

import com.genesislabs.video.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VideoInfoRepository extends QuerydslRepositorySupport {

    public VideoInfoRepository() {
        super(VideoInfoRepository.class);
    }

    @Override
    @PersistenceContext(unitName = "videouploadJpaEntityManagerFactory")
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    private JPAQueryFactory queryFactory;
    private QFileUploadEntity fileEntity = QFileUploadEntity.fileUploadEntity;
    private QUserEntity userEntity = QUserEntity.userEntity;

    @Transactional
    public boolean addUploadVideoInfo(FileUploadEntity _fileEntity) {
        super.getEntityManager().persist(_fileEntity);
        return super.getEntityManager().contains(_fileEntity);
    }

    public List<FileUploadEntity> findUploadVideoDataByEmail(String _Email) {
        return queryFactory
                .selectFrom(fileEntity)
                .fetch();
    }

    public FileUploadEntity findVideoDataByVfidx(int _vf_idx) {
        return queryFactory
                .selectFrom(fileEntity)
                .where(fileEntity.vf_idx.eq(_vf_idx))
                .fetchOne();
    }
}