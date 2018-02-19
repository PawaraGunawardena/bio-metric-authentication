package authentication;

import java.util.Random;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UserAccount {

	protected Shell shell;
	private String username;
	
	private Text text_answer;
	
	Random rn ;
	long second_remains; 
	int level;
	//private Label lblUsername;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UserAccount window = new UserAccount();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public UserAccount(){
		
	}
	
	public UserAccount(String  username, long second_remains, int level){
		this.username = username;
		this.second_remains = second_remains;
		this.level = level;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		shell = new Shell();
		shell.setSize(670, 567);
		shell.setText("User Account");
		
//		long[] secondsToType = new long[level+3];
//		long[] secondDifference = new long[level+2];
		
		AutomatedText automateText = new AutomatedText(); 
		final String text;
		
		final long start_time =System.currentTimeMillis();
		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setText(username);
		lblUsername.setBounds(10, 71, 414, 15);
		//lblUsername.setBounds(10, 71, 414, 15);
		Label label_winner = new Label(shell, SWT.NONE);
		label_winner.setAlignment(SWT.CENTER);
		label_winner.setBounds(152, 160, 344, 15);
		
		
		label_winner.setText("Type below text before "+(second_remains/1000.0)+" seconds");
		
		Group grpPuzzleOnYour = new Group(shell, SWT.NONE);
		grpPuzzleOnYour.setText("Puzzle on your skills");
		grpPuzzleOnYour.setBounds(152, 179, 344, 101);
		
		Label label_Question = new Label(grpPuzzleOnYour, SWT.NONE);
		label_Question.setBounds(43, 30, 265, 15);
		
		text =automateText.getWord(level+2);
		label_Question.setText(text);
		
		text_answer = new Text(grpPuzzleOnYour, SWT.BORDER);
		text_answer.setBounds(43, 51, 265, 21);
		
		Label label_level = new Label(shell, SWT.NONE);
		label_level.setAlignment(SWT.CENTER);
		label_level.setBounds(152, 123, 344, 15);
		label_level.setText("Level "+level);
		
		final Label label_win_lose = new Label(shell, SWT.NONE);
		label_win_lose.setAlignment(SWT.CENTER);
		label_win_lose.setBounds(204, 346, 173, 15);
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				long finish_time = System.currentTimeMillis();
				long time_take = finish_time-start_time;
				String typedValue = text_answer.getText();
				System.out.println("Typed value :"+typedValue);
				System.out.println("Typed value :"+text);
				if (time_take<=second_remains && typedValue.equals(text)){
					System.out.println("start time "+start_time);
					System.out.println("finish time "+finish_time);
					System.out.println("Time took "+time_take);
					
					System.out.println("Time had "+second_remains);
					long remaining_seconds = ((long)second_remains * 9) / 10;
					
					
					shell.setVisible(false);
					shell.dispose();
					try {
						UserAccount window = new UserAccount(("You won the level "+level),remaining_seconds,(level+1));
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else{
					System.out.println("Failed ");
					System.out.println("Time took "+time_take);
					System.out.println("Time had "+second_remains);
					label_win_lose.setText("YOU LOSE AT LEVEL "+level);
				}
			}
		});
		btnSubmit.setBounds(291, 297, 75, 25);
		btnSubmit.setText("Submit");
		
		
				
	}
}
