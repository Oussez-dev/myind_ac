package turbomeca.gamme.assembly.services.utils;

import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.assembly.services.constants.Constants;

public class RoleConstants {

	public final static String ROLE_CONSULT_LABEL = "ROLE_CONSULT";
	public final static String ROLE_OPERATOR_ASSEMBLY_LABEL = "ROLE_OPERATOR_ASSEMBLY";
	public final static String ROLE_OPERATOR_DISASSEMBLY_LABEL = "ROLE_OPERATOR_DISASSEMBLY";
	public final static String ROLE_CONTROLLER_ASSEMBLY_LABEL = "ROLE_CONTROLLER_ASSEMBLY";
	public final static String ROLE_CONTROLLER_DISASSEMBLY_LABEL = "ROLE_CONTROLLER_DISASSEMBLY";
	public final static String ROLE_REDACTOR_LABEL = "ROLE_REDACTOR";
	public final static String ROLE_ADMIN_LABEL = "ROLE_ADMIN";
	public final static String ROLE_ADMIN_USER_LABEL = "ROLE_ADMIN_USER";
	public final static String ROLE_NOTIFICATION_QUALITY_LABEL = "ROLE_NOTIFICATION_QUALITY";
	public final static String ROLE_NOTIFICATION_METHOD_LABEL = "ROLE_NOTIFICATION_METHOD";
	public final static String ROLE_OPERATOR_ASSEMBLY_ST_LABEL = "ROLE_OPERATOR_ASSEMBLY_ST";
	public final static String ROLE_OPERATOR_DISASSEMBLY_ST_LABEL = "ROLE_OPERATOR_DISASSEMBLY_ST";
	
	public static String getMessageKey(int role) {

		String key = null;
		switch (role) {
		case Constants.ROLE_CONSULT:
			key = "role.consult";
			break;
		case Constants.ROLE_OPERATOR_ASSEMBLY:
			key = "role.operator.assembly";
			break;
		case Constants.ROLE_OPERATOR_DISASSEMBLY:
			key = "role.operator.disassembly";
			break;
		case Constants.ROLE_CONTROLLER_ASSEMBLY:
			key = "role.controller.assembly";
			break;
		case Constants.ROLE_CONTROLLER_DISASSEMBLY:
			key = "role.controller.disassembly";
			break;
		case Constants.ROLE_REDACTOR:
			key = "role.redactor";
			break;
		case Constants.ROLE_ADMIN:
			key = "role.admin";
			break;
		case Constants.ROLE_ADMIN_USER:
			key = "role.admin.user";
			break;
		case Constants.ROLE_NOTIFICATION_QUALITY:
			key = "role.notification.quality";
			break;
		case Constants.ROLE_NOTIFICATION_METHOD:
			key = "role.notification.method";
			break;
		case Constants.ROLE_OPERATOR_ASSEMBLY_ST:
			key = "role.operator.assembly.st";
			break;
		case Constants.ROLE_OPERATOR_DISASSEMBLY_ST:
			key = "role.operator.disassembly.st";
			break;
		}
		return key;
	}
	
	public static int getCodeFromLibelle(String role) {

		if (role.equals(ROLE_CONSULT_LABEL)) {
			return Constants.ROLE_CONSULT;
		} else if (role.equals(ROLE_OPERATOR_ASSEMBLY_LABEL)) {
			return Constants.ROLE_OPERATOR_ASSEMBLY;
		} else if (role.equals(ROLE_OPERATOR_DISASSEMBLY_LABEL)) {
			return Constants.ROLE_OPERATOR_DISASSEMBLY;
		} else if (role.equals(ROLE_CONTROLLER_ASSEMBLY_LABEL)) {
			return Constants.ROLE_CONTROLLER_ASSEMBLY;
		} else if (role.equals(ROLE_CONTROLLER_DISASSEMBLY_LABEL)) {
			return Constants.ROLE_CONTROLLER_DISASSEMBLY;
		} else if (role.equals(ROLE_ADMIN_USER_LABEL)) {
			return Constants.ROLE_ADMIN_USER;
		} else if (role.equals(ROLE_ADMIN_LABEL)) {
			return Constants.ROLE_ADMIN;
		} else if (role.equals(ROLE_REDACTOR_LABEL)) {
			return Constants.ROLE_REDACTOR;
		}else if (role.equals(ROLE_NOTIFICATION_METHOD_LABEL)) {
			return Constants.ROLE_NOTIFICATION_METHOD;
		}else if (role.equals(ROLE_NOTIFICATION_QUALITY_LABEL)) {
			return Constants.ROLE_NOTIFICATION_QUALITY;
		} else if (role.equals(ROLE_OPERATOR_ASSEMBLY_ST_LABEL)) {
			return Constants.ROLE_OPERATOR_ASSEMBLY_ST;
		} else if (role.equals(ROLE_OPERATOR_DISASSEMBLY_ST_LABEL)) {
			return Constants.ROLE_OPERATOR_DISASSEMBLY_ST;
		} else {
			return Constants.ROLE_CONSULT;
		}
	}

	public static List<String> getAllLibelleForAllRole() {
		List<String> allLibelle = new ArrayList<String>();
		allLibelle.add(getMessageKey(Constants.ROLE_ADMIN));
		allLibelle.add(getMessageKey(Constants.ROLE_CONSULT));
		allLibelle.add(getMessageKey(Constants.ROLE_ADMIN_USER));
		allLibelle.add(getMessageKey(Constants.ROLE_OPERATOR_ASSEMBLY));
		allLibelle.add(getMessageKey(Constants.ROLE_OPERATOR_DISASSEMBLY));
		allLibelle.add(getMessageKey(Constants.ROLE_CONTROLLER_ASSEMBLY));
		allLibelle.add(getMessageKey(Constants.ROLE_CONTROLLER_DISASSEMBLY));
		allLibelle.add(getMessageKey(Constants.ROLE_REDACTOR));
		allLibelle.add(getMessageKey(Constants.ROLE_NOTIFICATION_METHOD));
		allLibelle.add(getMessageKey(Constants.ROLE_NOTIFICATION_QUALITY));
		allLibelle.add(getMessageKey(Constants.ROLE_OPERATOR_ASSEMBLY_ST));
		allLibelle.add(getMessageKey(Constants.ROLE_OPERATOR_DISASSEMBLY_ST));
		return allLibelle;
	}

	public static String mapBetweenRolesAndLibelle(String role) {
		if (role.equals("ADMIN")) {
			return "role.admin";
		} else if (role.equals("OPERATOR_ASSEMBLY")) {
			return "role.operator.assembly";
		} else if (role.equals("OPERATOR_DISASSEMBLY")) {
			return "role.operator.disassembly";
		} else if (role.equals("CONTROLLER_ASSEMBLY")) {
			return "role.controller.assembly";
		} else if (role.equals("CONTROLLER_DISASSEMBLY")) {
			return "role.controller.disassembly";
		} else if (role.equals("ADMIN_USER")) {
			return "role.admin.user";
		} else if (role.equals("REDACTOR")) {
			return "role.redactor";
		} else if (role.equals("NOTIFICATION_QUALITY")) {
			return "role.notification.quality";
		} else if (role.equals("NOTIFICATION_METHOD")) {
			return "role.notification.method";
		} else if (role.equals("OPERATOR_ASSEMBLY_ST")) {
			return "role.operator.assembly.st";
		} else if (role.equals("OPERATOR_DISASSEMBLY_ST")) {
			return "role.operator.disassembly.st";
		} else {
			return "role.consult";
		}
	}

	public static String mapLibelle(String role) {
		if (role.equals("role.admin")) {
			return "ADMIN";
		} else if (role.equals("role.consult")) {
			return "CONSULTANT";
		} else if (role.equals("role.operator.assembly")) {
			return "OPERATOR_ASSEMBLY";
		} else if (role.equals("role.operator.disassembly")) {
			return "OPERATOR_DISASSEMBLY";
		} else if (role.equals("role.controller.assembly")) {
			return "CONTROLLER_ASSEMBLY";
		} else if (role.equals("role.controller.disassembly")) {
			return "CONTROLLER_DISASSEMBLY";
		} else if (role.equals("role.admin.user")) {
			return "ADMIN_USER";
		} else if (role.equals("role.redactor")) {
			return "REDACTOR";
		} else if (role.equals("role.notification.method")) {
		    return "NOTIFICATION_METHOD";
		} else if (role.equals("role.notification.quality")) {
			return "NOTIFICATION_QUALITY";		
		} else if (role.equals("role.operator.assembly.st")) {
			return "OPERATOR_ASSEMBLY_ST";
		} else if (role.equals("role.operator.disassembly.st")) {
			return "OPERATOR_DISASSEMBLY_ST";
		} else {
			return "";
		}
	}
	
	public static String[] getCodeFromLibelle(String[] role) {
		String[] result = new String[role.length];
		
		for (int i = 0; i < role.length; i++) {
			result[i] = String.valueOf(getCodeFromLibelle("ROLE_" + role[i]));
		}
		return result;
	}
}
