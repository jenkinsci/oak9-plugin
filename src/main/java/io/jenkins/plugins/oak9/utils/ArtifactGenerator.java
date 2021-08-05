package io.jenkins.plugins.oak9.utils;

import io.jenkins.plugins.oak9.model.DesignGap;
import io.jenkins.plugins.oak9.model.Violation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class ArtifactGenerator
{
    public static String generateDesignGapXmlDocument(List<DesignGap> designGaps)
            throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory documentBuilderFactory = generteSecureDocumentBuilderFactory();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document xmlDocument = documentBuilder.newDocument();
        Element rootElement = xmlDocument.createElement("oak9_output");
        xmlDocument.appendChild(rootElement);

        Element designGapsDOM = xmlDocument.createElement("design_gaps");
        rootElement.appendChild(designGapsDOM);
        for (DesignGap designGap : designGaps) {
            Element currentGap = xmlDocument.createElement("design_gap");
            designGapsDOM.appendChild(currentGap);
            currentGap.appendChild(generateElement(xmlDocument, "requirement_id", designGap.getRequirementId()));
            currentGap.appendChild(generateElement(xmlDocument, "requirement_name", designGap.getRequirementName()));
            currentGap.appendChild(generateElement(xmlDocument, "source", designGap.getSource()));
            currentGap.appendChild(generateElement(xmlDocument, "requirement", designGap.getSource()));
            currentGap.appendChild(generateElement(xmlDocument, "resource_name", designGap.getResourceName()));
            currentGap.appendChild(generateElement(xmlDocument, "resource_id", designGap.getResourceId()));
            currentGap.appendChild(generateElement(xmlDocument, "resource_type", designGap.getResourceType()));
            currentGap.appendChild(generateElement(xmlDocument, "oak9_guidance", designGap.getOak9Guidance()));
            Element violations = xmlDocument.createElement("violations");
            currentGap.appendChild(violations);
            for (Violation violation: designGap.getViolations()) {
                Element currentViolation = xmlDocument.createElement("violation");
                violations.appendChild(currentViolation);
                currentViolation.appendChild(generateElement(xmlDocument,
                        "policy_id", violation.getPolicyId()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "requirement_id", violation.getRequirementId()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "requirement_name", violation.getRequirementName()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "attribute_key", violation.getAttributeKey()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "attribute_value", violation.getAttributeValue()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "oak9_recommended_value", violation.getOak9RecommendedValue()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "error_message", violation.getErrorMessage()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "severity", violation.getSeverity()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "oak9_severity", violation.getOak9Severity()));
                currentViolation.appendChild(generateElement(xmlDocument,
                        "documentation", violation.getDocumentation()));
            }
        }

        return convertDocumentToString(xmlDocument);
    }

    /**
     * Private helper function for writing XML nodes
     *
     * @param xmlDocument the xml document to append to
     * @param name the name of the element
     * @param value the value of the element
     * @return the generated Element
     */
    private static Element generateElement(Document xmlDocument, String name, String value) {
        Element currentElement = xmlDocument.createElement(name);
        currentElement.setTextContent(value);
        return currentElement;
    }

    /**
     * Generate a secure DocumentBuilderFactory as per:
     * https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html
     * @return DocumentBuilderFactory
     */
    private static DocumentBuilderFactory generteSecureDocumentBuilderFactory() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String FEATURE = null;
        // This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all
        // XML entity attacks are prevented
        // Xerces 2 only - http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
        FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
        dbf.setFeature(FEATURE, true);

        // If you can't completely disable DTDs, then at least do the following:
        // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
        // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
        // JDK7+ - http://xml.org/sax/features/external-general-entities
        //This feature has to be used together with the following one, otherwise it will not protect you from XXE for sure
        FEATURE = "http://xml.org/sax/features/external-general-entities";
        dbf.setFeature(FEATURE, false);

        // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
        // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
        // JDK7+ - http://xml.org/sax/features/external-parameter-entities
        //This feature has to be used together with the previous one, otherwise it will not protect you from XXE for sure
        FEATURE = "http://xml.org/sax/features/external-parameter-entities";
        dbf.setFeature(FEATURE, false);

        // Disable external DTDs as well
        FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        dbf.setFeature(FEATURE, false);

        // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
        dbf.setXIncludeAware(false);
        dbf.setExpandEntityReferences(false);

        // And, per Timothy Morgan: "If for some reason support for inline DOCTYPEs are a requirement, then
        // ensure the entity settings are disabled (as shown above) and beware that SSRF attacks
        // (http://cwe.mitre.org/data/definitions/918.html) and denial
        // of service attacks (such as billion laughs or decompression bombs via "jar:") are a risk."
        return dbf;
    }

    /**
     * Helper method for transforming Document to a string
     * @param xmlDocument the xml doc to convert
     * @return the xml in string form
     * @throws TransformerException thrown when unable to convert to string
     */
    private static String convertDocumentToString(Document xmlDocument) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

}
