import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NoteRetriever.class })
public class SchedulerUTest {

	@Test
	public void addEvent_withEventToday_shouldBeAdded() {
		// Setup
		Scheduler scheduler = new Scheduler("Robert");
		Date now = new Date();
		Event event = new Event(now, 0);
		MailService mailerMocked = Mockito.mock(MailService.class);
		SchedulerDisplay displayMocked = Mockito.mock(SchedulerDisplay.class);
		scheduler.setDisplay(displayMocked);
		scheduler.setMailService(mailerMocked);
		
		// Test
		scheduler.addEvent(event);

		// Asserts
		Assert.assertTrue(scheduler.hasEvents(now));
		Mockito.verify(mailerMocked);
		Mockito.verify(displayMocked);
	}

	@Test
	public void addEvent_withEventAnterior_shouldNotBeAdded() {
		// Setup
		Scheduler scheduler = new Scheduler("Robert");
		int aDayInMillis = 86400000;
		Date yesterday = new Date(new Date().getTime() - aDayInMillis);
		Event event = new Event(yesterday, 0);
		MailService mailerMocked = Mockito.mock(MailService.class);
		SchedulerDisplay displayMocked = Mockito.mock(SchedulerDisplay.class);
		scheduler.setDisplay(displayMocked);
		scheduler.setMailService(mailerMocked);

		// Test
		scheduler.addEvent(event);

		// Asserts
		Assert.assertFalse(scheduler.hasEvents(yesterday));
		Mockito.verify(mailerMocked);
		Mockito.verify(displayMocked);
	}

	@Test
	public void update_withFullNote_shouldAppendItToText() throws Exception {
		// Setup
		Date now = new Date();
		Scheduler scheduler = new Scheduler("Michel");
		List<Event> events = new ArrayList<Event>();
		events.add(new Meeting(now, 8000, "test"));
		scheduler.setEvents(events);
		
		PowerMockito.mockStatic(NoteRetriever.class);
		PowerMockito.when(NoteRetriever.get_note(now)).thenReturn(" test");

		// Test
		scheduler.update();

		// Asserts
		PowerMockito.verifyStatic();
		// Sanity Check
		Assert.assertEquals(1, events.size());
		Assert.assertTrue(events.get(0) instanceof Meeting);

		Meeting meeting = (Meeting) events.get(0);
		Assert.assertEquals("test test", meeting.getText());

	}

	@Test
	public void update_withEmptyNote_shouldIgnoreIt() throws Exception {
		// Setup
		Date now = new Date();
		Scheduler scheduler = new Scheduler("Robert");
		List<Event> events = new ArrayList<Event>();
		events.add(new Meeting(now, 8000, "test"));
		scheduler.setEvents(events);
		PowerMockito.mockStatic(NoteRetriever.class);
		PowerMockito.when(NoteRetriever.get_note(now)).thenReturn("      ");

		// Test
		scheduler.update();

		// Asserts
		// Sanity Check
		Assert.assertEquals(1, events.size());
		Assert.assertTrue(events.get(0) instanceof Meeting);

		Meeting meeting = (Meeting) events.get(0);
		Assert.assertEquals("test", meeting.getText());

	}
	
	

}
