package de.symeda.sormas.rest;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.feature.FeatureConfigurationDto;
import de.symeda.sormas.api.user.UserReferenceDto;

@Path("/featureconfigurations")
@Produces({ MediaType.APPLICATION_JSON + "; charset=UTF-8" })
@Consumes({ MediaType.APPLICATION_JSON + "; charset=UTF-8" })
@RolesAllowed("USER")
public class FeatureConfigurationResource extends EntityDtoResource {

	@GET
	@Path("/all/{since}")
	public List<FeatureConfigurationDto> getAllFeatureConfigurations(@Context SecurityContext sc, @PathParam("since") long since) {
		UserReferenceDto userDto = FacadeProvider.getUserFacade().getByUserNameAsReference(sc.getUserPrincipal().getName());
		return FacadeProvider.getFeatureConfigurationFacade().getAllAfter(new Date(since), userDto.getUuid());
	}

	@POST
	@Path("/query")
	public List<FeatureConfigurationDto> getByUuids(@Context SecurityContext sc, List<String> uuids) {
		return FacadeProvider.getFeatureConfigurationFacade().getByUuids(uuids);
	}

	@GET
	@Path("/uuids")
	public List<String> getAllUuids(@Context SecurityContext sc) {
		UserReferenceDto userDto = FacadeProvider.getUserFacade().getByUserNameAsReference(sc.getUserPrincipal().getName());
		return FacadeProvider.getFeatureConfigurationFacade().getAllUuids(userDto.getUuid());
	}
	
	@GET
	@Path("/deleted/{since}")
	public List<String> getDeletedUuids(@Context SecurityContext sc, @PathParam("since") long since) {
		UserReferenceDto userDto = FacadeProvider.getUserFacade().getByUserNameAsReference(sc.getUserPrincipal().getName());
		return FacadeProvider.getFeatureConfigurationFacade().getDeletedUuids(new Date(since), userDto.getUuid());
	}
	
}
