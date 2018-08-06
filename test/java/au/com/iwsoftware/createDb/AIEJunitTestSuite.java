package au.com.iwsoftware.createDb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import au.com.iwsoftware.sqlDbcon.AddProcessedRequestSeperateTest;
import au.com.iwsoftware.sqlDbcon.AddRequestReceivedTest;
import au.com.iwsoftware.sqlDbcon.AddResponseReceivedTest;
import au.com.iwsoftware.sqlDbcon.GetLogDetailsTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	AddRequestReceivedTest.class,
	AddResponseReceivedTest.class,
//	AddProcessedRequestSeperateTest.class,
//	GetLogDetailsTest.class
	
	
})
public class AIEJunitTestSuite {

}
