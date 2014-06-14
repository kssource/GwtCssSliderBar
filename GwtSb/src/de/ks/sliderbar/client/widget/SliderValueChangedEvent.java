package de.ks.sliderbar.client.widget;

public class SliderValueChangedEvent {

	//selected index
	private int value = 0;
//	private boolean isNextEvent = false;
//	private boolean isPrevEvent = false;
//	
//	public boolean isNextEvent() {
//		return isNextEvent;
//	}
//
//	public void setNextEvent(boolean isNextEvent) {
//		this.isNextEvent = isNextEvent;
//	}
//
//	public boolean isPrevEvent() {
//		return isPrevEvent;
//	}
//
//	public void setPrevEvent(boolean isPrevEvent) {
//		this.isPrevEvent = isPrevEvent;
//	}


	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
//	public void addChangeListener(SliderValueChangedListener listener){
//		
//	}
	
}
