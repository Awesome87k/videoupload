package com.genesislabs.video.repository;

import com.genesislabs.video.dto.res.UserWithTokenInfoDTO;
import com.genesislabs.video.entity.QTokenEntity;
import com.genesislabs.video.entity.QUserEntity;
import com.genesislabs.video.entity.TokenEntity;
import com.genesislabs.video.entity.UserEntity;
import com.querydsl.core.types.Projections;
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
                .where(userEntity.vu_email.eq(_email))
                .fetchOne();
    }

    public UserEntity findUserByUserInfo(String _email, String _pw) {
        return queryFactory
                .selectFrom(userEntity)
                .where(userEntity.vu_email.eq(_email)
                        .and(userEntity.vu_pw.eq(_pw)))
                .fetchOne();
    }

    public UserEntity findUserByTokeninfo(String _refreshToken) {
        return queryFactory
                .selectFrom(userEntity)
                .join(tokenEntity).on(tokenEntity.vu_idx.eq(userEntity.vu_idx))
                .where(tokenEntity.vt_refresh_token.eq(_refreshToken))
                .fetchOne();
    }

    public TokenEntity findTokenInfoByEmail(String _Email) {
        return queryFactory
                .selectFrom(tokenEntity)
                .join(userEntity).on(tokenEntity.vu_idx.eq(userEntity.vu_idx))
                .where(userEntity.vu_email.eq(_Email))
                .fetchOne();
    }

    public void patchTokenInfo(TokenEntity _tokenEntity) {
        super.getEntityManager().persist(_tokenEntity);
    }

}