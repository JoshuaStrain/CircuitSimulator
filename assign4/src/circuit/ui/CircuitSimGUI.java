
package circuit.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import circuit.ANDGate;
import circuit.Circuit;
import circuit.NOTGate;
import circuit.ORGate;
import circuit.util.ComponentsExplorer;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class CircuitSimGUI extends JFrame {

	private JPanel mainPanel;
	private Circuit circuit;
	private ComponentsExplorer util;
	private List<String> shortComponentNames;
	private JPanel circuitPanel;
	private JPanel busLabels;
	private Cell grid[][]; 
	int nrows;
	int ncolumns;
	private volatile boolean isShutingDown; 

	@Override
	protected void frameInit(){

		super.frameInit();

		nrows = 1;
		ncolumns = 10;

		circuit = new Circuit(ncolumns);

		util = new ComponentsExplorer();
		shortComponentNames = util.getShortComponentNames();


		super.setSize(1000, 700);
		super.setBackground(Color.GRAY);
		super.setTitle("Circuit Simulator");
		super.addWindowListener(new WindowCloseHandler());
		grid = new Cell[nrows][ncolumns];

		setupContentPane();

		super.setVisible(true);
		super.setLocationRelativeTo(null);
	}

	private void setupContentPane() {
		JMenuBar menu = new JMenuBar();

		menu.add(getSaveLoadMenu());
		menu.add(getStartEndMenu());
		menu.add(getGateMenu(shortComponentNames));
		menu.add(getSimulateMenu());

		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout(5, 0));
		setContentPane(mainPanel);
		mainPanel.add(menu, BorderLayout.NORTH);

		circuitPanel = newCircuitPanel(nrows, ncolumns);
		mainPanel.add(circuitPanel, BorderLayout.CENTER);

		busLabels = getBusLabels(ncolumns);
		mainPanel.add(busLabels, BorderLayout.SOUTH);

	}

	private JPanel getBusLabels(int busColumns){
		JPanel panel = new JPanel();
		int rows = 1;
		panel.setLayout(new GridLayout(1, busColumns));
		JLabel label;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < busColumns; j++){
				label = new JLabel(Integer.toString(j), JLabel.RIGHT);
				panel.add(label);
			}
		return panel;
	}

	private JPanel newCircuitPanel(int rows, int columns){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, columns));
		grid = new Cell[rows][columns];
		Font smallFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++){
				Cell cell = new Cell(i, j);
				cell.setFont(smallFont);
				panel.add(cell);
				grid[i][j] = cell;
			}

		return panel;
	}



	private class WindowCloseHandler extends WindowAdapter
	{
		@Override
		public void windowClosing( WindowEvent event)
		{
			System.exit(0); 
		}
	}

	private JComponent getStartEndMenu(){
		JMenu menuItems = new JMenu("Set Input Value");

		JMenuItem inCircuit = new JMenuItem("Toggle Bus Value");
		inCircuit.addActionListener(new ToggleMenuActionListener());
		menuItems.add(inCircuit);

		return menuItems;
	}

	private JComponent getSimulateMenu()
	{
		JMenu simulateMenu = new JMenu("Simulate");

		JMenuItem simulateItem = new JMenuItem("Start Simulation");
		JMenuItem simulateStopItem = new JMenuItem("Stop Simulation");
		simulateItem.addActionListener(new StartSimActionListener());
		simulateStopItem.addActionListener(new StopSimActionListener());
		simulateMenu.add(simulateItem);
		simulateMenu.add(simulateStopItem);

		return simulateMenu;
	}

	private JComponent getGateMenu(List<String> gateNames)
	{
		JMenu gateMenu = new JMenu("Add Gate");

		for(int i = 0; i < gateNames.size(); i++)
		{
			JMenuItem gate = new JMenuItem(gateNames.get(i));
			gate.addActionListener(new MenuActionListener());
			gateMenu.add(gate);
		}
		return gateMenu;
	}

	private JComponent getSaveLoadMenu(){

		JMenu saveLoad = new JMenu("Circuit");

		JMenuItem newCircuit = new JMenuItem("New");
		newCircuit.addActionListener(new NewCircuitActionListener());

		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");

		saveLoad.add(newCircuit);
		saveLoad.add(save);
		saveLoad.add(load);

		return saveLoad;
	}

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String label = e.getActionCommand();
			int bus1, bus2 = 0; 
			int busOut;

			String busID = (String)JOptionPane.showInputDialog(mainPanel, "Enter bus number for input 1", null);
			bus1 = Integer.parseInt(busID);

			if (!label.equals("NOTGate")){
				String busID2 = JOptionPane.showInputDialog(mainPanel, "Enter bus number for input 2", null);
				bus2 = Integer.parseInt(busID2);
			}

			String outputBusID = JOptionPane.showInputDialog(mainPanel, "Enter bus number for output", null);
			busOut = Integer.parseInt(outputBusID);

			boolean placedIt = false;
			int in1 = 0;
			while (!placedIt){
				if (grid[in1][busOut].isOccupied()){
					JOptionPane.showMessageDialog(circuitPanel, 
							"There is already an output in " + busOut + ". Try again.");
					return;
				}
				else
					placedIt = true;
			}

			String cellLabel;
			if (label.equals("ANDGate")){
				cellLabel = "AND";
				int inputBus[] = {bus1, bus2};
				circuit.connectComponent(new ANDGate(), inputBus, busOut);
			}
			else if (label.equals("ORGate")){
				cellLabel = "OR";
				int inputBus[] = {bus1, bus2};
				circuit.connectComponent(new ORGate(), inputBus, busOut);
			}
			else{
				cellLabel = "NOT";
				int inputBus[] = {bus1};
				circuit.connectComponent(new NOTGate(), inputBus, busOut);
			}

			cellLabel += "." + bus1;
			if (!label.equals("NOTGate"))
				cellLabel += Integer.toString(bus2);
			cellLabel += Integer.toString(busOut);

			grid[in1][busOut].setBackground(Color.WHITE);
			grid[in1][busOut].setText(cellLabel);	
			grid[in1][busOut].setOccupied(true);

		}
	}

	class ToggleMenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String busID = JOptionPane.showInputDialog(mainPanel, "What bus value do you want to change?", null);
			int toggleBus = Integer.parseInt(busID);

			if(!circuit.busValueSettable(toggleBus)){
				JOptionPane.showInputDialog(mainPanel, "That bus value cannot be changed. It contains an output lead.", null);
				return;
			}
			boolean currentValue = circuit.getBusValue(toggleBus);
			int currInt = (currentValue == true) ? 1 : 0;
			int confirm = JOptionPane.showConfirmDialog(circuitPanel, "The current value of that bus is " + currInt 
					+ ". Do you want to change it?");

			if(confirm == 0){
				circuit.toggleBusValue(toggleBus);
				JOptionPane.showMessageDialog(circuitPanel, "The value is changed.");
			}
			else
				return;
		}
	}


	class NewCircuitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String busID = JOptionPane.showInputDialog(mainPanel, "How many buses would you like on your circuit board?", null);
			int ncolumns = Integer.parseInt(busID);

			mainPanel.remove(circuitPanel);
			mainPanel.remove(busLabels);
			circuit = new Circuit(ncolumns);
			circuitPanel = newCircuitPanel(nrows,ncolumns);
			busLabels = getBusLabels(ncolumns);
			mainPanel.add(circuitPanel, BorderLayout.CENTER);
			mainPanel.add(busLabels, BorderLayout.SOUTH);
			Container parent = circuitPanel.getTopLevelAncestor();
			parent.validate();

		}
	}

	
	private class StartSimActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Thread work = new Thread() {
                public void run() {
                    while (!isShutingDown) {
                        circuit.evaluate();
                        List<Integer> unconnectedOutputs = circuit.getUnconnectedOutputs();
                        showUnconnectedOutputs(unconnectedOutputs);
                        repaint();
                    }
                }
            };
            work.start();
        }
    }

	private void showUnconnectedOutputs(List<Integer> unconnectedOutputs) {
		for (int i = 0; i < unconnectedOutputs.size(); i++) {
			if (circuit.getBusValue(unconnectedOutputs.get(i))) {
				grid[0][unconnectedOutputs.get(i)].setBackground(Color.GREEN);
			} else {
				grid[0][unconnectedOutputs.get(i)].setBackground(Color.YELLOW);

			}
		}
	}
        

	private class StopSimActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			shutDown();
			isShutingDown = false;
		}
	}

	public void shutDown(){
		isShutingDown = true;
	}
}




