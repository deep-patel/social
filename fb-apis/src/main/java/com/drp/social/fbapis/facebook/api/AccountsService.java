package com.drp.social.fbapis.facebook.api;

import com.drp.social.fbapis.facebook.accounts.Account;
import com.drp.social.fbapis.facebook.accounts.AccountsAPI;
import com.drp.social.fbapis.facebook.accounts.Application;
import com.drp.social.fbapis.facebook.accounts.Page;
import com.drp.social.fbapis.facebook.store.AccountStore;
import com.drp.social.fbapis.facebook.store.FBTokenStore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by deep.patel on 11/16/16.
 */
@Path("/accounts")
public class AccountsService {
    @GET
    @Path("/{apiId}")
    @Produces("application/json")
    public Response getAllAccount(@PathParam("apiId") String apiId) {
        com.drp.social.fbapis.response.Response<List<Account>> response = new com.drp.social.fbapis.response.Response<>();
        String fbToken = FBTokenStore.getInstance().getToken(apiId);
        if (fbToken == null) return Response.status(Response.Status.FORBIDDEN).build();
        AccountsAPI accountsAPI = new AccountsAPI(fbToken);
        response.setData(accountsAPI.getAllAccounts());
        response.setStatus(Response.Status.OK.getStatusCode());
        response.setMessage(Response.Status.OK.getReasonPhrase());
        Gson gson = new Gson();
        String json = gson.toJson(response, com.drp.social.fbapis.response.Response.class);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @GET
    @Path("pages/{apiId}")
    @Produces("application/json")
    public Response getPages(@PathParam("apiId") String apiId) {
        com.drp.social.fbapis.response.Response<List<Page>> response = new com.drp.social.fbapis.response.Response<>();
        String fbToken = FBTokenStore.getInstance().getToken(apiId);
        if (fbToken == null) return Response.status(Response.Status.FORBIDDEN).build();
        AccountsAPI accountsAPI = new AccountsAPI(fbToken);
        response.setData(accountsAPI.getAllPages());
        response.setStatus(Response.Status.OK.getStatusCode());
        response.setMessage(Response.Status.OK.getReasonPhrase());
        Gson gson = new Gson();
        String json = gson.toJson(response, com.drp.social.fbapis.response.Response.class);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @GET
    @Path("applications/{accountId}/{apiId}")
    @Produces("application/json")
    public Response getApplications(@PathParam("apiId") String apiId, @PathParam("accountId") String accountId) {
        com.drp.social.fbapis.response.Response<List<Application>> response = new com.drp.social.fbapis.response.Response<>();
        String fbToken = FBTokenStore.getInstance().getToken(apiId);
        if (fbToken == null) return Response.status(Response.Status.FORBIDDEN).build();
        AccountsAPI accountsAPI = new AccountsAPI(fbToken);
        List<Account> userAccounts = AccountStore.getInstance().getAccounts(apiId);
        Account account = null;
        System.out.println(apiId);
        System.out.println(userAccounts.size());
        for (Account itAccount : userAccounts) {
            if (itAccount.getAccountId().equalsIgnoreCase(accountId)) {
                account = itAccount;
                break;
            }
        }
        System.out.println(account);
        response.setData(accountsAPI.getAdvertisableApplication(accountId, account));
        response.setStatus(Response.Status.OK.getStatusCode());
        response.setMessage(Response.Status.OK.getReasonPhrase());
        Gson gson = new Gson();
        String json = gson.toJson(response, com.drp.social.fbapis.response.Response.class);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @GET
    @Path("insights/{apiId}")
    @Produces("application/json")
    public Response getInsights(@PathParam("apiId") String apiId) {
        com.drp.social.fbapis.response.Response<JsonObject> response = new com.drp.social.fbapis.response.Response<>();
        String fbToken = FBTokenStore.getInstance().getToken(apiId);
        if (fbToken == null) return Response.status(Response.Status.FORBIDDEN).build();
        AccountsAPI accountsAPI = new AccountsAPI(fbToken);
        response.setData(accountsAPI.getAccountInsights(AccountStore.getInstance().getAccounts(apiId)));
        response.setStatus(Response.Status.OK.getStatusCode());
        response.setMessage(Response.Status.OK.getReasonPhrase());
        Gson gson = new Gson();
        String json = gson.toJson(response, com.drp.social.fbapis.response.Response.class);
        return Response.status(Response.Status.OK).entity(json).build();
    }
}
