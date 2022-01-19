import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements ActionListener
{
	ImageIcon quizIcon;
	
	Timer timer=new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			secondsleft--;
			second.setText(String.valueOf(secondsleft));
			
			if(secondsleft==0)
			{
				if(ans.get(current_question)>0)
					showAnswer(ans.get(current_question)-1);
				else 
					showAnswer(ans.get(current_question)+1);
			}
		}
	});
	
	Scanner fileScanner;
	
	JButton btnA;
	JButton btnB;
	JButton btnC;
	JButton btnD;
	
	JButton reset;
	JButton createQuestion;
	JButton mainMenu;
	
	int count=0;
	
	ArrayList<JButton> buttons;
	
	JPanel btnPanel;
	JPanel questionPanel;
	JLabel question;
	JLabel qnum;
	
	int secondsleft=10;
	JLabel second;
	
	ArrayList<String> questionText=new ArrayList<String>();
	String[][] options; 
	
	ArrayList<Integer> ans=new ArrayList<Integer>();
	
	int current_question=0;
	int score=0;
	int total_questions;
	
	String current_project;
	
	public GameFrame(String project) 
	{		
		current_project=project;
		
		quizIcon=new ImageIcon("Quiz.png");
		this.setIconImage(quizIcon.getImage());
		
		File dirFile=new File("Bank\\"+current_project+".txt");
		
		int count=0;
		
		//For csv reference check the java project ReadWriteCSV which I made
		try 
		{
			Scanner reader=new Scanner(dirFile);
			
			//To make sure if nextLine is there and it is not an empty line
			while (reader.hasNextLine() && reader.nextLine()!=null)
				count++;
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		options=new String[count][4];	
		
		try 
		{
			Scanner reader=new Scanner(dirFile);	
			
			int qNumber=0;
			
			for(int i=0; i<count; i++)
			{
				String ar[]=reader.nextLine().split(";");
				
				ans.add(Integer.parseInt(ar[0]));
				options[qNumber][0]=ar[1];
				options[qNumber][1]=ar[2];
				options[qNumber][2]=ar[3];
				options[qNumber][3]=ar[4];
				questionText.add(ar[5]);
				
				qNumber++;
			}
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		total_questions=questionText.size();
				
		reset=new JButton("Reset");
		reset.setFont(new Font("MV Boli", Font.BOLD, 20));
		reset.setBounds(0,450,325,50);
		reset.setForeground(Color.BLUE);
		reset.setBackground(Color.WHITE);
		reset.addActionListener(this);
		reset.setFocusable(false);
		reset.setEnabled(false);
		
		createQuestion=new JButton("Create");
		createQuestion.setFont(new Font("MV Boli", Font.BOLD, 20));
		createQuestion.setBounds(325,450,162,50);
		createQuestion.setForeground(Color.GREEN);
		createQuestion.setBackground(Color.WHITE);
		createQuestion.addActionListener(this);
		createQuestion.setFocusable(false);
		createQuestion.setEnabled(false);
		
		mainMenu=new JButton("Main Menu");
		mainMenu.setFont(new Font("MV Boli", Font.BOLD, 20));
		mainMenu.setBounds(487,450,163,50);
		mainMenu.setForeground(Color.GREEN);
		mainMenu.setBackground(Color.WHITE);
		mainMenu.addActionListener(this);
		mainMenu.setFocusable(false);
		mainMenu.setEnabled(false);
		
		second=new JLabel();
		second.setText(String.valueOf(secondsleft));
		second.setForeground(Color.RED);
		second.setBounds(590,20,30,20);
		second.setFont(new Font("MV Boli", Font.BOLD, 25));
		
		qnum=new JLabel();
		qnum.setForeground(Color.RED);
		qnum.setBounds(20,20,560,20);
		qnum.setFont(new Font("MV Boli", Font.BOLD, 25));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);	
		this.setSize(650,540);
		this.setTitle("Quiz: Play Quiz");
		
		btnA=new JButton("A");
		btnB=new JButton("B");
		btnC=new JButton("C");
		btnD=new JButton("D");
		
		buttons=new ArrayList<JButton>();
		buttons.add(btnA);
		buttons.add(btnB);
		buttons.add(btnC);
		buttons.add(btnD);
		
		for(int i=0; i<buttons.size(); i++)
		{
			buttons.get(i).setFocusable(false);
			buttons.get(i).setFont(new Font("MV Boli", Font.BOLD, 20));
			buttons.get(i).addActionListener(this);
		}
		
		btnPanel=new JPanel();
		btnPanel.setBounds(0,150,650,300);	
		btnPanel.setVisible(true);
		btnPanel.setLayout(new GridLayout(2,2));
		
		question=new JLabel();
		question.setForeground(Color.WHITE);
		question.setVisible(true);
		question.setFont(new Font("MV Boli", Font.BOLD, 20));		
		
		questionPanel=new JPanel();
		questionPanel.setBounds(0,100,650,50);	
		questionPanel.setVisible(true);
		questionPanel.setBackground(Color.BLACK);
		questionPanel.add(question);
		
		for(int i=0; i<buttons.size(); i++)
			btnPanel.add(buttons.get(i));
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(second);
		this.add(btnPanel);
		this.add(reset);
		this.add(questionPanel);
		this.add(qnum);
		this.add(createQuestion);
		this.add(mainMenu);
		
		this.revalidate();
		this.repaint();
		
		getQuestion();				
	}
	public void getQuestion()
	{
		timer.start();
		
		if(current_question>=total_questions)
			showResult();
		else 
		{
			qnum.setText("Question: "+(current_question+1));
			question.setText(questionText.get(current_question));
			for(int i=0; i<buttons.size(); i++)
				buttons.get(i).setText(options[current_question][i]);
		}
	}
	
	public void showResult()
	{	
		for(int i=0; i<buttons.size(); i++)
			buttons.get(i).setEnabled(false);
		current_question=total_questions;
		second.setText("");
		question.setText("Number of Questions Correct: "+score+"/"+total_questions);
		
		reset.setEnabled(true);
		createQuestion.setEnabled(true);
		mainMenu.setEnabled(true);
		
		timer.stop();
	}
	public void showAnswer(int chose)
	{
		timer.stop();
		
		for(int i=0; i<buttons.size(); i++)
			buttons.get(i).setEnabled(false);
		
		buttons.get(ans.get(current_question)).setBackground(Color.GREEN);
		
		if(chose==ans.get(current_question))
			score++;
		else 
			buttons.get(chose).setBackground(Color.RED);
		
		Timer pause=new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0; i<buttons.size(); i++)
				{
					buttons.get(i).setBackground(null);
					buttons.get(i).setEnabled(true);
				}
					
				question.setText("");
				secondsleft=10;
				second.setText(String.valueOf(secondsleft));
				
				current_question++;
		
				getQuestion();
			}		
		});
		
		pause.setRepeats(false); //Prevents the thing inside actionPerformed to repeat more than once, this is for the delay
		pause.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if(current_question<total_questions)
		{
			for(int i=0; i<buttons.size(); i++)
			{
				if(e.getSource()==buttons.get(i))
					showAnswer(i);
			}
		}
		
		if(e.getSource()==reset)
		{
			this.dispose();
			new GameFrame(current_project);
		}
		
		if(e.getSource()==createQuestion)
		{
			this.dispose();
			new CreateQuestion(current_project);
		}
		
		if(e.getSource()==mainMenu)
		{
			this.dispose();
			new Main();
		}
		
	}	
}
