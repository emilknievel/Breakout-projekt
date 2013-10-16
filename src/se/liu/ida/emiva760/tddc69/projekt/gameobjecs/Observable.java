package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import javafx.scene.input.KeyEvent;

public interface Observable
{
    public void NotifyObservers(KeyEvent keyEvent);
}
