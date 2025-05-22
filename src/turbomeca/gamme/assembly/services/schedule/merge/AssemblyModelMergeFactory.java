package turbomeca.gamme.assembly.services.schedule.merge;

import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.schedule.service.merge.IModelMerge;

@Service
public class AssemblyModelMergeFactory {

	public static IModelMerge create(int rangeType) {
		return  new AssemblyModelMerge();
	}

}
