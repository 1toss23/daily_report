package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ClientView;
import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ClientService;

public class ClientAction extends ActionBase {

	private ClientService service;

	/*
	 * メソッドを実行する
	 */
	@Override
	public void process() throws ServletException, IOException {

		service = new ClientService();

		invoke();

		service.close();
	}

	/*
	 * 顧客の一覧の表示
	 */
	public void index() throws ServletException, IOException {

		int page = getPage();
		List<ClientView> client = service.getAllPerPage(page);

		long clientCount = service.countAll();

		putRequestScope(AttributeConst.CLIENT, client);
		putRequestScope(AttributeConst.CLI_COUNT, clientCount);
		putRequestScope(AttributeConst.PAGE, page);
		putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);

		String flush = getSessionScope(AttributeConst.FLUSH);
		if (flush != null) {
			putRequestScope(AttributeConst.FLUSH, flush);
			removeSessionScope(AttributeConst.FLUSH);
		}

		forward(ForwardConst.FW_CLI_INDEX);
	}

	/*
	 * 詳細を表示
	 */
	public void show() throws ServletException, IOException {

	ClientView cv = service.findOne(toNumber(getRequestParam(AttributeConst.CLI_ID)));

		if(cv == null || cv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {
			forward(ForwardConst.FW_ERR_UNKNOWN);
			return;

		}

		putRequestScope(AttributeConst.CLIENT, cv);

		forward(ForwardConst.FW_CLI_SHOW);
	}

	/*
	 * 新規登録場面の表示
	 */
	public void entryNew() throws ServletException, IOException {

		putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

		//新規登録画面を表示
		forward(ForwardConst.FW_CLI_NEW);
	}

	/*
	 * 編集場面を表示
	 */
	public void edit() throws ServletException, IOException {

		ClientView cv = service.findOne(toNumber(getRequestParam(AttributeConst.CLI_ID)));

		if(cv == null) {
			forward(ForwardConst.FW_ERR_UNKNOWN);

		}else{
			putRequestScope(AttributeConst.TOKEN, getTokenId());
			putRequestScope(AttributeConst.CLIENT, cv);

			forward(ForwardConst.FW_CLI_EDIT);
		}

	}

	public void create() throws ServletException, IOException {

		if (checkToken()) {

			//ログインしている従業員情報を取得
			EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

			ClientView cv = new ClientView(
					null,
					getRequestParam(AttributeConst.CLI_NAME),
					AttributeConst.SIT_SALE.getIntegerValue(),
					null,
					null,
					getRequestParam(AttributeConst.CLI_ADDRESS),
					AttributeConst.DEL_FLAG_FALSE.getIntegerValue(),
					ev);

			List<String> errors = service.create(cv);

			if (errors.size() > 0) {
				putRequestScope(AttributeConst.TOKEN, getTokenId());
				putRequestScope(AttributeConst.CLIENT, cv);
				putRequestScope(AttributeConst.ERR, errors);

				forward(ForwardConst.FW_CLI_NEW);

			} else {

				putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

				redirect(ForwardConst.ACT_CLI, ForwardConst.CMD_INDEX);
			}
		}
	}

	/*
	 * 更新を行う
	 */
	public void update() throws ServletException, IOException {

		if(checkToken()) {
			ClientView cv = service.findOne(toNumber(getRequestParam(AttributeConst.CLI_ID)));

			cv.setName(getRequestParam(AttributeConst.CLI_NAME));
			cv.setAddress(getRequestParam(AttributeConst.CLI_ADDRESS));

			List<String> errors = service.update(cv);

			if(errors.size() > 0) {
				putRequestScope(AttributeConst.TOKEN, getTokenId());
				putRequestScope(AttributeConst.CLIENT, cv);
				putRequestScope(AttributeConst.ERR, errors);

				forward(ForwardConst.FW_ERR_UNKNOWN);

			}else {
				putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

				redirect(ForwardConst.ACT_CLI, ForwardConst.CMD_INDEX);
			}
		}

	}

	/*
	 * 論理削除をする
	 */
	public void destroy() throws ServletException, IOException {

		if(checkToken()) {
			service.destroy(toNumber(getRequestParam(AttributeConst.CLI_ID)));

			putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

			redirect(ForwardConst.ACT_CLI, ForwardConst.CMD_INDEX);
		}
	}
}
