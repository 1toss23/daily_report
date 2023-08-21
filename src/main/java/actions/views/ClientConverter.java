package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Client;

public class ClientConverter {

	public static Client toModel(ClientView cv) {

		return new Client(
				cv.getId(),
				cv.getName(),
				cv.getSituationFlag() == null ? null
						: cv.getSituationFlag() == AttributeConst.CLI_SITUATION.getIntegerValue() ? JpaConst.SIT_TURE
								: JpaConst.SIT_FALSE,
				cv.getCreateAt(),
				cv.getUpdateAt(),
				cv.getAddress(),
				cv.getDeleteFlag() == null ? null
						: cv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue() ? JpaConst.CLI_DEL_TRUE
								: JpaConst.CLI_DEL_FALSE,
				EmployeeConverter.toModel(cv.getEmployee()));
	}

	public static ClientView toView(Client c) {

		if (c == null) {
			return null;
		}
		return new ClientView(
				c.getId(),
				c.getName(),
				c.getSituationFlag() == null ? null
						: c.getSituationFlag() == AttributeConst.CLI_SITUATION.getIntegerValue() ? JpaConst.SIT_TURE
								: JpaConst.SIT_FALSE,
				c.getCreatedAt(),
				c.getUpdatedAt(),
				c.getAddress(),
				c.getDeleteFlag() == null ? null
						: c.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue() ? JpaConst.CLI_DEL_TRUE
								: JpaConst.CLI_DEL_FALSE,
				EmployeeConverter.toView(c.getEmployee()));
	}

	public static List<ClientView> toViewList(List<Client> list) {

		List<ClientView> cvs = new ArrayList<>();

		for (Client c : list) {
			cvs.add(toView(c));
		}
		return cvs;
	}

	public static void copyViewToModel(Client c, ClientView cv) {
		c.setId(cv.getId());
		c.setName(cv.getName());
		c.setSituationFlag(cv.getSituationFlag());
		c.setCreatedAt(cv.getCreateAt());
		c.setUpdatedAt(cv.getUpdateAt());
		c.setAddress(cv.getAddress());
		c.setDeleteFlag(cv.getDeleteFlag());
		c.setEmployee(EmployeeConverter.toModel(cv.getEmployee()));
	}
}