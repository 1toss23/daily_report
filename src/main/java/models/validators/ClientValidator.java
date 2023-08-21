package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.ClientView;
import constants.MessageConst;

public class ClientValidator {

	/*
	 *　入力の確認
	 */
	public static List<String> validate(ClientView cv){
		List<String> errors = new ArrayList<String>();

		String nameError = validateName(cv.getName());
		if(!nameError.equals("")) {
			errors.add(nameError);
		}

		String addressError = validateAddress(cv.getAddress());
		if(!addressError.equals("")) {
			errors.add(addressError);
	    }

		return errors;
	}

	/*
	 * 顧客名を入力してるかどうか
	 */
	public static String validateName(String name) {
		if(name == null || name.equals("")) {
			return MessageConst.E_NONAME.getMessage();
		}
		return "";
	}

	/*
	 * 住所の入力してるかどうか
	 */
	public static String validateAddress(String address) {
		if(address == null || address.equals("")) {
			return MessageConst.E_NOADDRESS.getMessage();
		}
		return "";
	}
}
