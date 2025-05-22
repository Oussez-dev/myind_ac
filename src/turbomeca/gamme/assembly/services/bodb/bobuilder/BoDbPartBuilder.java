package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.LinkedHashSet;
import java.util.Set;

import turbomeca.gamme.assembly.services.model.data.SN;
import turbomeca.gamme.ecran.services.bodb.bo.BoOrderService;
import turbomeca.gamme.ecran.services.bodb.bo.BoPart;
import turbomeca.gamme.ecran.services.bomodel.IBoPart;

public class BoDbPartBuilder {
	
	public static final int FIRST_INSTANCE_INDEX = 1;
	
	private BoDbPartBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}
	
	public static Set<BoPart> buildPartBeans(SN[] snComponents, BoOrderService boOrder) {
		Set<BoPart> listParts = new LinkedHashSet<BoPart>();
		int instanceIndex = FIRST_INSTANCE_INDEX;
		
		for (int i = 0; i < snComponents.length; i++){
			SN snComponent = snComponents[i];
			BoPart partBean = new BoPart();
			if (snComponent.getTaskAction().getInputAction().getInputValue() != null) {
				partBean.setSn(snComponent.getTaskAction().getInputAction().getInputValue().getValue());
			}
			partBean.setOrder(boOrder);
			// Instance is set with 1-based-index
			partBean.setInstanceId(instanceIndex);
			
			listParts.add(partBean);
			instanceIndex++;
		}
		
		return listParts;
	}
	
	public static BoPart getPartByInstanceId(Integer instanceId, Set<? extends IBoPart> listBoParts) {
		BoPart result = null;
		if (listBoParts != null && instanceId != null) {
			for(IBoPart listItem :  listBoParts) {
				if( listItem.getInstanceId() != null && listItem.getInstanceId() == instanceId) {
					result = (BoPart)listItem;
					break;
				}
			}
		}
		return result;
	}

}
