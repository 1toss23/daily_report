package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ClientView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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

//	public void index() throws ServletException, IOException {
//
//		ClientView client = (ClientView) getSessionScope(AttributeConst.CLI_ID);
//
//		int page = getPage();
//		List<ReportView> report = service.getClientPerPage(client, page);
//
//		long ReportClient = service.countAllClient(client);
//
//		putRequestScope(AttributeConst.REPORTS, report);
//		putRequestScope(AttributeConst.CLI_COUNT, ReportClient);
//		putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);
//
//		String flush = getSessionScope(AttributeConst.FLUSH);
//		if(flush != null) {
//			putRequestScope(AttributeConst.FLUSH, flush);
//			removeSessionScope(AttributeConst.FLUSH);
//		}
//
//		forward(ForwardConst.FW_CLI_SHOW);
//
//	}

	public void index() throws ServletException, IOException {

		ClientView client = service.findOne(toNumber(getRequestParam(AttributeConst.CLI_ID)))

		int page = getPage();
		List<ReportView> report = service.getClientPerPage(client, page);

		long ReportClient = service.countAllClient(client);

		putRequestScope(AttributeConst.REPORTS, report);
		putRequestScope(AttributeConst.CLI_COUNT, ReportClient);
		putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);

		String flush = getSessionScope(AttributeConst.FLUSH);
		if(flush != null) {
			putRequestScope(AttributeConst.FLUSH, flush);
			removeSessionScope(AttributeConst.FLUSH);
		}

		forward(ForwardConst.FW_CLI_SHOW);

	}

}
