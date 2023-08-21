package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.ClientConverter;
import actions.views.ClientView;
import constants.JpaConst;
import models.Client;
import models.validators.ClientValidator;

public class ClientService extends ServiceBase {

	/*
	 * 指定されたページ数の一覧画面に表示するデータを取得し、ClientViewのリストで返却する
	 */
	public List<ClientView> getAllPerPage(int page) {
		List<Client> client = em.createNamedQuery(JpaConst.Q_CLI_GET_ALL, Client.class)
				.setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
				.setMaxResults(JpaConst.ROW_PER_PAGE)
				.getResultList();

		return ClientConverter.toViewList(client);
	}

	/*
	 * 顧客テーブルのデータの件数を取得し、返却
	 */
	public long countAll() {
		long client_count = (long) em.createNamedQuery(JpaConst.Q_CLI_COUNT, Long.class)
				.getSingleResult();
		return client_count;
	}

	/*
	 * 入力された顧客情報をもとに、顧客テーブルを登録
	 */
	public List<String> create(ClientView cv) {

		List<String> errors = ClientValidator.validate(cv);
		if (errors.size() == 0) {
			LocalDateTime now = LocalDateTime.now();
			cv.setCreateAt(now);
			cv.setUpdateAt(now);
			createInternal(cv);
		}

		return errors;
	}

	/*
	 * 顧客テーブルを登録
	 */
	public void createInternal(ClientView cv) {

		em.getTransaction().begin();
		em.persist(ClientConverter.toModel(cv));
		em.getTransaction().commit();
	}

	/*
	 * idを条件に取得したデータをClientViewのインスタンスで返却
	 */
	public ClientView findOne(int id) {
		return ClientConverter.toView(findOneInternal(id));
	}

	/*
	 * idを条件にデータを1件取得する
	 */
	private Client findOneInternal(int id) {
		return em.find(Client.class, id);
	}

	/*
	 * 入力された内容で顧客情報を更新する
	 */
	public List<String> update(ClientView cv) {
		List<String> errors = ClientValidator.validate(cv);

		if (errors.size() == 0) {
			LocalDateTime now = LocalDateTime.now();
			cv.setUpdateAt(now);

			updateInternal(cv);
		}

		return errors;
	}

	/*
	 * 顧客情報の更新
	 */
	private void updateInternal(ClientView cv) {

		em.getTransaction().begin();
		Client c = findOneInternal(cv.getId());
		ClientConverter.copyViewToModel(c, cv);
		em.getTransaction().commit();

	}

	/*
	 * idを条件に論理削除
	 */
	public void destroy(Integer id) {

		ClientView cv = findOne(id);

		LocalDateTime now = LocalDateTime.now();
		cv.setUpdateAt(now);

		cv.setDeleteFlag(JpaConst.CLI_DEL_TRUE);

		update(cv);
	}
}
