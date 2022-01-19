import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

public class Main implements ActionListener
{
	ImageIcon quizIcon;
	
	JFrame frame=new JFrame();
	JPanel title=new JPanel();
	JPanel buttons=new JPanel();
	
	JButton createQuiz=new JButton();
	JButton playQuiz=new JButton();
	JButton newProject=new JButton();
	JButton delete=new JButton();
	
	JLabel textfield=new JLabel();
	
	JComboBox<String> projects;
	
	File bank;
	File[] projectList;	
	String[] list;
	
	Formatter x;
	Scanner reader;
	
	public Main()
	{
		bank=new File("Bank");
		projectList=bank.listFiles();
		
		list=new String[projectList.length];
		
		int i=0;
		for(File file:projectList)
		{
			list[i]=file.getName().substring(0, file.getName().length()-4);
			i++;
		}
		
		quizIcon=new ImageIcon("Quiz.png");
		frame.setIconImage(quizIcon.getImage());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Quiz: Choice");	
		frame.getContentPane().setBackground(new Color(100,100,100));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setResizable(false); //Can't be resized
		
		projects=new JComboBox<String>(list);
		projects.addActionListener(this);
		projects.setFont(new Font("MV Boli",Font.BOLD,20)); 
		projects.setBackground(new Color(165, 183, 201));
		projects.setForeground(new Color(255,255,255));
		projects.setEditable(false);
		
		textfield.setBackground(new Color(50,50,50));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("MV Boli",Font.ITALIC,25));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Choose:");
		textfield.setOpaque(true);

		title.setLayout(new BorderLayout());
		
		buttons.setLayout(new GridLayout(2,2));
		buttons.setBackground(new Color(100,100,100));
		
		playQuiz=new JButton("Play Quiz");	
		playQuiz.setFocusable(false);	
		playQuiz.addActionListener(this);	
		playQuiz.setBackground(new Color(75,75,75));	
		playQuiz.setFont(new Font("MV Boli",Font.BOLD,25));
		playQuiz.setForeground(new Color(255,255,255));
		
		createQuiz=new JButton("Create Quiz");
		createQuiz.addActionListener(this);
		createQuiz.setFocusable(false);
		createQuiz.setBackground(new Color(75,75,75));
		createQuiz.setFont(new Font("MV Boli",Font.BOLD,25));
		createQuiz.setForeground(new Color(255,255,255));
		
		newProject=new JButton("New Project");
		newProject.addActionListener(this);
		newProject.setFocusable(false);
		newProject.setBackground(new Color(75,75,75));
		newProject.setFont(new Font("MV Boli",Font.BOLD,25));
		newProject.setForeground(new Color(255,255,255));
		
		delete=new JButton("Delete");
		delete.addActionListener(this);
		delete.setFocusable(false);
		delete.setBackground(new Color(75,75,75));
		delete.setFont(new Font("MV Boli",Font.BOLD,25));
		delete.setForeground(new Color(255,255,255));
		
		buttons.add(playQuiz);
		buttons.add(createQuiz);
		buttons.add(newProject);
		buttons.add(delete);
		
		title.add(textfield);
		frame.add(title, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);
		frame.add(projects);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) 
	{
		File bank=new File("Bank");
		if(bank.exists() && bank.isDirectory())
			new Main();
		else 
		{
			bank.mkdir();			
			new Main();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==playQuiz)
		{
			if(projects.getSelectedItem()==null)
				JOptionPane.showMessageDialog(null, "Please create a project first", "Warning", JOptionPane.WARNING_MESSAGE);
			else 
			{
				File questionNumber=(new File("Bank\\"+projects.getSelectedItem()+".txt"));
				try {
					reader=new Scanner(questionNumber);
					
					if(reader.hasNextLine() && reader.nextLine()!=null)
					{
						frame.dispose();
						new GameFrame((String) projects.getSelectedItem());
					}
					else 
						JOptionPane.showMessageDialog(null, "Please add atleast 1 Question to Play", "Warning", JOptionPane.WARNING_MESSAGE);
					
					reader.close();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource()==createQuiz)
		{
			if(projects.getSelectedItem()==null)
				JOptionPane.showMessageDialog(null, "Please create a project first", "Warning", JOptionPane.WARNING_MESSAGE);
			else
			{
				frame.dispose();
				new CreateQuestion((String) projects.getSelectedItem());
			}
		}
		if(e.getSource()==newProject)
		{
			String name= JOptionPane.showInputDialog(null,"Enter Project Name:","New Project", JOptionPane.PLAIN_MESSAGE);
			
			if(name==null || name.length()==0)
			{
				JOptionPane.showMessageDialog(null, "Please enter a name for New Project", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			else 
			{
				File newProject=new File("Bank\\"+name+".txt");
				boolean alreadyExists=false;
				
				for(int i=0; i<list.length; i++)
				{
					if(list[i].equalsIgnoreCase(name))
					{
						JOptionPane.showMessageDialog(null, "This Project Already Exists", "Warning", JOptionPane.WARNING_MESSAGE);
						alreadyExists=true;
					}
				}
				
				if(alreadyExists==false)
				{
					try {
						x=new Formatter(newProject);
						x.close();
						
						newProject.setWritable(false);
						
						JOptionPane.showMessageDialog(null, "Project Created", "Info", JOptionPane.INFORMATION_MESSAGE);
						
						//To refresh JComboBox
						frame.dispose(); 
						new Main();
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}
				}		
			}
		}
		
		if(e.getSource()==delete)
		{
			if(projects.getSelectedItem()==null)
				JOptionPane.showMessageDialog(null, "No Project Present", "Warning", JOptionPane.WARNING_MESSAGE);
			else
			{
				File deleteFile=new File("Bank\\"+projects.getSelectedItem()+".txt");
				
				int a=JOptionPane.showConfirmDialog(null, "Do you want to delete the entire project: "+deleteFile.getName().substring(0, deleteFile.getName().length()-4));		
				if(a==JOptionPane.YES_OPTION)
				{					
					if(deleteFile.delete())
					{
						JOptionPane.showMessageDialog(null, "Project Deleted", "Info", JOptionPane.INFORMATION_MESSAGE);
						
						frame.dispose();
						new  Main();
					}
				}
			}
		}		
	}
}
