package com.drp.social.fbapis.facebook.api;

import com.drp.social.fbapis.Utils;
import com.drp.social.fbapis.facebook.campaigns.CampaignsAPI;
import com.drp.social.fbapis.facebook.store.AccountStore;
import com.drp.social.fbapis.facebook.store.FBTokenStore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by deep.patel on 11/17/16.
 */

@Path("/campaigns")
public class CampaignsService {
    @GET
    @Path("/{apiId}")
    @Produces("application/json")
    public Response getAllCampaigns(@PathParam("apiId") String apiId) {
        com.drp.social.fbapis.response.Response<JsonObject> response = new com.drp.social.fbapis.response.Response<>();
        String fbToken = FBTokenStore.getInstance().getToken(apiId);
        if (fbToken == null) return Response.status(Response.Status.FORBIDDEN).build();
        CampaignsAPI campaignsAPI = new CampaignsAPI(fbToken);
        response.setData(campaignsAPI.getCampaigns(AccountStore.getInstance().getAccounts(apiId)));
        response.setStatus(Response.Status.OK.getStatusCode());
        response.setMessage(Response.Status.OK.getReasonPhrase());
        Gson gson = new Gson();
        String json = gson.toJson(response, com.drp.social.fbapis.response.Response.class);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @POST
    @Path("/create/{apiId}")
    @Consumes("application/json")
    public Response createCampaign(@PathParam("apiId") String apiId, InputStream inputStream) {
        String fbToken = FBTokenStore.getInstance().getToken(apiId);
        if (fbToken == null) return Response.status(Response.Status.FORBIDDEN).build();
        try {
            System.out.println(Utils.getJSONFromStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.CREATED).build();
    }
}
