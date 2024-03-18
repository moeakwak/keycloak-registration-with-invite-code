<#macro inviteCodeField>
    <#if inviteCodeRequired??>
        <div class="${properties.kcFormGroupClass!}">
        <div class="${properties.kcLabelWrapperClass!}">
            <label for="invite_code" class="${properties.kcLabelClass!}">
                Invite Code
            </label> *
        </div>
        <div class="${properties.kcInputWrapperClass!}">
            <span class="${properties.kcInputGroup!}">
                    <input type="text" id="invite_code" name="invite_code" class="${properties.kcInputClass!}"
                           aria-invalid="<#if messagesPerField.existsError('invite_code')>true</#if>"
                    />
            </span>

            <#if messagesPerField.existsError('invite_code')>
                <span id="input-error-invite_code" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
                    ${kcSanitize(messagesPerField.get('invite_code'))?no_esc}
                </span>
            </#if>
        </div>
    </#if>
</#macro>