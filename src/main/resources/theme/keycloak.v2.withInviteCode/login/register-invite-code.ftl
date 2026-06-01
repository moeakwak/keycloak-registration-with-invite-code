<#import "field.ftl" as field>

<#macro inviteCodeField>
    <#if inviteCodeRequired??>
        <@field.input name="invite_code" label="Invite Code" required=true fieldName="invite_code" />
    </#if>
</#macro>
