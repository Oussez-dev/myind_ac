package turbomeca.gamme.assembly.services.bodb.bobuilder;

import turbomeca.gamme.assembly.services.model.data.ScheduleType;
import turbomeca.gamme.ecran.services.bodb.bo.BoArticleInfo;

public class BoDbArticleInfoBuilder {

	private BoDbArticleInfoBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

	public static BoArticleInfo buildArticleInfo(ScheduleType schedule) {
		BoArticleInfo info = new BoArticleInfo();

		if (schedule.getInstanciation() != null) {
			info.setArticleReference(schedule.getInstanciation().getArticle());
		}

		if (schedule.getIdentification() != null && schedule.getIdentification().getGeode().length > 0) {
			info.setAvis(schedule.getIdentification().getGeode(0).getNoticeNumber());
			if (schedule.getIdentification().getGeode(0).getDivision() != null) {
				info.setDivision(schedule.getIdentification().getGeode(0).getDivision().getDivision());
			}
		}
		return info;
	}


}
