package com.uploadservice.video.repository;

import com.uploadservice.video.entity.QTokenEntity;
import com.uploadservice.video.entity.QUserEntity;
import com.uploadservice.video.entity.TokenEntity;
import com.uploadservice.video.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserInfoRepository extends QuerydslRepositorySupport {

    public UserInfoRepository() {
        super(UserInfoRepository.class);
    }

    @Override
    @PersistenceContext(unitName = "videouploadJpaEntityManagerFactory")
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    private JPAQueryFactory queryFactory;
    private QUserEntity userEntity = QUserEntity.userEntity;
    private QTokenEntity tokenEntity = QTokenEntity.tokenEntity;

    public UserEntity findUserByUserInfo(String _email) {
        return queryFactory
                .selectFrom(userEntity)
                .where(userEntity.vu_del_yn.eq("N")
                        .and(userEntity.vu_email.eq(_email)))
                .fetchOne();
    }

    public UserEntity findUserByUserInfo(String _email, String _pw) {
        return queryFactory
                .selectFrom(userEntity)
                .where(userEntity.vu_del_yn.eq("N")
                        .and(userEntity.vu_email.eq(_email))
                        .and(userEntity.vu_pw.eq(_pw)))
                .fetchOne();
    }

    public UserEntity findUserByTokeninfo(String _refreshToken) {
        return queryFactory
                .selectFrom(userEntity)
                .join(tokenEntity).on(tokenEntity.vu_idx.eq(userEntity.vu_idx))
                .where(userEntity.vu_del_yn.eq("N")
                        .and(tokenEntity.vt_refresh_token.eq(_refreshToken)))
                .fetchOne();
    }

    public TokenEntity findTokenInfoByEmail(String _Email) {
        return queryFactory
                .selectFrom(tokenEntity)
                .join(userEntity).on(tokenEntity.vu_idx.eq(userEntity.vu_idx))
                .where(userEntity.vu_del_yn.eq("N")
                        .and(userEntity.vu_email.eq(_Email)))
                .fetchOne();
    }

    public void patchTokenInfo(TokenEntity _tokenEntity) {
        super.getEntityManager().persist(_tokenEntity);
    }

    @Transactional
    public boolean addJoinUser(UserEntity _userEntity) {
        super.getEntityManager().persist(_userEntity);
        return super.getEntityManager().contains(_userEntity);
    }

    public UserEntity findDuplicateIdByEmail(String _email) {
        return queryFactory
                .selectFrom(userEntity)
                .where(userEntity.vu_email.eq(_email))
                .fetchOne();
    }

    public boolean removeUserWithEmail(UserEntity _userEntity) {
        super.getEntityManager().persist(_userEntity);
        return super.getEntityManager().contains(_userEntity);
    }

}