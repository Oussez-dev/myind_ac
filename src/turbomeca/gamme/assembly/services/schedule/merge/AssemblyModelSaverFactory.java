package turbomeca.gamme.assembly.services.schedule.merge;

import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.schedule.service.merge.IModelInterfaceFactory;
import turbomeca.gamme.ecran.services.schedule.service.merge.IModelInterfaceSaver;
import turbomeca.gamme.ecran.services.schedule.service.merge.ModelInterfaceSaver;

@Service
public class AssemblyModelSaverFactory implements IModelInterfaceFactory  {

	@Override
	public IModelInterfaceSaver create(int wrintingMode) {
			IModelInterfaceSaver modelInterfaceSaver = null;
			switch (wrintingMode) {
			case 0 :
				break;
			case 1:
				modelInterfaceSaver = new ModelInterfaceSaver();
				break;
			case 2:	
			default:
				AssemblyModelMergeSaver assemblyModelMerger = new AssemblyModelMergeSaver();
				assemblyModelMerger.setModelMerger(new AssemblyModelMerge());
				modelInterfaceSaver = assemblyModelMerger;
				break;
			}
			return modelInterfaceSaver;
		}
		
}
