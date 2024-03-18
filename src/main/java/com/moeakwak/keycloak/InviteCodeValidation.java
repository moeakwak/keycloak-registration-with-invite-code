package com.moeakwak.keycloak;

import org.jboss.logging.Logger;
import jakarta.ws.rs.core.MultivaluedMap;
import org.keycloak.Config;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.*;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

import java.util.List;
import java.util.Map;

public class InviteCodeValidation implements FormAction, FormActionFactory, ServerInfoAwareProviderFactory {
    public static final String PROVIDER_ID = "invite-code-validation";

    private static final Logger LOG = Logger.getLogger(InviteCodeValidation.class);

    @Override
    public String getDisplayType() {
        return "Invite Code Validation";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.DISABLED
        };
    }


    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Require users to enter an invite code to register.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
                .property().name("inviteCode")
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("Invite Code")
                .defaultValue("SET_INVITE_CODE")
                .helpText("The invite code that users must enter to register.")
                .add().build();
    }

    @Override
    public void validate(ValidationContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String inviteCode = formData.getFirst("invite_code");

        String configuredCode = context.getAuthenticatorConfig().getConfig().get("inviteCode");
        LOG.info("User's invite code: " + inviteCode + " configured: " + configuredCode);

        if (inviteCode == null || !inviteCode.equals(configuredCode)) {
            LOG.info("Invalid invite code from user: " + inviteCode + " expected: " + configuredCode);
            context.error("invalid_invite_code");
            context.validationError(formData, List.of(new FormMessage("invite_code", "Invalid invite code. Please try again.")));
            return;
        }

        context.success();
    }

    @Override
    public void success(FormContext context) {
    }

    @Override
    public void buildPage(FormContext context, LoginFormsProvider form) {
        form.setAttribute("inviteCodeRequired", true).createForm("register-invite-code.ftl");
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public FormAction create(KeycloakSession session) {
        return new InviteCodeValidation();
    }


    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        LOG.info("InviteCodeValidation initialized, session factory: " + keycloakSessionFactory);
    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Map<String, String> getOperationalInfo() {
        return Map.of("inviteCode", "The invite code that users must enter to register.");
    }
}