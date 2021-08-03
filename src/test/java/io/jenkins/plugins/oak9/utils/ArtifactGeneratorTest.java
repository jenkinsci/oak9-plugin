package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.jenkins.plugins.oak9.model.DesignGap;
import io.jenkins.plugins.oak9.model.Violation;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.jupiter.api.Test;

public class ArtifactGeneratorTest {
    @Test
    public void testGenerateDesignGapXmlDocument() throws ParserConfigurationException, TransformerException {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + "<oak9_output>\n"
                        + "    <design_gaps/>\n" + "</oak9_output>\n",
                ArtifactGenerator.generateDesignGapXmlDocument(new ArrayList<DesignGap>()));
    }

    @Test
    public void testGenerateDesignGapXmlDocument2() throws ParserConfigurationException, TransformerException {
        DesignGap designGap = new DesignGap();
        designGap.setViolations(new ArrayList<Violation>());

        ArrayList<DesignGap> designGapList = new ArrayList<DesignGap>();
        designGapList.add(designGap);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + "<oak9_output>\n" + "    <design_gaps>\n"
                        + "        <design_gap>\n" + "            <requirement_id/>\n" + "            <requirement_name/>\n"
                        + "            <source/>\n" + "            <requirement/>\n" + "            <resource_name/>\n"
                        + "            <resource_id/>\n" + "            <resource_type/>\n" + "            <oak9_guidance/>\n"
                        + "            <violations/>\n" + "        </design_gap>\n" + "    </design_gaps>\n" + "</oak9_output>\n",
                ArtifactGenerator.generateDesignGapXmlDocument(designGapList));
    }
}

