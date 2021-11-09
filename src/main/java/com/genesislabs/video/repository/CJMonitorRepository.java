//package com.genesislabs.video.repository;
//
//import com.genesislabs.video.dto.res.CJManualResDTO;
//import com.genesislabs.video.dto.res.CJSchChDetailResDTO;
//import com.genesislabs.video.dto.res.CJSchDetailResDTO;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class CJMonitorRepository extends QuerydslRepositorySupport {
//
//    public CJMonitorRepository() {
//        super(CJMonitorRepository.class);
//    }
//
//    @Override
//    @PersistenceContext(unitName = "customerEntityManagerFactory")
//    public void setEntityManager(EntityManager entityManager) {
//        super.setEntityManager(entityManager);
//        this.queryFactory = new JPAQueryFactory(entityManager);
//    }
//
//    private JPAQueryFactory queryFactory;
//    private QCJ_channelVO channelVO = QCJ_channelVO.cJ_channelVO;
//    private QCJ_channelinfoVO channelinfoVO = QCJ_channelinfoVO.cJ_channelinfoVO;
//    private QCJ_agroupVO agroupVO = QCJ_agroupVO.cJ_agroupVO;
//    private QCJ_alistVO alistVO = QCJ_alistVO.cJ_alistVO;
//
//    public List<CJManualResDTO> findCjManual() {
//        return queryFactory
//                .select(Projections.fields(
//                        CJManualResDTO.class
//                        , channelVO.ch_tvingid
//                        , channelVO.ch_viewnm
//                        , channelVO.ch_stat
//                        , channelVO.ch_sec
//                ))
//                .from(channelVO)
//                .where(channelVO.ch_delyn.eq("N"))
//                .fetch();
//    }
//
//    public Optional<CJSchChDetailResDTO> findScheduleOfChannelInfo(long sg_idx) {
//        return Optional.ofNullable(queryFactory
//                .select(Projections.fields(
//                        CJSchChDetailResDTO.class
//                        , channelVO.ch_tvingid
//                        , channelVO.ch_viewnm
//                        , agroupVO.sg_start
//                        , agroupVO.sg_end
//                ))
//                .from(agroupVO)
//                .leftJoin(channelVO).on(agroupVO.cj_idx.eq(channelVO.cj_idx))
//                .where(agroupVO.sg_idx.eq(sg_idx))
//                .fetchOne());
//    }
//
//    public List<CJSchDetailResDTO> findScheduleByChannel(long sg_idx) {
//        return queryFactory
//                .select(Projections.fields(
//                        CJSchDetailResDTO.class
//                        , alistVO.sl_start
//                        , alistVO.sl_end
//                        , alistVO.sl_MTRL_NM.as("vod_name")
//                ))
//                .from(agroupVO).on()
//                .leftJoin(channelVO).on(agroupVO.cj_idx.eq(channelVO.cj_idx))
//                .leftJoin(alistVO).on(agroupVO.sg_idx.eq(alistVO.sg_idx))
//                .where(
//                        alistVO.sg_idx.eq(sg_idx)
//                )
//                .orderBy(alistVO.sl_start.asc())
//                .fetch();
//    }
//
//}