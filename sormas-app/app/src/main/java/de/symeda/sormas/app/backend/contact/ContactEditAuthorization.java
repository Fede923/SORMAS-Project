package de.symeda.sormas.app.backend.contact;

import java.util.Set;

import de.symeda.sormas.api.user.UserRole;
import de.symeda.sormas.app.backend.caze.Case;
import de.symeda.sormas.app.backend.caze.CaseEditAuthorization;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.config.ConfigProvider;
import de.symeda.sormas.app.backend.user.User;

public class ContactEditAuthorization {

<<<<<<< HEAD
    public static boolean isContactEditAllowed(Contact contact) {
        User user = ConfigProvider.getUser();

        if (user.getUuid().equals(contact.getReportingUser().getUuid())){
            return true;
        }

        if (contact.getCaseUuid() != null) {
            Case caseOfContact = DatabaseHelper.getCaseDao().queryUuidBasic(contact.getCaseUuid());
            if (caseOfContact != null && CaseEditAuthorization.isCaseEditAllowed(caseOfContact)) {
                return true;
            }
        }

        if (contact.getRegion() != null && ConfigProvider.hasRole(UserRole.getSupervisorRoles())) {
            return contact.getRegion().equals(user.getRegion());
        }

        if (contact.getDistrict() != null && ConfigProvider.hasRole(UserRole.getOfficerRoles())) {
=======
    public static boolean isContactEditAllowed(Contact contact){

        User user = ConfigProvider.getUser();

        if (user.getUuid().equals(contact.getReportingUser().getUuid())){
            return true;
        }

        if (contact.getCaseUuid() != null) {
            Case caseofContact = DatabaseHelper.getCaseDao().queryUuidBasic(contact.getCaseUuid());
            if (caseofContact != null && CaseEditAuthorization.isCaseEditAllowed(caseofContact)) {
                return true;
            }
        }

        if (ConfigProvider.hasRole(UserRole.getSupervisorRoles())) {
            return contact.getRegion().equals(user.getRegion());
        }

        if (ConfigProvider.hasRole(UserRole.getOfficerRoles())) {
>>>>>>> branch 'development' of https://github.com/Fede923/SORMAS-Project
            return contact.getDistrict().equals(user.getDistrict());
        }

        if (ConfigProvider.hasRole(UserRole.NATIONAL_USER)) {
            return true;
        }

        return false;
    }
}
