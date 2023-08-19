package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Report;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class ReportConverter {

	/**
	 * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
	 * @param rv ReportViewのインスタンス
	 * @return Reportのインスタンス
	 */
	public static Report toModel(ReportView rv) {
		return new Report(
				rv.getId(),
				EmployeeConverter.toModel(rv.getEmployee()),
				rv.getReportDate(),
				rv.getTitle(),
				rv.getContent(),
				rv.getCreatedAt(),
				rv.getUpdatedAt(),
				rv.getLikeFlag() == null ? null : rv.getLikeFlag() == AttributeConst.LIKE_ON.getIntegerValue() ? JpaConst.LIKE_ON : JpaConst.LIKE_OFF);
	}

	/**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static ReportView toView(Report r) {

        if (r == null) {
            return null;
        }

        return new ReportView(
                r.getId(),
                EmployeeConverter.toView(r.getEmployee()),
                r.getReportDate(),
                r.getTitle(),
                r.getContent(),
                r.getCreatedAt(),
                r.getUpdatedAt(),
                r.getLikeFlag() == null ? null : r.getLikeFlag() == JpaConst.LIKE_ON ? AttributeConst.LIKE_ON.getIntegerValue() : AttributeConst.LIKE_OFF.getIntegerValue());
    }

	/**
	 * DTOモデルのリストからViewモデルのリストを作成する
	 * @param list DTOモデルのリスト
	 * @return Viewモデルのリスト
	 */
	public static List<ReportView> toViewList(List<Report> list) {
		List<ReportView> rvs = new ArrayList<>();

		for (Report r : list) {
			rvs.add(toView(r));
		}

		return rvs;
	}

	/**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Report r, ReportView rv) {
        r.setId(rv.getId());
        r.setEmployee(EmployeeConverter.toModel(rv.getEmployee()));
        r.setReportDate(rv.getReportDate());
        r.setTitle(rv.getTitle());
        r.setContent(rv.getContent());
        r.setCreatedAt(rv.getCreatedAt());
        r.setUpdatedAt(rv.getUpdatedAt());
        r.setLikeFlag(rv.getLikeFlag());
    }

}