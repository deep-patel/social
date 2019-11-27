package com.drp.social.fbapis.facebook.api;

import com.drp.social.fbapis.facebook.adset.AdSetAPI;
import com.drp.social.fbapis.facebook.store.FBTokenStore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by deep.patel on 11/17/16.
 */

@Path("/adsets")
public class AdSetService {
    @GET
    @Path("/{campaignId}/{apiId}")
    @Produces("application/json")
    public Response getAdSets(@PathParam("campaignId") String campaignId, @PathParam("apiId") String apiId) {
        com.drp.social.fbapis.response.Response<JsonObject> response = new com.drp.social.fbapis.response.Response<>();
        String fbToken = FBTokenStore.getInstance().getToken(apiId);
        if (fbToken == null) return Response.status(Response.Status.FORBIDDEN).build();
        AdSetAPI adSetAPI = new AdSetAPI(fbToken);
        response.setData(adSetAPI.getAdSets(campaignId));
        response.setStatus(Response.Status.OK.getStatusCode());
        response.setMessage(Response.Status.OK.getReasonPhrase());
        Gson gson = new Gson();
        String json = gson.toJson(response, com.drp.social.fbapis.response.Response.class);
        return Response.status(Response.Status.OK).entity(json).build();
    }
}
