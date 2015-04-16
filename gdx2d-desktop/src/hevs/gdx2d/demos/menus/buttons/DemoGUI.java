package hevs.gdx2d.demos.menus.buttons;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Very simple UI demonstration
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoGUI extends PortableApplication {
	Skin skin;
	Stage stage;

	public DemoGUI(boolean onAndroid) {
		super(onAndroid);
	}

	TextButton newGameButton, quitGameButton;
	TextField textArea;
	
	@Override
	public void onInit() {
		int buttonWidth = 180;
		int buttonHeight = 30;
		
		setTitle("GUI demonstration");
        
		stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        //createBasicSkin();
        skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        
        newGameButton = new TextButton("Click me", skin); // Use the initialized skin        
        newGameButton.setWidth(buttonWidth);
        newGameButton.setHeight(buttonHeight);
        
        quitGameButton = new TextButton("Useless button", skin); // Use the initialized skin        
        quitGameButton.setWidth(buttonWidth);
        quitGameButton.setHeight(buttonHeight);

        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - buttonWidth/2 , (int)(Gdx.graphics.getHeight()*.6));
        quitGameButton.setPosition(Gdx.graphics.getWidth()/2 - buttonWidth/2, (int)(Gdx.graphics.getHeight()*.7));
        
        textArea = new TextField("Enter some text...", skin);
        textArea.setWidth(buttonWidth);        
        textArea.setPosition(Gdx.graphics.getWidth()/2 - buttonWidth/2, (int)(Gdx.graphics.getHeight()*.4));
        
        textArea.setTextFieldListener(new TextFieldListener() {
            public void keyTyped (TextField textField, char key) {                            	            	
            	textArea.setSelection(0, 0);
            	if(key == 13)
            		Logger.log("You have typed " + textArea.getText());
            }
        });
        
        /**
         * Adds the buttons to the stage
         */
        stage.addActor(newGameButton);
        stage.addActor(quitGameButton);
        stage.addActor(textArea);
        
        /**
         * Register listener
         */
        newGameButton.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		super.clicked(event, x, y);        		
        		
        		if(newGameButton.isChecked())
        			Logger.log("Button is checked");
        		else
        			Logger.log("Button is not checked");
        	}
		});
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.BLACK);		
        stage.act();
        stage.draw();
        
        g.drawStringCentered(getWindowHeight() / 4, "Button status " + newGameButton.isChecked());
        g.drawSchoolLogo();
        g.drawFPS();
	}

	@Override
	public void onDispose() {
		super.onDispose();
		stage.dispose();
		skin.dispose();
	}

	public static void main(String[] args) {
		new DemoGUI(false);
	}
}
