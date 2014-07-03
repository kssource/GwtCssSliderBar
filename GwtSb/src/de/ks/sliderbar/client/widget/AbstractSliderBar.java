package de.ks.sliderbar.client.widget;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public abstract class AbstractSliderBar extends FocusPanel{

	public static final String DEFAULT_CSS_THEME = "sbTheme1";
	public static final int MAX_MARKERS_COUNT = 100;
	public static final int ORIENTATION_VERICAL = 2;
	public static final int ORIENTATION_HORIZONTAL = 1;

	
	protected int markCount = 0;
	protected int currentValue = 0;
	protected boolean isCyclic = false;
	protected int sbOrientation = AbstractSliderBar.ORIENTATION_HORIZONTAL;
	
	protected String css_theme = AbstractSliderBar.DEFAULT_CSS_THEME;

	protected ArrayList<SliderValueChangedListener> changeListeners = new ArrayList<>();
	private ArrayList<Panel> markArr;
	
	private void fireChangedEvent(SliderValueChangedEvent changedEvent){
		for (SliderValueChangedListener sliderValueChangedListener : changeListeners) {
			sliderValueChangedListener.onChange(changedEvent);
		}
	}

	protected void initSlider(){
		this.addStyleName(css_theme);
		Panel orientContainer = new FlowPanel();
		if(sbOrientation == ORIENTATION_HORIZONTAL){
			orientContainer.addStyleName(CssNames.HORIZ_PANEL);
		}else{
			orientContainer.addStyleName(CssNames.VERTICAL_PANEL);
		}
		
		this.clear();
		this.add(orientContainer);
		Panel prevButton = createPrevButton();
		orientContainer.add(prevButton);
		
		
		Panel markContainer = createSbMarkContainer();
		orientContainer.add(markContainer);
		
		Panel nextButton = createNextButton();
		orientContainer.add(nextButton);

//		bindAdditionalPrevNextButtons();
	}

	
	@SuppressWarnings("deprecation")
	public boolean bindAdditionalPrevNextButtons() {
		boolean prevBind=false, nextBind=false;
		
		RootPanel addNextButton = RootPanel.get(CssNames.SB_ADDITIONAL_NEXT_BUTTON_ID);
		
		if(addNextButton != null){
			Element nextAsElem = addNextButton.getElement();
			Label nextWrapper = Label.wrap(nextAsElem);
			nextWrapper.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					actionNext();
				}
			});
		}

		RootPanel addPrevButton = RootPanel.get(CssNames.SB_ADDITIONAL_PREV_BUTTON_ID);
		
		if(addPrevButton != null){
			Element buttAsElem = addPrevButton.getElement();
			Label nextWrapper = Label.wrap(buttAsElem);
			nextWrapper.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					actionPrev();
				}
			});
		}
		
		boolean successBind = prevBind && nextBind;
		
		return successBind;
	}

	private Panel createSbMarkContainer(){
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.addStyleName(CssNames.SB_MARK_BAR_CONTAINER);
		
		markArr = new ArrayList<Panel>();	
		for(int i=0; i<markCount; ++i){
			Panel mark = createSbMark(i);
			flowPanel.add(mark);
			markArr.add(mark);
		}

		return flowPanel;
	}

	private Panel createSbMark(final int index){
		FocusPanel panel = new FocusPanel();
		panel.addStyleName(CssNames.SB_MARK_WRAPPER);

//		if(index==0){//init
//			panel.addStyleName(CssNames.SB_MARK_SELECTED);
//		}
		
		panel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectMark(index);
			}
		});
		
		panel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				actionKeyDown(event);
				
			}
		});
		
		HTML numberHtml = new HTML(""+(index+1));
		SimplePanel numberPanel = new SimplePanel(numberHtml);
		numberPanel.addStyleName(CssNames.SB_MARK_NUMBER);
		panel.add(numberPanel);	

		return panel;
	}

	private Panel createPrevButton(){
		FocusPanel panel = new FocusPanel();
		panel.addStyleName(CssNames.SB_BUTTON);
		panel.addStyleName(CssNames.PREV_BUTTON);
		
		panel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				actionPrev();
			}
		});
		
		panel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				actionKeyDown(event);
				
			}
		});

		return panel;
	}
	
	private Panel createNextButton(){
		FocusPanel panel = new FocusPanel();
		panel.addStyleName(CssNames.SB_BUTTON);
		panel.addStyleName(CssNames.NEXT_BUTTON);
		
		panel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				actionNext();
			}
		});
		
		panel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				actionKeyDown(event);
				
			}
		});

		return panel;
	}
	
	public void actionKeyDown(KeyDownEvent event) {
		event.preventDefault();
		int kCode = event.getNativeKeyCode();
//		System.out.println("actionKeyDown, keyCode: "+kCode);
		switch (kCode) {
		case KeyCodes.KEY_END:
			selectMark(markCount-1);
			break;
		case KeyCodes.KEY_HOME:
			selectMark(0);
			break;
		case KeyCodes.KEY_LEFT:
		case KeyCodes.KEY_UP:
			actionPrev();
			break;
		case KeyCodes.KEY_RIGHT:
		case KeyCodes.KEY_DOWN:
		case KeyCodes.KEY_ENTER:
		case KeyCodes.KEY_SPACE:
			actionNext();
			break;

		default:
			break;
		}
		
	}

	public void actionPrev(){
//		System.out.println("actionPrev");
		selectMark(currentValue-1);
	}

	public void actionNext(){
//		System.out.println("actionNext");
		selectMark(currentValue+1);
	}

	public void selectMark(int index){
//		System.out.println("in selectMark: "+index);
		
		int oldSelIndex = currentValue;
		if(markCount == 0)	return;
		
		int highestIndex = markCount-1;
		
		if(index<=0){
			currentValue = 0;
			if(index<0 && isCyclic){
				currentValue = highestIndex;
			}
		}else if(index>=highestIndex){
			currentValue = highestIndex;
			if(index>highestIndex && isCyclic){
				currentValue = 0;
			}
		}else{
			currentValue = index;
		}
		
		if(oldSelIndex != currentValue){//changed
			updateSelectedStyle();
			SliderValueChangedEvent changedEvent = new SliderValueChangedEvent();
			changedEvent.setValue(currentValue);
			fireChangedEvent(changedEvent);
		}
//		System.out.println("selectedMark: "+currentValue);
	}

	protected void updateSelectedStyle() {
		for(int i=0; i<markCount; ++i){
			Panel mark = markArr.get(i);
			if(i == currentValue){
				mark.addStyleName(CssNames.SB_MARK_SELECTED);
				mark.removeStyleName(CssNames.SB_MARK_UNSELECTED);
			}else{
				mark.addStyleName(CssNames.SB_MARK_UNSELECTED);
				mark.removeStyleName(CssNames.SB_MARK_SELECTED);
			}
		}
		
		
	}

}
