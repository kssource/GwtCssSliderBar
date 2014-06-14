package de.ks.sliderbar.client;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.ks.sliderbar.client.widget.AbstractSliderBar;
import de.ks.sliderbar.client.widget.SliderBarWidget;
import de.ks.sliderbar.client.widget.SliderValueChangedEvent;
import de.ks.sliderbar.client.widget.SliderValueChangedListener;

public class Test2 extends VerticalPanel {

	private SliderBarWidget horizBarWidget;
	private DeckPanel deckPanel;
	private Panel deckPanelWrapper;
	private SliderBarWidget vertBarWidget;

	public Test2() {
		init();
	}

	private void init() {
		FlexTable flexTable = new FlexTable();
		
		deckPanelWrapper = createTestDeckPanel();
		flexTable.setWidget(1, 1, deckPanelWrapper);
		
		int count = deckPanel.getWidgetCount();
		horizBarWidget = new SliderBarWidget(count, 0, true, AbstractSliderBar.ORIENTATION_HORIZONTAL, "sbTheme2");
		horizBarWidget.addChangeListener(new SliderValueChangedListener() {
			@Override
			public void onChange(SliderValueChangedEvent e) {
				sliderValueChanged(e.getValue());
			}
		});
		flexTable.setWidget(0, 1, horizBarWidget);
		
		vertBarWidget = new SliderBarWidget(count, 0, true, AbstractSliderBar.ORIENTATION_VERICAL, "sbTheme2");
		vertBarWidget.addChangeListener(new SliderValueChangedListener() {
			@Override
			public void onChange(SliderValueChangedEvent e) {
				sliderValueChanged(e.getValue());
			}
		});
		flexTable.setWidget(1, 0, vertBarWidget);
		
		
		

		
		this.add(flexTable);
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
		String height = "300px";
		deckPanel.setHeight(height);
		
		preloadImages(deckPanel);
		
		deckPanel.setAnimationEnabled(true);
		
		focusPanel.add(deckPanel);
		return focusPanel;
	}
	
	//delegate deckPanel keyEvent
	private void handleKeyDown(KeyDownEvent event) {
		horizBarWidget.actionKeyDown(event);
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
