import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;


public class MeetingUTest {
	
	@Test
	public void toString_withAString_shouldKeeIt(){
		//setup
		Meeting truc = new Meeting(new Date(), 8000, "Formation TDD Octo  ");
		
		//action
		String inString = truc.toString();
		
		//assert
		Assert.assertEquals("Formation TDD Octo  ", inString);
		
	}
	@Test
	public void toString_withHtmlBalise_shouldKeeIt(){
		//setup
		Meeting truc = new Meeting(new Date(), 8000, "<b>Formation TDD</b> Octo  ");
		
		//action
		String inString = truc.toString();
		
		//assert
		Assert.assertEquals("<b>Formation TDD</b> Octo  ", inString);
		
	}
}
