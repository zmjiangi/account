package net.aulang.account.controller;

import net.aulang.account.document.OAuthClient;
import net.aulang.account.oauth.provider.OAuthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"authorizationRequest"})
public class OAuthServerController {
    @Autowired
    private OAuthClientService clientService;
    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    @PostConstruct
    public void init() {
        authorizationEndpoint.setUserApprovalPage("forward:/oauth/approval_page");
        authorizationEndpoint.setErrorPage("forward:/oauth/error_page");
    }

    /**
     * @see org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint
     */
    @RequestMapping("/oauth/approval_page")
    public String approvalPage(Map<String, Object> model, HttpServletRequest request) {
        if (model.containsKey("scopes") || request.getAttribute("scopes") != null) {
            Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request
                    .getAttribute("scopes"));
            List<String> scopeList = new ArrayList<>();
            for (String scope : scopes.keySet()) {
                scopeList.add(scope);
            }
            model.put("scopeList", scopeList);

            AuthorizationRequest authorizationRequest = (AuthorizationRequest)request.getSession().getAttribute("authorizationRequest");
            OAuthClient client = (OAuthClient) clientService.loadClientByClientId(authorizationRequest.getClientId());
            model.put("clientName", client.getClientName());
        } else {

        }
        return "oauth_approval";
    }

    /**
     * @see org.springframework.security.oauth2.provider.endpoint.WhitelabelErrorEndpoint
     */
    @RequestMapping("/oauth/error_page")
    public String handleError(Map<String, Object> model, HttpServletRequest request) {
        Object error = request.getAttribute("error");
        String errorSummary;
        if (error instanceof OAuth2Exception) {
            OAuth2Exception oauthError = (OAuth2Exception) error;
            errorSummary = HtmlUtils.htmlEscape(oauthError.getSummary());
        } else {
            errorSummary = "Unknown error";
        }
        model.put("errorSummary", errorSummary);
        return "oauth_error";
    }
}
