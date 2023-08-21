package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = JpaConst.TABLE_CLI)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_CLI_GET_ALL,
            query = JpaConst.Q_CLI_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_CLI_COUNT,
            query = JpaConst.Q_CLI_COUNT_DEF),
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity

public class Client {


	/*
	 *  ID
	 */
	@Id
    @Column(name = JpaConst.CLI_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * 顧客名
     */
	@Column(name = JpaConst.CLI_COL_NAME, length = 255, nullable = false)
	private String name;

    /*
     *商談状況（商談中：0 　商談完了：1）
     */
	@Column(name = JpaConst.CLI_COL_SITUATION_FLAG, nullable = false)
	private Integer situationFlag;

	/*
     *登録日時
     */
    @Column(name = JpaConst.CLI_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /*
     * 更新日時
     */
    @Column(name = JpaConst.CLI_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    /*
     *顧客の住所
     */
    @Column(name = JpaConst.CLI_COL_ADDRESS, length = 255, nullable = false)
    private String address;

    /*
     * 顧客の削除管理（論理削除）削除済み: 1　保存中: 0
     */
    @Column(name = JpaConst.CLI_COL_DELETE_FLAG, nullable = false)
    private Integer deleteFlag;

	/*
	 * 顧客情報を登録した従業員
	 */
	@ManyToOne
	@JoinColumn(name = JpaConst.CLI_COL_EMP, nullable = false)
	private Employee employee;

}
