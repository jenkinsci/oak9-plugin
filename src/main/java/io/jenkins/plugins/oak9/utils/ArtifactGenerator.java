package io.jenkins.plugins.oak9.utils;

import io.jenkins.plugins.oak9.model.DesignGap;
import io.jenkins.plugins.oak9.model.Violation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

public class ArtifactGenerator
{
    public static String generateDesignGapXmlDocument(List<DesignGap> designGaps)
            throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
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
     * Helper method for transforming Document to a string
     * @param xmlDocument the xml doc to convert
     * @return the xml in string form
     * @throws TransformerException thrown when unable to convert to string
     */
    private static String convertDocumentToString(Document xmlDocument) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        // Uncomment if you do not require XML declaration
        // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        //A character stream that collects its output in a string buffer,
        //which can then be used to construct a string.
        StringWriter writer = new StringWriter();

        //transform document to string
        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));

        String xmlString = writer.getBuffer().toString();
        return xmlString;
    }

}
