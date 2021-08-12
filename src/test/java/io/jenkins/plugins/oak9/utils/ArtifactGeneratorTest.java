package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.jenkins.plugins.oak9.model.DesignGap;
import io.jenkins.plugins.oak9.model.Violation;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.jupiter.api.Test;

public class ArtifactGeneratorTest {
    private final String sysLs = System.getProperty("line.separator");

    @Test
    public void testGenerateDesignGapXmlDocument() throws ParserConfigurationException, TransformerException {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + sysLs + "<oak9_output>" + sysLs
                        + "    <design_gaps/>" + sysLs + "</oak9_output>" + sysLs,
                ArtifactGenerator.generateDesignGapXmlDocument(new ArrayList<DesignGap>()));
    }

    @Test
    public void testGenerateDesignGapXmlDocument2() throws ParserConfigurationException, TransformerException {
        DesignGap designGap = new DesignGap();
        designGap.setViolations(new ArrayList<Violation>());

        ArrayList<DesignGap> designGapList = new ArrayList<DesignGap>();
        designGapList.add(designGap);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + sysLs + "<oak9_output>" + sysLs + "    <design_gaps>" + sysLs
                        + "        <design_gap>" + sysLs + "            <requirement_id/>" + sysLs + "            <requirement_name/>" + sysLs
                        + "            <source/>" + sysLs + "            <requirement/>" + sysLs + "            <resource_name/>" + sysLs
                        + "            <resource_id/>" + sysLs + "            <resource_type/>" + sysLs + "            <oak9_guidance/>" + sysLs
                        + "            <violations/>" + sysLs + "        </design_gap>" + sysLs + "    </design_gaps>" + sysLs + "</oak9_output>" + sysLs,
                ArtifactGenerator.generateDesignGapXmlDocument(designGapList));
    }
}

