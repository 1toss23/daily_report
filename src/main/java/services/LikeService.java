package services;

import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Report;

public class LikeService extends ServiceBase {



	/**
	* idを条件に取得したデータをReportViewのインスタンスで返却する
	* @param id
	* @return 取得データのインスタンス
	*/

	public ReportView findOne(int id) {
		return ReportConverter.toView(findOneInternal(id));
	}

	/**
	 * idを条件にデータを1件取得する
	 * @param id
	 * @return 取得データのインスタンス
	 */

	private Report findOneInternal(int id) {
		return em.find(Report.class, id);
	}

	public void destroy(Integer id) {

		//idを条件に登録済みの従業員情報を取得する
		ReportView savedRep = findOne(id);

		//フラグをたてる
		savedRep.setLikeFlag(JpaConst.LIKE_OFF);

		//更新処理を行う
		update(savedRep);

	}

	public void updateLike(Integer id) {

		//idを条件に登録済みの従業員情報を取得する
		ReportView savedRep = findOne(id);

		//フラグをたてる
		savedRep.setLikeFlag(JpaConst.LIKE_ON);

		//更新処理を行う
		update(savedRep);

	}

	 private void update(ReportView rv) {

	        em.getTransaction().begin();
	        Report r = findOneInternal(rv.getId());
	        ReportConverter.copyViewToModel(r, rv);
	        em.getTransaction().commit();

	    }


}
