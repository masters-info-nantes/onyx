package com.onyx.launcher;

import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import com.onyx.core.OActivity;
import com.onyx.core.OAppProperty;
import com.onyx.core.OCore;

/**
 * Created by Maxime on 08/02/15.
 */
public class OLauncher extends OActivity {
	private static final Image ICON_48 = new Image("file:icon-68x68.png");
	private OCore core = null;

    private class EventHandlerRunApp implements EventHandler<Event> {

        OAppProperty oAppProperty;

        public EventHandlerRunApp(OAppProperty oAppProperty) {
            this.oAppProperty = oAppProperty;
        }

        @Override
        public void handle(Event event) {
            core.setApplication(oAppProperty.id);
        }
    }
	
	private Pane createContent(final List<Object> apps){
        
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(3); //preferred columns
        tilePane.setAlignment(Pos.CENTER);
        
        Object[] appsButton = apps.toArray();
        
        
        for (int j = 0; j < apps.size(); j++) {
            OAppProperty app  = (OAppProperty)apps.get(j);
            if(!app.id.equals(OCore.LAUNCHER_APP)) {
                //Image = app[i].getOnyxIcon()
                //buttons[j] = new Button(app[j].getName(), new ImageView(app[j].getIcon());
                appsButton[j] = new Button();
                ((Labeled) appsButton[j]).setGraphic(new ImageView(ICON_48));
                ((Node) appsButton[j]).setTranslateX(35);
                //buttons[j].setPadding(new Insets(15));
                ((Node) appsButton[j]).setOnMouseClicked(new EventHandlerRunApp((OAppProperty)apps.get(j)));
                tilePane.getChildren().add((Node) appsButton[j]);
            }
        }
		return tilePane;

	}
	
	
    @Override
    public void onCreate() {
    	super.onCreate();
        System.out.println("run launcher");
        core = this.getCore();
        List<Object> apps = getPlatform().getServices("application");
        System.out.println("Creation de l'interface");
        getPane().getChildren().add(createContent(apps));
        
    }
}