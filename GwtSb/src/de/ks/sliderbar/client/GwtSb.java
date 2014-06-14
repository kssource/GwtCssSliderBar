package de.ks.sliderbar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtSb implements EntryPoint {
	@Override
	public void onModuleLoad() {
		Test1 test1Container = new Test1();
		RootPanel.get("test1").add(test1Container);

		Test2 test2Container = new Test2();
		RootPanel.get("test2").add(test2Container);

		Test1 test3Container = new Test1();
		RootPanel.get("test3").add(test3Container);
		test3Container.bindAdditionalPrevNextButtons();

		Test4 test4Container = new Test4();
		RootPanel.get("test4").add(test4Container);
		
	}

}
