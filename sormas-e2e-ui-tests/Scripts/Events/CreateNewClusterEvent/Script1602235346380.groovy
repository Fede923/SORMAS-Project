import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.callTestCase(findTestCase('Login/partials/LoginAsSurveillanceSupervisor'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Events/partials/switchToEvents'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Events/NewEventView/div_New event_btn'))

WebUI.setText(findTestObject('Events/NewEventView/input_DateOfEvent_inputBox'), '05/15/2020')

WebUI.click(findTestObject('Events/NewEventView/label_Cluster_option'))

WebUI.click(findTestObject('Events/NewEventView/div_Disease_v-filterselect-button'))

WebUI.click(findTestObject('Events/NewEventView/td_Disease_COVID-19'))

WebUI.setText(findTestObject('Events/NewEventView/input_Title_eventTitle'), 'Cluster Event')

WebUI.setText(findTestObject('Events/NewEventView/textarea_EventDescription_TArea'), 'This is a Cluster Event')

//select Nosocomial as Yes
//WebUI.click(findTestObject('Events/NewEventView/Nosocomial_label_Yes'))

//WebUI.click(findTestObject('Events/NewEventView/div_Source type_v-filterselect-button'))

//WebUI.click(findTestObject('Events/NewEventView/null'))
//Sending text instead of selecting from drop down due to issue #2447
WebUI.setText(findTestObject('Events/NewEventView/SourceType_Input'), 'Mathematical model')
WebUI.sendKeys(findTestObject('Events/NewEventView/SourceType_Input'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Events/NewEventView/div_TypeOfPlace_v-filterselect-button'))

WebUI.click(findTestObject('Events/NewEventView/td_TypeOfPlace_Festivities_DDItem'))

//WebUI.setText(findTestObject('Events/NewEventView/input_SourceTelephoneNo_inputBox'), '555-35271')
/*WebUI.click(findTestObject('Events/NewEventView/textarea_AddressOrLandmark_TArea'))
WebUI.setText(findTestObject('Events/NewEventView/textarea_AddressOrLandmark_TArea'), 'Am Klammerberg 3')*/

WebUI.setText(findTestObject('Events/NewEventView/input_Street'), 'Rossel street')

WebUI.setText(findTestObject('Events/NewEventView/input_House number'), '71')

WebUI.setText(findTestObject('Events/NewEventView/input_PostalCode_inptBox'), '12345')

WebUI.setText(findTestObject('Events/NewEventView/input_City_inputBox'), 'Klammhausen')

/*WebUI.click(findTestObject('Events/NewEventView/div_AreaType_v-filterselect-button'))

WebUI.click(findTestObject('Events/NewEventView/td_AreaType_Urban_DDItem'))*/

WebUI.click(findTestObject('Events/NewEventView/div_District_v-filterselect-button'))

WebUI.setText(findTestObject('Events/NewEventView/input_district'), 'vorein')

WebUI.sendKeys(findTestObject('Events/NewEventView/input_district'), Keys.chord(Keys.ENTER))

//WebUI.click(findTestObject('Events/NewEventView/td_Voreingestellter Landkreis'))

/*WebUI.click(findTestObject('Events/NewEventView/div_Community_v-filterselect-button'))

WebUI.click(findTestObject('Events/NewEventView/span_Community_CharlottenburgWilmersdorf-DDItem')) */

WebUI.click(findTestObject('ReusableORs/div_Save'))

WebUI.delay(1)

if (isStandalone) {
	WebUI.closeBrowser()
}