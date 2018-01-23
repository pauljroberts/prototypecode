<#-- @ftlvariable name="service" type="com.paul.prototype.model.hippo" -->
{
  "name" : "${service.jcrNodeName}",
  "primaryType" : "publicationsystem:service",
  "mixinTypes" : [ "mix:referenceable" ],
  "properties" : [ {
    "name" : "hippotranslation:locale",
    "type" : "STRING",
    "multiple" : false,
    "values" : [ "en" ]
  }, {
    "name" : "publicationsystem:seosummary",
    "type" : "STRING",
    "multiple" : false,
    "values" : [ "${service.seoSummary}" ]
  }, {
    "name" : "publicationsystem:title",
    "type" : "STRING",
    "multiple" : false,
    "values" : [ "${service.title}" ]
  }, {
    "name" : "publicationsystem:summary",
    "type" : "STRING",
    "multiple" : false,
    "values" : [ "${service.summary}" ]
  }, {
    "name" : "common:searchable",
    "type" : "BOOLEAN",
    "multiple" : false,
    "values" : [ "true" ]
  }, {
    "name" : "publicationsystem:shortsummary",
    "type" : "STRING",
    "multiple" : false,
    "values" : [ "${service.shortSummary}" ]
  }, {
    "name" : "jcr:path",
    "type" : "STRING",
    "multiple" : false,
    "values" : [ "${service.jcrPath}" ]
  }, {
    "name" : "jcr:localizedName",
    "type" : "STRING",
    "multiple" : false,
    "values" : [ "${service.localizedName}" ]
  } ],
  ---- START TEST ----
  <#list service.topTasks as tasks>
    ${tasks.content}
    <#list tasks.docReferences as refs>
      ${refs}<#sep>, </#sep>
    </#list>
  </#list>
  ---- END TEST   ----
  "nodes" : [ {
    "name" : "publicationsystem:toptasks",
    "primaryType" : "hippostd:html",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "hippostd:content",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "<p>Health and care staff can <a href=\"published-upcoming-publication\">access SCR through the Spine web portal</a></p>" ]
    } ],
    "nodes" : [ {
      "name" : "published-upcoming-publication",
      "primaryType" : "hippo:facetselect",
      "mixinTypes" : [ ],
      "properties" : [ {
        "name" : "hippo:facets",
        "type" : "STRING",
        "multiple" : true,
        "values" : [ ]
      }, {
        "name" : "hippo:values",
        "type" : "STRING",
        "multiple" : true,
        "values" : [ ]
      }, {
        "name" : "hippo:docbase",
        "type" : "STRING",
        "multiple" : false,
        "values" : [ "/content/documents/corporate-website/publication-system/published-upcoming-publication" ]
      }, {
        "name" : "hippo:modes",
        "type" : "STRING",
        "multiple" : true,
        "values" : [ ]
      } ],
      "nodes" : [ ]
    } ]
  }, {
    "name" : "publicationsystem:toptasks",
    "primaryType" : "hippostd:html",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "hippostd:content",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "<p>Patients can ask to view or add information to their SCR by visiting their GP practice. For more patient information see <a href=\"http://www.nhs.uk/NHSEngland/thenhs/records/healthrecords/Pages/overview.aspx\">your health records - NHS Choices</a>.</p>" ]
    } ],
    "nodes" : [ ]
  }, {
    "name" : "publicationsystem:toptasks",
    "primaryType" : "hippostd:html",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "hippostd:content",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "<p>These pages are under construction. If you can't find what you are looking for try the Summary Care Record archive pages.</p>" ]
    } ],
    "nodes" : [ ]
  }, {
    "name" : "publicationsystem:introduction",
    "primaryType" : "hippostd:html",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "hippostd:content",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "<p>Access to SCR information means that care in other settings is safer, reducing the risk of prescribing errors. It also helps avoid delays to urgent care.</p>\n\n<p>At a minimum, the SCR holds important information about;</p>\n\n<ul>\n <li>current medication</li>\n <li>allergies and details of any previous bad reactions to medicines</li>\n <li>the name, address, date of birth and NHS number of the patient</li>\n</ul>\n\n<p>The patient can also choose to include&nbsp;<a href=\"https://digital.nhs.uk/summary-care-records/additional-information\">additional information in the SCR</a>, such as details of long-term conditions, significant medical history, or specific communications needs.</p>" ]
    } ],
    "nodes" : [ ]
  }, {
    "name" : "publicationsystem:sections",
    "primaryType" : "publicationsystem:section",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "publicationsystem:title",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "Using SCR" ]
    }, {
      "name" : "publicationsystem:type",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "" ]
    } ],
    "nodes" : [ {
      "name" : "publicationsystem:html",
      "primaryType" : "hippostd:html",
      "mixinTypes" : [ ],
      "properties" : [ {
        "name" : "hippostd:content",
        "type" : "STRING",
        "multiple" : false,
        "values" : [ "<h3>SCR for patients</h3>\n\n<p>If you are registered with a GP practice in England your SCR is created automatically, unless you have opted out. 98% of practices are now using the system. You can talk to your practice about&nbsp;<a href=\"https://digital.nhs.uk/summary-care-records/additional-information\">including additional information</a>&nbsp;to do with long term conditions, care preferences or specific communications needs.</p>\n\n<p><a href=\"https://digital.nhs.uk/summary-care-records/patients\">Read more patient information on SCR</a></p>\n\n<h3>GP information on creating SCRs and including additional information</h3>\n\n<p>The SCR is created automatically through clinical systems in GP practices and uploaded to the&nbsp;<a href=\"https://digital.nhs.uk/spine\">Spine</a>. It will then be updated automatically. When new patients are registered the practice should check they are happy to have an SCR. A&nbsp;<img src=\"https://digital.nhs.uk/resource/themes/dynamic/images/media/pdficon.gif\" alt=\"icon\" /><a href=\"https://digital.nhs.uk/media/1287/sample-SCR-letter-and-consent-form-for-new-patients/pdf/SCRConsentFormDec16\">sample letter for new patients [151.19KB]</a>&nbsp;is available. Additional information can be added to the SCR, with express patient consent, by the GP. The additional information dataset can be included automatically by changing the patient's consent status.</p>\n\n<p>From 1 July 2017, the General Medical Services (GMS) contract requires GPs to identify patients with moderate or severe frailty, and promote the inclusion of additional information in the SCRs of those with severe frailty by seeking their consent to add it. NHS Digital have sent a resource pack,&nbsp;<img src=\"https://digital.nhs.uk/resource/themes/dynamic/images/media/pdficon.gif\" alt=\"icon\" /><a href=\"https://digital.nhs.uk/media/31949/Supporting-the-GMS-Contract-2017-18-SCR-Resource-Pack/pdf/Supporting_the_GMS_Contract_2017-18__SCR_Resource_Pack1\">Supporting Guidance for promoting enriched Summary Care Records for patients with frailty [480.53KB]</a>, to CCGs, to be distributed to GP practices, containing support and guidance on their new duties and how to include additional information in SCRs.</p>\n\n<p><a href=\"https://www.england.nhs.uk/publication/supporting-routine-frailty-identification-and-frailty-through-the-gp-contract-20172018/\">Read NHS England guidance on the requirements to support frailty in the GMS contract 2017-18</a>.&nbsp;</p>\n\n<p><a href=\"https://digital.nhs.uk/summary-care-records/additional-information\">Read more on including additional information in the SCR</a></p>\n\n<h3>Viewing SCRs</h3>\n\n<p>The SCR can be viewed by health and care staff, and viewing is now being rolled out to community pharmacies. SCRs can be viewed through clinical systems or through the&nbsp;<a href=\"https://portal.national.ncrs.nhs.uk/portal/\">SCRa web viewer</a>, from a machine logged in to the secure NHS network, using a&nbsp;<a href=\"https://digital.nhs.uk/Registration-Authorities-and-Smartcards\">smartcard</a>&nbsp;with the appropriate Role Based Access Control codes set.</p>\n\n<p><a href=\"https://digital.nhs.uk/summary-care-records/viewing-SCR\">Read more on viewing SCR and implementing viewing in your organisation</a></p>\n\n<p><a href=\"https://digital.nhs.uk/summary-care-records/community-pharmacy\">Read more on SCR in community pharmacies</a></p>\n\n<h3>Security and the SCR</h3>\n\n<p>Data within the SCR is protected by secure technology. Users must have a&nbsp;<a href=\"https://digital.nhs.uk/Registration-Authorities-and-Smartcards\">smartcard</a>&nbsp;with the correct codes set. Each use is recorded. A patient can ask to see the record of who has looked at their SCR, from the viewing organisation. This is called a 'Subject Access Request'.</p>\n\n<p>Patient data is protected by strict&nbsp;<a href=\"https://digital.nhs.uk/summary-care-records/information-governance\">information governance</a>&nbsp;rules and procedures. Each organisation using the SCR has at least one privacy officer who is responsible for monitoring access and can generate audits and reports.</p>\n\n<p>A patient can also opt out of having an SCR by returning a completed&nbsp;<a href=\"http://webarchive.nationalarchives.gov.uk/20160921135209/http://systems.digital.nhs.uk/scr/library/optout.pdf\">opt-out form</a>&nbsp;to their GP practice.</p>" ]
      } ],
      "nodes" : [ ]
    } ]
  }, {
    "name" : "publicationsystem:sections",
    "primaryType" : "publicationsystem:section",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "publicationsystem:title",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "Information for Clinical Commissioning Groups and others responsible for health planning" ]
    }, {
      "name" : "publicationsystem:type",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "" ]
    } ],
    "nodes" : [ {
      "name" : "publicationsystem:html",
      "primaryType" : "hippostd:html",
      "mixinTypes" : [ ],
      "properties" : [ {
        "name" : "hippostd:content",
        "type" : "STRING",
        "multiple" : false,
        "values" : [ "<p>Use of SCR is featured in NHS England's&nbsp;<a href=\"https://www.england.nhs.uk/publication/univrsl-capabl-info-resources/\">Universal capabilities information and resources</a>&nbsp;for helping organisations to fulfil commitments in their&nbsp;<a href=\"https://www.england.nhs.uk/digitaltechnology/info-revolution/digital-roadmaps/\">Local Digital Roadmaps</a>&nbsp;and&nbsp;<a href=\"https://www.england.nhs.uk/stps/\">Sustainability and Transformation Plans</a>.</p>\n\n<p>SCR helps organisations fulfil capabilities:</p>\n\n<ul>\n <li>A: professionals across care settings can access GP held information on GP prescribed medications, patient allergies and adverse reactions (SCR core functionality)</li>\n <li>B: Clinicians in urgent and emergency care settings can access key GP-held information for patients previously identified by GPs as most likely to present in urgent and emergency care) (SCR with additional information)</li>\n <li>H: Professionals across care settings made aware of end-of-life preference information (SCR with additional information)</li>\n</ul>" ]
      } ],
      "nodes" : [ ]
    } ]
  } ]
}