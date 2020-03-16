/*******************************************************************************
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.symeda.sormas.ui.contact;

import java.util.Arrays;

import org.joda.time.LocalDate;

import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.Validator;
import com.vaadin.v7.data.validator.DateRangeValidator;
import com.vaadin.v7.shared.ui.datefield.Resolution;
import com.vaadin.v7.ui.ComboBox;
import com.vaadin.v7.ui.DateField;
import com.vaadin.v7.ui.Field;
import com.vaadin.v7.ui.OptionGroup;
import com.vaadin.v7.ui.TextArea;
import com.vaadin.v7.ui.TextField;

import de.symeda.sormas.api.Disease;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.caze.CaseDataDto;
import de.symeda.sormas.api.contact.ContactDto;
import de.symeda.sormas.api.contact.ContactProximity;
import de.symeda.sormas.api.contact.ContactRelation;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.i18n.Validations;
import de.symeda.sormas.api.person.PersonDto;
import de.symeda.sormas.api.user.UserRight;
import de.symeda.sormas.api.user.UserRole;
import de.symeda.sormas.api.utils.Diseases.DiseasesConfiguration;
import de.symeda.sormas.ui.utils.AbstractEditForm;
import de.symeda.sormas.ui.utils.CssStyles;
import de.symeda.sormas.ui.utils.FieldHelper;
import de.symeda.sormas.ui.utils.LayoutUtil;

@SuppressWarnings("serial")
public class ContactCreateForm extends AbstractEditForm<ContactDto> {
	
	private static final String FIRST_NAME = PersonDto.FIRST_NAME;
	private static final String LAST_NAME = PersonDto.LAST_NAME;
	
    private static final String HTML_LAYOUT = 
			LayoutUtil.fluidRowLocs(FIRST_NAME, LAST_NAME) +
			LayoutUtil.fluidRowLocs(ContactDto.LAST_CONTACT_DATE, "") +
			LayoutUtil.fluidRowLocs(ContactDto.CONTACT_PROXIMITY) +
			LayoutUtil.fluidRowLocs(ContactDto.RELATION_TO_CASE) +
			LayoutUtil.fluidRowLocs(ContactDto.RELATION_DESCRIPTION) +
			LayoutUtil.fluidRowLocs(ContactDto.DESCRIPTION) +
					LayoutUtil.fluidRowLocs(ContactDto.CONTACT_OFFICER, "");
    
	private OptionGroup contactProximity;

    public ContactCreateForm(UserRight editOrCreateUserRight) {
        super(ContactDto.class, ContactDto.I18N_PREFIX, editOrCreateUserRight);

		setWidth(540, Unit.PIXELS);
		
		hideValidationUntilNextCommit();
    }
    
    @Override
	protected void addFields() {
    	TextField firstName = addCustomField(FIRST_NAME, String.class, TextField.class);
    	TextField lastName = addCustomField(LAST_NAME, String.class, TextField.class);
   
    	DateField lastContactDate = addField(ContactDto.LAST_CONTACT_DATE, DateField.class);
    	contactProximity = addField(ContactDto.CONTACT_PROXIMITY, OptionGroup.class);
    	contactProximity.removeStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
    	addField(ContactDto.DESCRIPTION, TextArea.class).setRows(2);
    	ComboBox relationToCase = addField(ContactDto.RELATION_TO_CASE, ComboBox.class);
		addField(ContactDto.RELATION_DESCRIPTION, TextField.class);
    	
    	CssStyles.style(CssStyles.SOFT_REQUIRED, firstName, lastName, lastContactDate, contactProximity, relationToCase);

    	ComboBox contactOfficerField = addField(ContactDto.CONTACT_OFFICER, ComboBox.class);
    	contactOfficerField.setNullSelectionAllowed(true);
    	
    	setRequired(true, FIRST_NAME, LAST_NAME);
    	FieldHelper.setVisibleWhen(getFieldGroup(), ContactDto.RELATION_DESCRIPTION, ContactDto.RELATION_TO_CASE, Arrays.asList(ContactRelation.OTHER), true);
    	
    	addValueChangeListener(e -> {

    		updateLastContactDateValidator();
    		
    		// set assignable officers
    		ContactDto contactDto = getValue();
        	if (contactDto != null) {
    	    	CaseDataDto caseDto = FacadeProvider.getCaseFacade().getCaseDataByUuid(contactDto.getCaze().getUuid());
        		updateDiseaseConfiguration(caseDto.getDisease());
    	    	contactOfficerField.addItems(FacadeProvider.getUserFacade().getUserRefsByDistrict(caseDto.getDistrict(), false, UserRole.CONTACT_OFFICER));
        	}
    	});
    }
    
	private void updateRelationDescriptionField(ComboBox relationToCase, TextField relationDescription) {

		boolean otherContactRelation = relationToCase.getValue().equals(ContactRelation.OTHER);
		relationDescription.setVisible(otherContactRelation);
	}

    protected void updateLastContactDateValidator() {
    	Field<?> dateField = getField(ContactDto.LAST_CONTACT_DATE);
    	for (Validator validator : dateField.getValidators()) {
    		if (validator instanceof DateRangeValidator) {
    			dateField.removeValidator(validator);
    		}
    	}
    	if (getValue() != null) {
	    	dateField.addValidator(new DateRangeValidator(I18nProperties.getValidationError(Validations.beforeDate, 
	    			I18nProperties.getPrefixCaption(ContactDto.I18N_PREFIX, ContactDto.LAST_CONTACT_DATE), 
	    			I18nProperties.getPrefixCaption(ContactDto.I18N_PREFIX, ContactDto.REPORT_DATE_TIME)),
	    			null, new LocalDate(getValue().getReportDateTime()).toDate(), Resolution.SECOND));
    	}
    }

	private void updateDiseaseConfiguration(Disease disease) {
		for (Object propertyId : getFieldGroup().getBoundPropertyIds()) {
			boolean visible = DiseasesConfiguration.isDefinedOrMissing(ContactDto.class, (String)propertyId, disease);
			getFieldGroup().getField(propertyId).setVisible(visible);
		}
		
		ContactProximity value = (ContactProximity)contactProximity.getValue();
		FieldHelper.updateEnumData(contactProximity, Arrays.asList(ContactProximity.getValues(disease, FacadeProvider.getConfigFacade().getCountryLocale())));
		contactProximity.setValue(value);
	}
    
    public String getPersonFirstName() {
    	return (String) getField(FIRST_NAME).getValue();
    }

    public String getPersonLastName() {
    	return (String) getField(LAST_NAME).getValue();
    }

	@Override
	protected String createHtmlLayout() {
		 return HTML_LAYOUT;
	}
}
