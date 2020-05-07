package de.symeda.sormas.api.clinicalcourse;

import de.symeda.sormas.api.EntityDto;
import de.symeda.sormas.api.utils.DataHelper;
import de.symeda.sormas.api.utils.Order;
import de.symeda.sormas.api.utils.YesNoUnknown;

public class HealthConditionsDto extends EntityDto {
	
	private static final long serialVersionUID = -6688718889862479948L;

	public static final String I18N_PREFIX = "HealthConditions";
	
	public static final String TUBERCULOSIS = "tuberculosis";
	public static final String ASPLENIA = "asplenia";
	public static final String HEPATITIS = "hepatitis";
	public static final String DIABETES = "diabetes";
	public static final String HIV = "hiv";
	public static final String HIV_ART = "hivArt";
	public static final String CHRONIC_LIVER_DISEASE = "chronicLiverDisease";
	public static final String MALIGNANCY_CHEMOTHERAPY = "malignancyChemotherapy";
	public static final String CHRONIC_HEART_FAILURE = "chronicHeartFailure";
	public static final String CHRONIC_PULMONARY_DISEASE = "chronicPulmonaryDisease";
	public static final String CHRONIC_KIDNEY_DISEASE = "chronicKidneyDisease";
	public static final String CHRONIC_NEUROLOGIC_CONDITION = "chronicNeurologicCondition";
	public static final String DOWN_SYNDROME = "downSyndrome";
	public static final String CONGENITAL_SYPHILIS = "congenitalSyphilis";
	public static final String OTHER_CONDITIONS = "otherConditions";
	public static final String IMMUNODEFICIENCY_OTHER_THAN_HIV = "immunodeficiencyOtherThanHiv";
	public static final String CARDIOVASCULAR_DISEASE_INCLUDING_HYPERTENSION = "cardiovascularDiseaseIncludingHypertension";
	
	private YesNoUnknown tuberculosis;
	private YesNoUnknown asplenia;
	private YesNoUnknown hepatitis;
	private YesNoUnknown diabetes;
	private YesNoUnknown hiv;
	private YesNoUnknown hivArt;
	private YesNoUnknown chronicLiverDisease;
	private YesNoUnknown malignancyChemotherapy;
	private YesNoUnknown chronicHeartFailure;
	private YesNoUnknown chronicPulmonaryDisease;
	private YesNoUnknown chronicKidneyDisease;
	private YesNoUnknown chronicNeurologicCondition;
	private YesNoUnknown downSyndrome;
	private YesNoUnknown congenitalSyphilis;
	private YesNoUnknown immunodeficiencyOtherThanHiv;
	private YesNoUnknown cardiovascularDiseaseIncludingHypertension;
	private String otherConditions;
	
	public static HealthConditionsDto build() {
		HealthConditionsDto healthConditions = new HealthConditionsDto();
		healthConditions.setUuid(DataHelper.createUuid());
		return healthConditions;
	}
	
	@Order(1)
	public YesNoUnknown getTuberculosis() {
		return tuberculosis;
	}
	public void setTuberculosis(YesNoUnknown tuberculosis) {
		this.tuberculosis = tuberculosis;
	}
	@Order(2)
	public YesNoUnknown getAsplenia() {
		return asplenia;
	}
	public void setAsplenia(YesNoUnknown asplenia) {
		this.asplenia = asplenia;
	}
	@Order(3)
	public YesNoUnknown getHepatitis() {
		return hepatitis;
	}
	public void setHepatitis(YesNoUnknown hepatitis) {
		this.hepatitis = hepatitis;
	}
	@Order(4)
	public YesNoUnknown getDiabetes() {
		return diabetes;
	}
	public void setDiabetes(YesNoUnknown diabetes) {
		this.diabetes = diabetes;
	}
	@Order(5)
	public YesNoUnknown getImmunodeficiencyOtherThanHiv() {
		return immunodeficiencyOtherThanHiv;
	}
	public void setImmunodeficiencyOtherThanHiv(YesNoUnknown immunodeficiencyOtherThanHiv) {
		this.immunodeficiencyOtherThanHiv = immunodeficiencyOtherThanHiv;
	}
	@Order(10)
	public YesNoUnknown getHiv() {
		return hiv;
	}
	public void setHiv(YesNoUnknown hiv) {
		this.hiv = hiv;
	}
	@Order(11)
	public YesNoUnknown getHivArt() {
		return hivArt;
	}
	public void setHivArt(YesNoUnknown hivArt) {
		this.hivArt = hivArt;
	}
	@Order(12)
	public YesNoUnknown getChronicLiverDisease() {
		return chronicLiverDisease;
	}
	public void setChronicLiverDisease(YesNoUnknown chronicLiverDisease) {
		this.chronicLiverDisease = chronicLiverDisease;
	}
	@Order(13)
	public YesNoUnknown getMalignancyChemotherapy() {
		return malignancyChemotherapy;
	}
	public void setMalignancyChemotherapy(YesNoUnknown malignancyChemotherapy) {
		this.malignancyChemotherapy = malignancyChemotherapy;
	}
	@Order(14)
	public YesNoUnknown getChronicHeartFailure() {
		return chronicHeartFailure;
	}
	public void setChronicHeartFailure(YesNoUnknown chronicHeartFailure) {
		this.chronicHeartFailure = chronicHeartFailure;
	}
	@Order(20)
	public YesNoUnknown getChronicPulmonaryDisease() {
		return chronicPulmonaryDisease;
	}
	public void setChronicPulmonaryDisease(YesNoUnknown chronicPulmonaryDisease) {
		this.chronicPulmonaryDisease = chronicPulmonaryDisease;
	}
	@Order(21)
	public YesNoUnknown getChronicKidneyDisease() {
		return chronicKidneyDisease;
	}
	public void setChronicKidneyDisease(YesNoUnknown chronicKidneyDisease) {
		this.chronicKidneyDisease = chronicKidneyDisease;
	}
	@Order(22)
	public YesNoUnknown getChronicNeurologicCondition() {
		return chronicNeurologicCondition;
	}
	public void setChronicNeurologicCondition(YesNoUnknown chronicNeurologicCondition) {
		this.chronicNeurologicCondition = chronicNeurologicCondition;
	}
	@Order(23)
	public YesNoUnknown getCardiovascularDiseaseIncludingHypertension() {
		return cardiovascularDiseaseIncludingHypertension;
	}
	public void setCardiovascularDiseaseIncludingHypertension(YesNoUnknown cardiovascularDiseaseIncludingHypertension) {
		this.cardiovascularDiseaseIncludingHypertension = cardiovascularDiseaseIncludingHypertension;
	}
	@Order(24)
	public YesNoUnknown getDownSyndrome() {
		return downSyndrome;
	}
	public void setDownSyndrome(YesNoUnknown downSyndrome) {
		this.downSyndrome = downSyndrome;
	}
	@Order(25)
	public YesNoUnknown getCongenitalSyphilis() {
		return congenitalSyphilis;
	}
	public void setCongenitalSyphilis(YesNoUnknown congenitalSyphilis) {
		this.congenitalSyphilis = congenitalSyphilis;
	}
	@Order(26)
	public String getOtherConditions() {
		return otherConditions;
	}
	public void setOtherConditions(String otherConditions) {
		this.otherConditions = otherConditions;
	}
	
}
