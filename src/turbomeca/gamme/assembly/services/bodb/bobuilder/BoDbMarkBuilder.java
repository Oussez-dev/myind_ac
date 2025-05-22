package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import turbomeca.gamme.assembly.services.bodb.utils.BoDbUtil;
import turbomeca.gamme.assembly.services.model.data.Derogation;
import turbomeca.gamme.assembly.services.model.data.Mark;
import turbomeca.gamme.assembly.services.model.data.Marks;
import turbomeca.gamme.assembly.services.model.data.PN;
import turbomeca.gamme.assembly.services.model.data.SN;
import turbomeca.gamme.ecran.services.bodb.bo.BoMark;
import turbomeca.gamme.ecran.services.bodb.bo.BoPart;
import turbomeca.gamme.ecran.services.bodb.bo.BoSubPhase;
import turbomeca.gamme.ecran.services.bomodel.IBoPart;

public class BoDbMarkBuilder {

	private BoDbMarkBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

	public static List<BoMark> buildMarks(Marks marks, Set<? extends IBoPart> boParts, BoSubPhase boSubphase) {
		List<BoMark> result = new ArrayList<BoMark>();
		if (marks != null) {
			for (Mark mark : marks.getMark()) {
				result.addAll(buildBoMarks(mark, boParts, boSubphase));
			}
		}
		return result;
	}

	public static List<BoMark> buildBoMarks(Mark mark, Set<? extends IBoPart> boParts, BoSubPhase boSubphase) {
		List<BoMark> result = new ArrayList<BoMark>();

		for (IBoPart part : boParts) {
			
			BoMark boMark = new BoMark();
			boMark.setUfiId(mark.getId());
			
			PN pn = findPNinMark(part.getInstanceId(), mark);
			if (pn != null) {
				boMark.setPn(BoDbUtil.getTaskActionValue(pn.getTaskAction()));
			}
			
			SN sn = findSNinMark(part.getInstanceId(), mark);
			if (sn != null) {
				boMark.setSn(BoDbUtil.getTaskActionValue(sn.getTaskAction()));
			}
			
			boMark.setPart((BoPart) part);
			
			
			Derogation derogation = findDerogationInMark(part.getInstanceId(), mark);
			if (derogation != null) {
				boMark.setDerogation(BoDbUtil.getTaskActionValue(derogation.getTaskAction()));
			}

			boMark.setCompound(mark.getCompound());
			//		boMark.setSubCompond(subCompond); FIXME TBD ???

			boMark.setDrawingMarker(mark.getDrawingMarker());
			boMark.setAta(mark.getAta());

			if (mark.getDesignation() != null) {
				boMark.setDesignation(BoDbUtil.buildTextFromParaList(mark.getDesignation().getPara()));
			}

			boMark.setQuantity(BoDbUtil.buildQuantity(mark.getQuantity()));

			if (mark.getSpare() != null) {
				boMark.setIsSpare(Boolean.valueOf(mark.getSpare().value()));
			}
			if (mark.getSystematic() != null) {
				boMark.setIsSystematic(Boolean.valueOf(mark.getSystematic().value()));
			}
			if (mark.getTechnicalAlternative() != null) {
				boMark.setIsAlternative(Boolean.valueOf(mark.getTechnicalAlternative().value()));
			}
			boMark.setSubphase(boSubphase);
			result.add(boMark);

		}

		return result;
	}
	
	public static PN findPNinMark(Integer instanceId, Mark mark) {
		PN result = null;

		for (PN pn : mark.getPN()) {
			if (pn.getTaskAction() != null && pn.getTaskAction().getInstance() == instanceId) {
				result = pn;
			}
		}
		return result;
	}

	public static SN findSNinMark(Integer instanceId, Mark mark) {
		SN result = null;

		for (SN sn : mark.getSN()) {
			if (sn.getTaskAction() != null && sn.getTaskAction().getInstance() == instanceId) {
				result = sn;
			}
		}
		return result;
	}
	
	public static Derogation findDerogationInMark(Integer instanceId, Mark mark) {
		Derogation result = null;

		for (Derogation derogation : mark.getDerogation()) {
			if (derogation.getTaskAction() != null && derogation.getTaskAction().getInstance() == instanceId) {
				result = derogation;
			}
		}
		return result;
	}

}
