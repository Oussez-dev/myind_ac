package turbomeca.gamme.assembly.services.bodb.bobuilder;

import turbomeca.gamme.assembly.services.model.data.Instanciation;
import turbomeca.gamme.ecran.services.bodb.bo.BoOrderService;
import turbomeca.gamme.ecran.services.common.enumtypes.ScheduleState;

public class BoDbOrderBuilder {
	
	private BoDbOrderBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

	public static BoOrderService buildOrder(Instanciation scheduleInstanciation, ScheduleState scheduleState) {
		String repository = scheduleState.toString();
		
		BoOrderService orderBean = new BoOrderService();
		orderBean.setOrder(scheduleInstanciation.getOrder());
		orderBean.setRepository(repository);
		orderBean.setCockpit(scheduleInstanciation.getAffair());
		
		orderBean.setParts(BoDbPartBuilder.buildPartBeans(scheduleInstanciation.getSN(), orderBean));
		
		return orderBean;
	}
	
}
