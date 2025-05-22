package turbomeca.gamme.assembly.services.schedule.search;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.schedule.service.search.IScheduleInstanciatedListSortingColumnMappingService;

@Service
public class AssemblyScheduleInstanciatedListSortingColumnMappingService 
	implements IScheduleInstanciatedListSortingColumnMappingService {
	
	private static String DEFAULT_SORTING_COLUMN = "instanciatedSchedule.scheduleModification.modificationDate";
	private static Direction DEFAULT_SORTING_DIRECTION = Direction.DESC;
	
	@Override
	public String getSortColumnLabel(Integer sortColumnIndexParam) {
		String sortColumnDatabaseLabel = null;
		
		if (sortColumnIndexParam == null) {
			sortColumnDatabaseLabel = DEFAULT_SORTING_COLUMN;
		} else {
			switch (sortColumnIndexParam) {
				case 0 :
					sortColumnDatabaseLabel = "instanciatedSchedule.orderNumber";
				break;
				case 1 :
					sortColumnDatabaseLabel = "affair";
				break;
				case 2 :
					sortColumnDatabaseLabel = "type";
				break;
				case 3 :
					sortColumnDatabaseLabel = "family";
				break;
				case 4 :
					sortColumnDatabaseLabel = "variants";
				break;
				case 5 :
					sortColumnDatabaseLabel = "material";
				break;
				case 6 :
					sortColumnDatabaseLabel = "instanciatedSchedule.scheduleModification.modificationDate";
				break;
				case 7 :
					sortColumnDatabaseLabel = "instanciatedSchedule.scheduleCreation.creationUser";
				break;
				case 8 :
					sortColumnDatabaseLabel = "instanciatedSchedule.scheduleInstance.ufiStatus";
				break;
				default :
					sortColumnDatabaseLabel = DEFAULT_SORTING_COLUMN;
				break;
			}
		}
		
		return sortColumnDatabaseLabel;
	}

	@Override
	public Direction getSortDirection(Integer sortDirectionParam) {
		Direction sortDirection = null;
		
		if (sortDirectionParam == null) {
			sortDirection = DEFAULT_SORTING_DIRECTION;
		} else {
			switch (sortDirectionParam) {
				case 1 :
					sortDirection = Direction.DESC;
				break;
				case 2 :
					sortDirection = Direction.ASC;
				break;
				default :
					sortDirection = DEFAULT_SORTING_DIRECTION;
				break;
			}
		}
		
		return sortDirection;
	}
	
	
	
}
