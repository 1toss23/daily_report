package services;

import java.util.List;

import actions.views.ClientConverter;
import actions.views.ClientView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Report;

public class C_ReportService extends ServiceBase {

	   /*
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param client 顧客
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getClientPerPage(ClientView client, int page) {


        List<Report> report = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_CLIENT, Report.class)
                .setParameter(JpaConst.JPQL_PARM_CLIENT, ClientConverter.toModel(client))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(report);
    }

    /*
     * 顧客情報に関連した日報データの件数を取得し返却する
     * @param client
     * @return 日報データの件数
     */
    public long countAllClient(ClientView client) {

    	long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_CLIENT, long.class)
    			.setParameter(JpaConst.JPQL_PARM_CLIENT, ClientConverter.toModel(client))
    			.getSingleResult();

    	return count;
    }

}
