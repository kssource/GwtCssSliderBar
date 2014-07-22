package de.ks.sliderbar.client;

import java.util.List;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.ks.sliderbar.client.sbwidget.AbstractSliderBar;
import de.ks.sliderbar.client.sbwidget.SliderBarWidget;
import de.ks.sliderbar.client.sbwidget.SliderValueChangedEvent;
import de.ks.sliderbar.client.sbwidget.SliderValueChangedListener;

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
		int imagesCount = 5;
		List<String> urls = SbShared.getRandomImageUrl(256, 256);
		
		for(int i=0; i<imagesCount ; ++i){
			// build image url
//			StringBuilder builder = new StringBuilder("images/sb/shared/image").append(i+1).append(".png");
//			Image image = new Image(builder.toString());
//			Image.prefetch(builder.toString());

			String url = urls.get(i);
			Image image = new Image(url);
			Image.prefetch(url);
			
			SimplePanel panel = new SimplePanel(image);
			panel.addStyleName("centerContent");
			
			deckPanel.add(panel);
		}
	}


	
}
