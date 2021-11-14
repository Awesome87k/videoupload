package com.genesislabs.video.repository;

import com.genesislabs.video.entity.FileUploadEntity;
import com.genesislabs.video.entity.QFileUploadEntity;
import com.genesislabs.video.entity.QUserEntity;
import com.genesislabs.video.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @Transactional
    public boolean addUploadVideoInfo(FileUploadEntity _fileEntity) {
        super.getEntityManager().persist(_fileEntity);
        return super.getEntityManager().contains(_fileEntity);
    }

}