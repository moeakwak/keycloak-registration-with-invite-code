<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "form">
        <div class="${properties.kcFormGroupClass!}">
            <div class="${properties.kcLabelWrapperClass!}">
                <label for="invite_code" class="${properties.kcLabelClass!}">Invite Code</label>
            </div>
            <div class="${properties.kcInputWrapperClass!}">
                <input type="text" id="invite_code" name="invite_code" class="${properties.kcInputClass!}" />
            </div>
            <#if messagesPerField.existsError('invite_code')>
                <span id="input-error-invite-code" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
                    ${kcSanitize(messagesPerField.get('invite_code'))?no_esc}
                </span>
            </#if>
        </div>
    </#if>
</@layout.registrationLayout>