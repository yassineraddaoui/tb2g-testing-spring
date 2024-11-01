package org.springframework.samples.petclinic.sfg;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, YannyConfig.class})
@ActiveProfiles("base-test")
public class hearingIntrepreterTestY {
    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    public void testYanny() {
        var result = hearingInterpreter.whatIHeard();
        assertEquals("Yanny", result);

    }
}
