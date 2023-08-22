package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ClientView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.C_ReportService;

public class C_ReportAction extends ActionBase {

	private C_ReportService service;

	/*
	 * メソッドを実行する
	 */
	@Override
	public void process() throws ServletException, IOException {

		service = new C_ReportService();

		invoke();

		service.close();
	}

	public void index() throws ServletException, IOException {

		ClientView client = service.findOne(toNumber(getRequestParam(AttributeConst.CLI_ID)));

		int page = getPage();
		List<ReportView> reports = service.getClientPerPage(client, page);

		long ReportClient = service.countAllClient(client);

		putRequestScope(AttributeConst.REPORTS, reports);
		putRequestScope(AttributeConst.CLI_COUNT, ReportClient);
		putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);

		String flush = getSessionScope(AttributeConst.FLUSH);
		if(flush != null) {
			putRequestScope(AttributeConst.FLUSH, flush);
			removeSessionScope(AttributeConst.FLUSH);
		}

		forward(ForwardConst.FW_CLI_SHOW);
	}

	/*
	 * 商談状況完了フラグを立てる
	 */
	public void update() throws ServletException, IOException {

		 //idを条件に従業員データを論理削除する
        service.update(toNumber(getRequestParam(AttributeConst.CLI_ID)));

        //セッションにいいね完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_SITUATION_TURE.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_CLI, ForwardConst.CMD_INDEX);
		}

	/*
	 * 商談状況完了フラグを取り消す
	 */
	public void destroy() throws ServletException, IOException {

		 //idを条件に従業員データを論理削除する
        service.destroy(toNumber(getRequestParam(AttributeConst.CLI_ID)));

        //セッションに削除完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_SITUATION_FALSE.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_CLI, ForwardConst.CMD_INDEX);
		}
}
