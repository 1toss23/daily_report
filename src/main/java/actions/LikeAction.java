package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.LikeService;

public class LikeAction extends ActionBase {

	private LikeService service;

	/**
	 * メソッドを実行する
	 */
	@Override
	public void process() throws ServletException, IOException {

		service = new LikeService();

		//メソッドを実行
		invoke();
		service.close();
	}

	/*
	 * いいねフラグを立てる
	 */
	public void update() throws ServletException, IOException {

		 //idを条件に従業員データを論理削除する
        service.updateLike(toNumber(getRequestParam(AttributeConst.REP_ID)));

        //セッションに削除完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_LIKED.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
		}

	/*
	 * いいねフラグを取り消す
	 */
	public void destroy() throws ServletException, IOException {

		 //idを条件に従業員データを論理削除する
        service.destroy(toNumber(getRequestParam(AttributeConst.REP_ID)));

        //セッションに削除完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED_LIKE.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
		}
	}
