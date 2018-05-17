
package circuit.ui;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Cell extends JButton
{
        public final int row;
        public final int column;
        private boolean occupied = false;


				public Cell(int theRow, int theColumn)
        {
            row = theRow;
            column = theColumn;
        }
				
        
        public boolean isOccupied() {
					return occupied;
				}

				public void setOccupied(boolean occupied) {
					this.occupied = occupied;
				}
}
