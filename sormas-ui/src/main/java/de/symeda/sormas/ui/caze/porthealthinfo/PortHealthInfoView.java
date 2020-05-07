package de.symeda.sormas.ui.caze.porthealthinfo;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.symeda.sormas.ui.ControllerProvider;
import de.symeda.sormas.ui.caze.AbstractCaseView;

@SuppressWarnings("serial")
public class PortHealthInfoView extends AbstractCaseView {

	public static final String VIEW_NAME = ROOT_VIEW_NAME + "/porthealthinfo";
	
	public PortHealthInfoView() {
		super(VIEW_NAME);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		
		setSubComponent(ControllerProvider.getCaseController().getPortHealthInfoComponent(getCaseRef().getUuid()));
	}
	
}
