package turbomeca.gamme.assembly.services.schedule.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.WrapperRangeGeneric;
import turbomeca.gamme.assembly.services.schedule.model.Range;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;

public class WrapperRangeGenericLoader extends AWrapperRangeLoader<Range> implements IWrapperRangeLoader {

	public static final String XPATH_NODE_RANGE = "ranges/range";
	public static final String XPATH_NODE_FILE_NAME = "rangeIdentification/file";

	public WrapperRangeGenericLoader(File file) {
		super(file);
	}

	@Override
	protected String getXPath(ContextUser contextUser) {
		if(contextUser == null) {
			return XPATH_NODE_RANGE;
		} else {
			return XPATH_NODE_RANGE + "[" + XPATH_NODE_FILE_NAME + "='" + contextUser.getRangeName()
					+ "' ]";
		}
	}

	@Override
	protected List<IWrapperRange> createWrappers(ContextUser contextUser, Range range) {
		List<IWrapperRange> rangeWrappers = new ArrayList<IWrapperRange>();
		rangeWrappers.add(new WrapperRangeGeneric(range));
		return rangeWrappers;
	}

	@Override
	protected Class<?> getRangeClass() {
		return Range.class;
	}

}
