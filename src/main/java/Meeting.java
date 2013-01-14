import java.util.Date;

public class Meeting extends Event {
	
	private String description;
	
	public Meeting(Date date, int slot, String description) {
		super(date, slot);
		this.description = description;
	}

	public String getText() {
		return description;
	}

	public boolean isPastDue() {
		return TimeServices.isPastDue(getDate());
	}
	
	public String toString() {
		return description;
		
	}

  

    public void appendToText(String newText)
	{
		description += newText;
	}
}
