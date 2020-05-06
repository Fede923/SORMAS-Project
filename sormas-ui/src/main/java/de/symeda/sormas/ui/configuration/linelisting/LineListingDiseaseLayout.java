package de.symeda.sormas.ui.configuration.linelisting;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.symeda.sormas.api.Disease;
import de.symeda.sormas.api.i18n.Captions;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.ui.utils.CssStyles;

@SuppressWarnings("serial")
public class LineListingDiseaseLayout extends VerticalLayout {
	
	private final Disease disease;
	private Label lblDisease;
	private Button btnEdit;
	private Button btnDisableAll;
	
	private Runnable editCallback;
	private Runnable disableAllCallback;
	
	public LineListingDiseaseLayout(Disease disease) {
		this.disease = disease;
		
		setSpacing(false);
		setMargin(false);
		
		buildLayout();
	}
	
	public void setEditCallback(Runnable editCallback) {
		this.editCallback = editCallback;
	}
	
	public void setDisableAllCallback(Runnable disableAllCallback) {
		this.disableAllCallback = disableAllCallback;
	}
	
	private void buildLayout() {
		lblDisease = new Label(disease.toString());
		lblDisease.setWidth(100, Unit.PERCENTAGE);
		CssStyles.style(lblDisease, CssStyles.LABEL_ROUNDED_CORNERS, CssStyles.LABEL_BOLD, CssStyles.LABEL_BACKGROUND_FOCUS_LIGHT, CssStyles.ALIGN_CENTER);
		addComponent(lblDisease);
		
		btnEdit = new Button(I18nProperties.getCaption(Captions.lineListingEdit));
		btnEdit.addClickListener(e -> {
			if (editCallback != null) { 
				editCallback.run();
			}
		});
		btnEdit.setWidth(100, Unit.PERCENTAGE);
		CssStyles.style(btnEdit, ValoTheme.BUTTON_PRIMARY, CssStyles.VSPACE_TOP_3);
		addComponent(btnEdit);
		
		btnDisableAll = new Button(I18nProperties.getCaption(Captions.lineListingDisableAll));
		btnDisableAll.addClickListener(e ->  {
			if (disableAllCallback != null) {
				disableAllCallback.run();
			}
		});
		btnDisableAll.setWidth(100, Unit.PERCENTAGE);
		CssStyles.style(btnDisableAll, ValoTheme.BUTTON_PRIMARY, CssStyles.VSPACE_TOP_4);
		addComponent(btnDisableAll);
	}

}
