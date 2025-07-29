package view;

//fields
import javax.swing.JFrame; //set up the frame 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

//event listeners
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//layout managers
import java.awt.FlowLayout; //layouting
import java.awt.Container; //container datatypes 

//debugging
import java.util.Scanner;

public class GUIView implements View 
{
	private JFrame frame;
	private JLabel label;
	private JTextField jinput;
	private JButton button1;
	private JButton button2;
	private int width;
	private int height;

	public GUIView(int w, int h)
	{
		frame = new JFrame();
		label = new JLabel("Input shit here brawh: ");
		jinput = new JTextField(10); //field width of 10
		button1 = new JButton("CLICK ME PLEASEEE");
		button2 = new JButton("Button2");
		width = w;
		height = h;

		setupGUI();
		setupButtonListeners();
	}

	//sets up the app SCREEN
	public void setupGUI()
	{
		//access content
		Container cp = frame.getContentPane();
		FlowLayout flow = new FlowLayout(); //layout manager
		cp.setLayout(flow);

		frame.setSize(width, height);
		frame.setTitle("Pokedex");

		//add the stuff IN the container
		cp.add(jinput);
		cp.add(label);
		cp.add(button1);
		cp.add(button2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set everything first then set visible
		frame.setVisible(true);
	}

	public void setupButtonListeners()
	{
		//creating an object without defining another class
		//in a different file
		//
		//automatically runs this method
		ActionListener buttonListener = new ActionListener()
		{
			//exactly as so
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				//get INFORMATION/ representation of the event
				//can get timestamp, souce of event, etc
				
				//gets source of action event
				//ae.getSource();
				
				Object o = ae.getSource();

				if(o == button1)
				{
					System.out.println("ONG");
				} else if(o == button2)
				{
					System.out.println("On my soul");
				}
			}
		};

		//notifies button listener if button is clicked
		button1.addActionListener(buttonListener);
		button2.addActionListener(buttonListener);
	}

	Scanner input = new Scanner(System.in);

   @Override
   public void show(String msg) 
	{
		System.out.println(msg);
   }

   @Override
   public String prompt(String msg) 
	{
		System.out.println(msg);
		return input.nextLine();
	}

	@Override
	public int promptInt(String msg)
	{
		int choice = -1;
		boolean valid = false;
		
		System.out.print(msg);
		
		do
		{
			try
			{
				choice = input.nextInt();
				input.nextLine(); //buffer
				valid = true;
			} catch(Exception e)
			{
				System.out.println("Invalid input!");
				input.nextLine(); //buffer
			}
		} while(!valid);
			
		return choice;
	}
	
	@Override
	public int promptIntRange(String msg, int lowerRange, int higherRange)
	{
		int choice = -1;
		
		System.out.print(msg);
		do
		{
			try
			{
				choice = input.nextInt();
				input.nextLine();
				if(choice > higherRange || choice < lowerRange)
				{
					System.out.println("Out of Range!");
				}
			} catch(Exception E)
			{
				System.out.println("Invalid Input!");
				input.nextLine();
			} 
		} while (choice > higherRange || choice < lowerRange);
		
		return choice;
	}
}
