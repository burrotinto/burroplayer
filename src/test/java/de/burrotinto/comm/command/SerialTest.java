//package de.burrotinto.comm.command;
//
//import de.burrotinto.comm.SerialValue;
//import org.axonframework.test.aggregate.AggregateTestFixture;
//import org.axonframework.test.aggregate.FixtureConfiguration;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.util.Collections;
//
///**
// * Created by derduke on 26.03.17.
// */
//public class SerialTest {
//    public final SerialId serialId = new SerialId(new SerialValue("",0));
//    private FixtureConfiguration<Serial> fixture;
//
//    @Before
//    public void setUp() throws Exception {
//        fixture = new AggregateTestFixture<>(Serial.class);
//    }
//
//    @Test
////    @Ignore
//    public void create(){
////        SerialApi.CreateSerialCommand command = new SerialApi.CreateSerialCommand(serialId, Collections.EMPTY_SET,
////                Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET);
////
////        fixture.givenNoPriorActivity().when(command).expectEvents(new SerialApi.SerialCreatedEvent(serialId, Collections.EMPTY_SET,
////                Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET));
//    }
//}
