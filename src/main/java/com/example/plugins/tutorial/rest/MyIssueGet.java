package com.example.plugins.tutorial.rest;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.builder.JqlClauseBuilder;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.query.Query;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.ws.rs.core.Context;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A resource of message.
 */
@Named
@Path("/message")
public class MyIssueGet {


    private IssueService issueService;
    private ProjectService projectService;
    private SearchService searchService;
    private TemplateRenderer templateRenderer;
    private JiraAuthenticationContext authenticationContext;
    private ConstantsManager constantsManager;

    private static final String LIST_ISSUES_TEMPLATE = "/templates/listforrest.vm";
    private static final String PROJECT_NAME= "FJP";

    public MyIssueGet(){}

    @Inject
    public MyIssueGet(  IssueService issueService,
                        ProjectService projectService,
                        SearchService searchService,
                        TemplateRenderer templateRenderer,
                        JiraAuthenticationContext authenticationContext,
                        ConstantsManager constantsManager
    ) {
        this.issueService = issueService;
        this.projectService = projectService;
        this.searchService = searchService;
        this.templateRenderer = templateRenderer;
        this.authenticationContext = authenticationContext;
        this.constantsManager = constantsManager;
    }

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response  getList(@Context HttpServletResponse resp) throws IOException {
        //HttpServletResponse resp = null;
        Map<String, Object> context = new HashMap<>();
        resp.setContentType("text/html;charset=utf-8");
        List<Issue> issues = getProjectIssues();
        context.put("issues", issues);
        templateRenderer.render(LIST_ISSUES_TEMPLATE, context, resp.getWriter());
        return Response.ok(issues).build();
    }

    public List<Issue> getProjectIssues() {
        ApplicationUser user = authenticationContext.getLoggedInUser();
        JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
        Query query = jqlClauseBuilder.project(PROJECT_NAME).buildQuery();
        PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();

        SearchResults searchResults = null;
        try {
            searchResults = searchService.search(user, query, pagerFilter);
        } catch (SearchException e) {
            e.printStackTrace();
        }
        return searchResults != null ? searchResults.getIssues() : null;
    }

    @GET
    @Path("/nice")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response health() {
        return Response.ok("ok").build();
    }

    @GET
    @Path("/hello")
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage()
    {
       return Response.ok(new MyIssueGetModel("Hello World")).build();
    }
}