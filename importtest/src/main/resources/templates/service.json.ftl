<#-- @ftlvariable name="service" type="uk.nhs.digital.gossmigrator.model.hippo.Service" -->
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
  } ],<#--
   The nodes array will hold rich text components and complex components.
   If will contain one node for each top task, one or no node for introduction
   and a node per each section.  Nodes separated by commas that are suppressed
   if the preceding nodes did not exist.  This relies on the preceding nodes
   bean objects being null.
   -->
  "nodes" : [ <#if service.topTasks??><#list service.topTasks as tasks>{
    "name" : "publicationsystem:toptasks",
    "primaryType" : "hippostd:html",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "hippostd:content",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "${tasks.content}" ]
    } ],
    "nodes" : [ <#list tasks.docReferences as refs> {
      "name" : "${refs.nodeName}",
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
        "values" : [ "${refs.jcrPath}" ]
      }, {
        "name" : "hippo:modes",
        "type" : "STRING",
        "multiple" : true,
        "values" : [ ]
      } ],
      "nodes" : [ ]
    } <#sep>, </#sep> </#list>]
  }<#sep>, </#sep></#list></#if><#if service.introduction??><#if service.topTasks??>, </#if>{
    "name" : "publicationsystem:introduction",
    "primaryType" : "hippostd:html",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "hippostd:content",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "${service.introduction.content}" ]
    } ],
    "nodes" : [ <#list service.introduction.docReferences as refs> {
      "name" : "${refs.nodeName}",
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
        "values" : [ "${refs.jcrPath}" ]
      }, {
        "name" : "hippo:modes",
        "type" : "STRING",
        "multiple" : true,
        "values" : [ ]
      } ],
      "nodes" : [ ]
    }<#sep>, </#sep> </#list>]
  }<#-- End if service.introduction --></#if><#if service.sections??><#if service.introduction?? || service.topTasks??>, </#if><#list service.sections as section>{
    "name" : "publicationsystem:sections",
    "primaryType" : "publicationsystem:section",
    "mixinTypes" : [ ],
    "properties" : [ {
      "name" : "publicationsystem:title",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "${section.title}" ]
    }, {
      "name" : "publicationsystem:type",
      "type" : "STRING",
      "multiple" : false,
      "values" : [ "${section.type}" ]
    } ],
    "nodes" : [ {
      "name" : "publicationsystem:html",
      "primaryType" : "hippostd:html",
      "mixinTypes" : [ ],
      "properties" : [ {
        "name" : "hippostd:content",
        "type" : "STRING",
        "multiple" : false,
        "values" : [ "${section.content.content}" ]
      } ],
      "nodes" : [ <#list section.content.docReferences as refs> {
        "name" : "${refs.nodeName}",
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
          "values" : [ "${refs.jcrPath}" ]
        }, {
          "name" : "hippo:modes",
          "type" : "STRING",
          "multiple" : true,
          "values" : [ ]
        } ],
        "nodes" : [ ]
      }<#sep>, </#sep> </#list>]
    } ]<#-- Closing array section rich text content -->
  }<#-- End list service.sections --><#sep>, </#sep></#list><#-- End if service.sections?? --></#if> ]<#-- Closing array complex nodes -->
}