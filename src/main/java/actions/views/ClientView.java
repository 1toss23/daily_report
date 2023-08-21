package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class ClientView {

	/*
	 * ID
	 */
	private Integer id;

	/*
	 * 顧客名
	 */
	private String name;

	/*
	 * 商談状況
	 */
	private Integer situationFlag;

	/*
	 *登録日時
	 */
	private LocalDateTime createAt;

	/*
	 * 更新日時
	 */
	private LocalDateTime updateAt;

	/*
	 * 顧客の住所
	 */
	private String address;

	/*
	 * 顧客の論理削除
	 */
	private Integer deleteFlag;

	/*
	 * 顧客情報を登録した従業員
	 */
	private EmployeeView employee;

}
