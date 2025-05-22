package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.List;

import turbomeca.gamme.assembly.services.model.data.UserMarkType;
import turbomeca.gamme.ecran.services.bodb.bo.BoUserUfi;
import turbomeca.gamme.ecran.services.bomodel.IBoUserUfi;

public class BoDbUserUfiBuilder {

	public static BoUserUfi buildBeanFromUserMark(UserMarkType userMark) {
		BoUserUfi boUser = null;
		if (userMark != null) {
			boUser = new BoUserUfi();
			boUser.setLogin(userMark.getUser().getLogin());
			boUser.setName(userMark.getUser().getName());
		}
		return boUser;
	}

	public static BoUserUfi getOrbuildBeanFromUserMark(List<IBoUserUfi> listUserUfi, UserMarkType userMark) {
		BoUserUfi boUser = null;
		if(userMark != null) {
			if(listUserUfi != null && !listUserUfi.isEmpty()) {
				for (IBoUserUfi userUfi : listUserUfi) {
					if (userUfi.getLogin().equals(userMark.getUser().getLogin())) {
						boUser = (BoUserUfi)userUfi;
						break;
					}
				}
				
				if(boUser == null){
					boUser = buildBeanFromUserMark(userMark);
					listUserUfi.add(boUser);
				}
			} else {
				boUser = buildBeanFromUserMark(userMark);
				listUserUfi.add(boUser);
			}
		}
		return boUser;
	}

}
