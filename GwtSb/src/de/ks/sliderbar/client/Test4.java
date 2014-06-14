package de.ks.sliderbar.client;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.ks.sliderbar.client.widget.AbstractSliderBar;
import de.ks.sliderbar.client.widget.SliderBarWidget;
import de.ks.sliderbar.client.widget.SliderValueChangedEvent;
import de.ks.sliderbar.client.widget.SliderValueChangedListener;

public class Test4 extends VerticalPanel{

	
	private SliderBarWidget barWidget;
	private DeckPanel deckPanel;
	private Panel deckPanelWrapper;

	public Test4() {
		init();
	}

	private void init() {
		
		deckPanelWrapper = createTestDeckPanel();
		int count = deckPanel.getWidgetCount();
		
		barWidget = new SliderBarWidget(count, 0, true, AbstractSliderBar.ORIENTATION_HORIZONTAL, "sbTheme4");

		this.add(barWidget);

		barWidget.addChangeListener(new SliderValueChangedListener() {
			@Override
			public void onChange(SliderValueChangedEvent e) {
				sliderValueChanged(e.getValue());
			}
		});
		
		this.add(deckPanelWrapper);
		
		deckPanel.showWidget(0);
	}

	protected void sliderValueChanged(int value) {
		deckPanel.showWidget(value);
	}

	private Panel createTestDeckPanel() {
		FocusPanel focusPanel = new FocusPanel();
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				handleKeyDown(event);
			}
		});
		
		focusPanel.addStyleName("clearOutline");
		
		deckPanel = new DeckPanel();
		deckPanel.addStyleName("deckPanel");
		
		preloadImages(deckPanel);
		
		deckPanel.setAnimationEnabled(true);
		
		focusPanel.add(deckPanel);
		return focusPanel;
	}
	
	//delegate deckPanel keyEvent
	private void handleKeyDown(KeyDownEvent event) {
		barWidget.actionKeyDown(event);
	}

	private void preloadImages(DeckPanel deckPanel) {
		int imagesCount = 4;
		for(int i=0; i<imagesCount ; ++i){
			StringBuilder builder = new StringBuilder("images/test2/image").append(i+1).append(".png");
			Image image = new Image(builder.toString());
			Image.prefetch(builder.toString());
			
			SimplePanel panel = new SimplePanel(image);
			panel.addStyleName("centerContent");
			
			deckPanel.add(panel);
		}
		
	}


}
