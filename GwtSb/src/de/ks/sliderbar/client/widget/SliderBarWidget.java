package de.ks.sliderbar.client.widget;


public class SliderBarWidget extends AbstractSliderBar{

	
	public SliderBarWidget(int markersCount) {
		this(markersCount, 0, false, AbstractSliderBar.ORIENTATION_HORIZONTAL, AbstractSliderBar.DEFAULT_CSS_THEME);
	}

	public SliderBarWidget(int markersCount, int value, boolean cyclic, int orientation, String css_theme) {

		this.currentValue = value;
		this.isCyclic = cyclic;
		this.css_theme = css_theme;
		
		if(orientation == AbstractSliderBar.ORIENTATION_HORIZONTAL || orientation == AbstractSliderBar.ORIENTATION_VERICAL){
			//ok
		}else{
			orientation = AbstractSliderBar.ORIENTATION_HORIZONTAL;
		}
		this.sbOrientation = orientation;

		this.setMarkCount(markersCount);
		
		this.updateSelectedStyle();
	}

	
	
	// public interface
	
	public void setMarkCount(int count){
		if(count<0)	count = 0;
		if(count>AbstractSliderBar.MAX_MARKERS_COUNT)	count = AbstractSliderBar.MAX_MARKERS_COUNT;
		markCount = count;
		initSlider();
		updateSelectedStyle();
	}
	
	public int getMarkCount() {
		return markCount;
	}

	public int getValue() {
		return currentValue;
	}
	
	
	public void setValue(int value) {
		if(value>markCount-1)	value = markCount-1;
		if(value<0)	value = 0;
		
		this.currentValue = value;
	}


	public boolean isCyclic() {
		return isCyclic;
	}


	public int getOrientation() {
		return sbOrientation;
	}


	
	public void addChangeListener(SliderValueChangedListener changedListener){
		this.changeListeners.add(changedListener);
	}

	public void removeChangeListener(SliderValueChangedListener changedListener){
		this.changeListeners.remove(changedListener);
	}

}
