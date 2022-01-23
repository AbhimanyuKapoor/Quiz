import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class CreateQuestion extends JFrame implements ActionListener, FocusListener
{	
	ImageIcon quizIcon;
	
	JTextField btnA;
	JTextField btnB;
	JTextField btnC;
	JTextField btnD;
	
	ArrayList<JTextField> buttons;
	
	String options[]= {"a","b","c","d"};
	
	@SuppressWarnings("rawtypes")
	JComboBox optionsBox;

	int current_question=0;
	
	JButton nextButton;
	JButton prevButton;
	JButton playQuiz;
	JButton delButton;
	JButton editButton;
	JButton mainMenu;
	
	boolean editing;
	
	JLabel qnum;
	JTextField questionField;
	
	ArrayList<File> qFiles=new ArrayList<File>();
	ArrayList<File> qShowFiles=new ArrayList<File>();
	
	ArrayList<String> questionText=new ArrayList<String>();
	
	ArrayList<String> optionsA=new ArrayList<String>();
	ArrayList<String> optionsB=new ArrayList<String>();
	ArrayList<String> optionsC=new ArrayList<String>();
	ArrayList<String> optionsD=new ArrayList<String>();
	
	ArrayList<Integer> ans=new ArrayList<Integer>();
	
	Scanner reader;
	Formatter x;
	
	String current_project;
	
	int count=0;
	
	File dirFile;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CreateQuestion(String project) 
	{	
		current_project=project;
		
		quizIcon=new ImageIcon("Quiz.png");		
		this.setIconImage(quizIcon.getImage());	

		dirFile=new File("Bank\\"+current_project+".txt");
		
		getQuestionCount();
		//For csv reference check the java project ReadWriteCSV which I made
		try 
		{
			Scanner reader=new Scanner(dirFile);	
			
			for(int i=0; i<count; i++)
			{
				String ar[]=reader.nextLine().split(";");
				
				ans.add(Integer.parseInt(ar[0]));
				optionsA.add(ar[1]);
				optionsB.add(ar[2]);
				optionsC.add(ar[3]);
				optionsD.add(ar[4]);
				questionText.add(ar[5]);
			}
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);	
		this.setSize(650,540);
		this.setTitle("Quiz: Create Question");
		
		optionsBox=new JComboBox(options);
		optionsBox.addActionListener(this);
		optionsBox.setFont(new Font("MV Boli",Font.BOLD,20)); 
		optionsBox.setBackground(new Color(165, 183, 201));
		optionsBox.setForeground(new Color(255,255,255));
		optionsBox.setSelectedItem("a");
		optionsBox.setBounds(560,20,50,25);
		optionsBox.setEditable(false);
		
		playQuiz=new JButton("Play Quiz");
		playQuiz.setFont(new Font("MV Boli", Font.BOLD, 20));
		playQuiz.setBounds(325,450,162,50);
		playQuiz.setForeground(Color.GREEN);
		playQuiz.setBackground(Color.WHITE);
		playQuiz.addActionListener(this);
		playQuiz.setFocusable(false);
		
		mainMenu=new JButton("Main Menu");
		mainMenu.setFont(new Font("MV Boli", Font.BOLD, 20));
		mainMenu.setBounds(487,450,163,50);
		mainMenu.setForeground(Color.GREEN);
		mainMenu.setBackground(Color.WHITE);
		mainMenu.addActionListener(this);
		mainMenu.setFocusable(false);
		
		nextButton=new JButton("Next");
		nextButton.setFont(new Font("MV Boli", Font.BOLD, 20));
		nextButton.setBounds(162,450,163,50);
		nextButton.setForeground(Color.BLUE);
		nextButton.setBackground(Color.WHITE);
		nextButton.addActionListener(this);
		nextButton.setFocusable(false);
		
		prevButton=new JButton("Previous");
		prevButton.setFont(new Font("MV Boli", Font.BOLD, 20));
		prevButton.setBounds(0,450,162,50);
		prevButton.setForeground(Color.BLUE);
		prevButton.setBackground(Color.WHITE);
		prevButton.addActionListener(this);
		prevButton.setFocusable(false);
		
		delButton=new JButton("Del");
		delButton.setFont(new Font("MV Boli", Font.BOLD, 20));
		delButton.setBounds(487,20,65,25);
		delButton.setForeground(Color.WHITE);
		delButton.setBackground(Color.lightGray);
		delButton.addActionListener(this);
		delButton.setFocusable(false);
		
		editButton=new JButton("Edit");
		editButton.setFont(new Font("MV Boli", Font.BOLD, 20));
		editButton.setBounds(400,20,80,25);
		editButton.setForeground(Color.RED);
		editButton.setBackground(Color.WHITE);
		editButton.addActionListener(this);
		editButton.setFocusable(false);
		
		qnum=new JLabel();
		qnum.setForeground(Color.RED);
		qnum.setBounds(20,20,560,20);
		qnum.setFont(new Font("MV Boli", Font.BOLD, 25));
		
		btnA=new JTextField();
		btnB=new JTextField();
		btnC=new JTextField();
		btnD=new JTextField();
		
		buttons=new ArrayList<JTextField>();
		
		buttons.add(btnA);
		buttons.add(btnB);
		buttons.add(btnC);
		buttons.add(btnD);
		
		for(int i=0; i<buttons.size(); i++)
		{
			buttons.get(i).setBackground(Color.lightGray);
			buttons.get(i).setEditable(true);
			buttons.get(i).setVisible(true);
			buttons.get(i).setFont(new Font("MV Boli", Font.BOLD, 20));
			buttons.get(i).setHorizontalAlignment(JTextField.CENTER);
		}
		
		btnA.setBounds(0,150,325,150);
		btnB.setBounds(325,150,325,150);
		btnC.setBounds(0,300,325,150);
		btnD.setBounds(325,300,325,150);
		
		questionField=new JTextField();
		questionField.setForeground(Color.WHITE);
		questionField.setEditable(true);
		questionField.setVisible(true);
		questionField.setFont(new Font("MV Boli", Font.BOLD, 20));
		questionField.setBackground(Color.BLACK);
		questionField.setBounds(0,100,650,50);	
		questionField.setHorizontalAlignment(JTextField.CENTER); 
		//Editable text comes in center instead of left side
		
		for(int i=0; i<buttons.size(); i++)
			this.add(buttons.get(i));
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(questionField);	
		this.add(nextButton);
		this.add(prevButton);
		this.add(qnum);
		this.add(optionsBox);
		this.add(playQuiz);
		this.add(delButton);
		this.add(editButton);
		this.add(mainMenu);
		
		this.revalidate(); //Sometimes some components don't show properly, this is very important
		this.repaint(); //Both repaint and revalidate is needed to replace all the elements
		
		resetFields();
		
		questionField.addFocusListener(this);
		btnA.addFocusListener(this);
		btnB.addFocusListener(this);
		btnC.addFocusListener(this);
		btnD.addFocusListener(this);
	}
	
	public void nextQuestion()
	{		
		if(current_question<count)
		{
			questionText.set(current_question, questionField.getText().replace(';', ' '));
			optionsA.set(current_question, btnA.getText().replace(';', ' '));
			optionsB.set(current_question, btnB.getText().replace(';', ' '));
			optionsC.set(current_question, btnC.getText().replace(';', ' '));
			optionsD.set(current_question, btnD.getText().replace(';', ' '));
			ans.set(current_question, optionsBox.getSelectedIndex());
			
			addFileValues();
		}
		else
		{
			//Since I have separated by semicolons, I need to replace it with a space to prevent confusion
			questionText.add(questionField.getText().replace(';', ' '));
			optionsA.add(btnA.getText().replace(';', ' '));
			optionsB.add(btnB.getText().replace(';', ' '));
			optionsC.add(btnC.getText().replace(';', ' '));
			optionsD.add(btnD.getText().replace(';', ' '));
			ans.add(optionsBox.getSelectedIndex());
			
			count++;
			addFileValues();
			getQuestionCount();
		}
	}
	
	public void resetFields()
	{
		if(current_question<count)
		{			
			questionField.setText(questionText.get(current_question));
			btnA.setText(optionsA.get(current_question));
			btnB.setText(optionsB.get(current_question));
			btnC.setText(optionsC.get(current_question));
			btnD.setText(optionsD.get(current_question));
			
			editButton.setBackground(Color.WHITE);
			editButton.setEnabled(true);
			editing=false;
			
			optionsBox.setSelectedIndex(ans.get(current_question));
		}		
		else 
		{
			questionField.setText("Question");
			btnA.setText("A");
			btnB.setText("B");
			btnC.setText("C");
			btnD.setText("D");
			
			editButton.setBackground(Color.ORANGE);
			editButton.setEnabled(false);
			editing=true;
			
			optionsBox.setSelectedIndex(0);
		}
		
		qnum.setText("Question: "+(1+current_question));
		
		qFiles.clear();
		qShowFiles.clear();
	}
	
	public void getQuestionCount()
	{
		count=0;
		
		try {
			Scanner reader=new Scanner(dirFile);
			
			//To make sure if nextLine is there and it is not an empty line
			while (reader.hasNextLine() && reader.nextLine()!=null)
				count++;
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void addFileValues()
	{
		if(dirFile.delete())
		{
			try {
				x=new Formatter(dirFile);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			x.close();
			
			FileWriter fw;
			BufferedWriter bw;
			PrintWriter pw;
			
			try {
				fw = new FileWriter(dirFile, true);
				bw=new BufferedWriter(fw);
				pw=new PrintWriter(bw);
				
				for(int i=0; i<count; i++)
					pw.println(ans.get(i)+";"+optionsA.get(i)+";"+optionsB.get(i)+";"+optionsC.get(i)+";"+optionsD.get(i)+";"+questionText.get(i));
				
				pw.flush();
				pw.close();
				//Both flush & close are needed to clear data
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			dirFile.setWritable(false);	
			//setWritable(false) after writing the values
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if(e.getSource()==nextButton)
		{
			if(questionField.getText().isEmpty() || btnA.getText().isEmpty() || btnB.getText().isEmpty() || btnC.getText().isEmpty() || btnD.getText().isEmpty())
				JOptionPane.showMessageDialog(null, "Field is Empty!", "Warning", JOptionPane.WARNING_MESSAGE);
			else 
			{
				if(editing)
				{
					nextQuestion();
					current_question++;
					resetFields();
				}
				else 
				{
					current_question++;
					resetFields();
				}
			}
		}
		if(e.getSource()==prevButton)
		{
			if(qnum.getText().equals("Question: 1"))
				JOptionPane.showMessageDialog(null, "No Previous Question!", "Warning", JOptionPane.WARNING_MESSAGE);
			else 
			{
				current_question--;
				resetFields();
			}				
		}
		
		if(e.getSource()==playQuiz)
		{
			if(count>0)
			{
				this.dispose();
				new GameFrame(current_project);
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Please add atleast 1 Question to Play", "Warning", JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog(null, "Click 'Next' to Save question", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if(e.getSource()== delButton)
		{
			if(1+current_question>count)
				JOptionPane.showMessageDialog(null, "This question does not exist", "Warning", JOptionPane.WARNING_MESSAGE);
			else 
			{
				questionText.remove(current_question);
				optionsA.remove(current_question);
				optionsB.remove(current_question);
				optionsC.remove(current_question);
				optionsD.remove(current_question);
				ans.remove(current_question);
				
				count--;
				addFileValues();
				getQuestionCount();
				resetFields();
				
				JOptionPane.showMessageDialog(null, "Question deleted!", "Info", JOptionPane.INFORMATION_MESSAGE);			
			}
		}
		
		if(e.getSource()==editButton)
		{
			if(editing)
			{
				editing=false;
				editButton.setBackground(Color.WHITE);
			}
			else 
			{
				editing=true;
				editButton.setBackground(Color.ORANGE);
			}
		}
		
		if(e.getSource()==mainMenu)
		{
			this.dispose();
			new Main();
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		//This acts like hint, clears fields when focus is gained
		
		if(e.getSource()==questionField) 
			if(questionField.getText().equals("Question"))
				questionField.setText("");
		
		if(e.getSource()==btnA)
			if(btnA.getText().equals("A"))
				btnA.setText("");
		
		if(e.getSource()==btnB)
			if(btnB.getText().equals("B"))
				btnB.setText("");
		
		if(e.getSource()==btnC)
			if(btnC.getText().equals("C"))
				btnC.setText("");
		
		if(e.getSource()==btnD)
			if(btnD.getText().equals("D"))
				btnD.setText("");				
	}

	@Override
	public void focusLost(FocusEvent e) 
	{
		
	}
}
