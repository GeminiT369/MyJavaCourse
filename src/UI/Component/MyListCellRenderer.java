package UI.Component;

import javax.swing.*;
import java.awt.*;

public class MyListCellRenderer extends DefaultListCellRenderer {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}
