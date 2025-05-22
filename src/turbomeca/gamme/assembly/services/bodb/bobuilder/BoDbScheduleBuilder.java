package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.List;

import turbomeca.gamme.assembly.services.model.data.ScheduleType;
import turbomeca.gamme.ecran.services.bodb.bo.BoSchedule;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;

public class BoDbScheduleBuilder {
	
	private BoDbScheduleBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}
	
	/**
	 * Build bo schedule with edited operation 
	 * @param schedule
	 * @param xmlFlow
	 * @param listIdOperation : if null, build bo schedule with all operations
	 * @return
	 */
	public static BoSchedule buildScheduleBean(ScheduleType schedule, List<String> listIdOperation, List<String> listIdSubPhases, 
			ContextSynchronizeBoData contextSynchroBOData) {
		BoSchedule scheduleBean = null;
		if(schedule != null){
			scheduleBean = new BoSchedule();
			scheduleBean.setArticlePn(schedule.getInstanciation().getPn());
			scheduleBean.setArticleVersion(schedule.getInstanciation().getArticle());
			scheduleBean.setProcessusVersion(BoDbCommonBuilder.getHistoricalLastVersion(schedule.getHistorical()));
			if (schedule.getInstanciation().getState() != null && schedule.getInstanciation().getState().getStatus() != null) {
				scheduleBean.setStatus(schedule.getInstanciation().getState().getStatus().name());
			}
			scheduleBean.setUfiId(contextSynchroBOData.getScheduleName());
			
			scheduleBean.setBoScheduleInfo(BoDbScheduleInfoBuilder.buildScheduleInfo(schedule));
			scheduleBean.setBoArticleInfo(BoDbArticleInfoBuilder.buildArticleInfo(schedule));
			
			scheduleBean.setOrder(BoDbOrderBuilder.buildOrder(schedule.getInstanciation(), contextSynchroBOData.getScheduleState()));
			scheduleBean.setOperations(BoDbOperationBuilder.buildScheduleOperationsBean(
					scheduleBean, schedule, scheduleBean.getOrder().getParts(), 
					listIdOperation, listIdSubPhases, contextSynchroBOData));
			
			scheduleBean.setSn(schedule.getInstanciation().getArticle());
			scheduleBean.setFamily(schedule.getIdentification().getFamily());
			scheduleBean.setVariant(schedule.getInstanciation().getVariant());
			scheduleBean.setSite(schedule.getInstanciation().getSite());
			scheduleBean.setMaterial(schedule.getIdentification().getMaterial());
			
		}
		return scheduleBean;
	}
	
}
