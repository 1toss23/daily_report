package services;

import java.util.List;

import actions.views.ClientConverter;
import actions.views.ClientView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Client;
import models.Report;

public class C_ReportService extends ServiceBase {

	/*
	* 顧客情報に関した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
	* @param client 顧客
	* @param page ページ数
	* @return 一覧画面に表示するデータのリスト
	*/
	public List<ReportView> getClientPerPage(ClientView client, int page) {

		List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_CLIENT, Report.class)
				.setParameter(JpaConst.JPQL_PARM_CLIENT, ClientConverter.toModel(client))
				.setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
				.setMaxResults(JpaConst.ROW_PER_PAGE)
				.getResultList();
		return ReportConverter.toViewList(reports);
	}

	/*
	 * 顧客情報に関連した日報データの件数を取得し返却する
	 * @param client
	 * @return 日報データの件数
	 */
	public long countAllClient(ClientView client) {

		long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_CLIENT, Long.class)
				.setParameter(JpaConst.JPQL_PARM_CLIENT, ClientConverter.toModel(client))
				.getSingleResult();

		return count;
	}

	/*
	 * idを条件に取得したデータをclientviewで返却
	 */
	public ClientView findOne(int id) {
		Client c = findOneinternal(id);
		return ClientConverter.toView(c);
	}

	/*
	 * idを条件にデータを1件取得する
	 */
	private Client findOneinternal(int id) {
		Client c = em.find(Client.class, id);
		return c;
	}

	/*
	 * 商談状況のフラグを立てる
	 */
	public void update(int id) {
		//idを条件に登録済みの従業員情報を取得する
				ClientView savedRep = findOne(id);

				//フラグをたてる
				savedRep.setSituationFlag(JpaConst.SIT_TRUE);

				//更新処理を行う
				update(savedRep);
	}

	/*
	 * idを条件に登録済みの従業員情報を取得する
	 */
	private void update(ClientView cv) {
		em.getTransaction().begin();
        Client c = findOneinternal(cv.getId());
        ClientConverter.copyViewToModel(c, cv);
        em.getTransaction().commit();

	}

	/*
	 * 商談状況のフラグを取り消す
	 */
	public void destroy(int id) {
		//idを条件に登録済みの従業員情報を取得する
		ClientView savedRep = findOne(id);

		//フラグを取り消す
		savedRep.setSituationFlag(JpaConst.SIT_FALSE);

		//更新処理を行う
		update(savedRep);
		}
}
