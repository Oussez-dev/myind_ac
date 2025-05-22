package turbomeca.gamme.assembly.services.bodb.bobuilder;

import turbomeca.gamme.assembly.services.model.data.Identification;
import turbomeca.gamme.assembly.services.model.data.ScheduleType;
import turbomeca.gamme.ecran.services.bodb.bo.BoScheduleInfo;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusType;

public class BoDbScheduleInfoBuilder {

	private BoDbScheduleInfoBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

	public static BoScheduleInfo buildScheduleInfo(ScheduleType schedule) {
		BoScheduleInfo info = new BoScheduleInfo();

		info.setSchedule("ZMO");

		if (schedule.getIdentification() != null && schedule.getIdentification().getGeode().length > 0) {
			info.setScheduleType(buildScheduleType(schedule.getIdentification()));
			info.setGeodReference(schedule.getIdentification().getGeode(0).getReference());
			info.setGeodVersion(schedule.getIdentification().getGeode(0).getVersion());
		}

		if (schedule.getInstanciation() != null && schedule.getInstanciation().getState() != null) {
			info.setStatus(StatusType.valueOf(schedule.getInstanciation().getState().getStatus().value()));
		}

		return info;
	}

	private static String buildScheduleType(Identification identification) {
		StringBuilder builder = new StringBuilder();
		String scheduleType = identification.getGeode(0).getScheduleType();
		if (scheduleType != null) {
			builder.append(scheduleType);

			if (scheduleType.compareTo("T") == 0) {
				//			instanciation.getS
			} else {
				builder.append("A");
			}
		}
		return builder.toString();
	}


}
