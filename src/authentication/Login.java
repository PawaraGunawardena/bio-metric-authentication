package authentication;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import databaseconnectivity.SQLiteTest;

import java.sql.*;

import logicComponent.Calculation;

public class Login {

	protected Shell shell;
	private Text txtUsername;
	private Text txtAthenticationTypedValue;
	static long timein;
	static long timeout;
	
	long[] secondsToType = new long[26];
	long[] secondDifference = new long[25];
	
	int counter =0;
	SQLiteTest dbSQLite;
	 
	AutomatedText automateText ;
	String text;
	
	String username;
	long average_current;
	long variance_current;
	long standard_deviation_current;
	
	long average_db;
	long variance_db;
	long standard_deviation_db;
	
	private String error ="";
	/**
	 * Launch the application.
	 * @param args
	 */
	//this is the main method in the Login page and in the whole application. 
	//Initial login page of the App
	public static void main(String[] args) {
		try {
			Login window = new Login("If you have an account type user name and type the given text.");
			window.open();
			timein = System.currentTimeMillis();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Login(){
		
	}
	public Login(String error){
		this.error = error;
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
	
	//Method which call inside the event handling modifyText method of the text box
	//Track the time differences between each keyboard presses.
	protected void valueChanged(Text widget) {
		if (counter <26){
			long x = System.currentTimeMillis();
			System.out.println("My values "+x+"\n");
			secondsToType[counter] = x;
			counter ++ ;
		}else{
			//counter =0;
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		timein = System.currentTimeMillis();
		shell = new Shell();
		shell.setSize(670, 567);
		shell.setText("Biometric Authentication");
		
		Group loginGroup = new Group(shell, SWT.NONE);
		loginGroup.setText("Fill the Login Form");
		loginGroup.setBounds(156, 71, 488, 344);
		
		Label label_error = new Label(shell, SWT.NONE);
		label_error.setBounds(37, 481, 454, 15);
		label_error.setText(error);
		
		txtUsername = new Text(loginGroup, SWT.BORDER);
		txtUsername.setBounds(142, 65, 336, 21);
		
		Label lblUsername = new Label(loginGroup, SWT.NONE);
		lblUsername.setBounds(36, 68, 55, 15);
		lblUsername.setText("Username");
		
		Label lblLogin = new Label(loginGroup, SWT.NONE);
		lblLogin.setBounds(220, 22, 55, 15);
		lblLogin.setText("Login");
		
		Label lbltryToType = new Label(loginGroup, SWT.NONE);
		lbltryToType.setBounds(119, 110, 323, 15);
		lbltryToType.setText("*Try to type following text to verify your identity");
		
		Group groupVerify = new Group(loginGroup, SWT.NONE);
		groupVerify.setText("Type the given Text");
		groupVerify.setBounds(21, 147, 445, 159);
		
		final Label lblAuthenticationValue = new Label(groupVerify, SWT.NONE);
		lblAuthenticationValue.setBounds(24, 32, 411, 34);
		automateText = new AutomatedText();
		text =automateText.getWord(25);
		lblAuthenticationValue.setText(text);
		
		txtAthenticationTypedValue = new Text(groupVerify, SWT.BORDER);
		txtAthenticationTypedValue.setBounds(22, 73, 413, 34);
		
		//Event handling for change text
		ModifyListener listener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				        valueChanged((Text) e.widget);
			}
		};
		
		txtAthenticationTypedValue.addModifyListener(listener);
		
		final Label lblFailedMesssage = new Label(shell, SWT.NONE);
		lblFailedMesssage.setBounds(22, 481, 488, 15);
		
		Button btnTryAnother = new Button(groupVerify, SWT.NONE);
		
		btnTryAnother.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//When create the login window creates a arbitrary word or sentence 
				text =automateText.getWord(25);
				lblAuthenticationValue.setText(text);
			}
		});
		
		btnTryAnother.setBounds(313, 124, 122, 25);
		btnTryAnother.setText("Try Different Text");
		
		Label lblBiometricauthentication = new Label(shell, SWT.NONE);
		lblBiometricauthentication.setBounds(272, 26, 140, 15);
		lblBiometricauthentication.setText("Bio-Metric-Authentication");
		
		Button btnSignupNewAccount = new Button(shell, SWT.NONE);
		
		//Event handling Button for sign up form
		btnSignupNewAccount.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.setVisible(false);
				shell.dispose();
				try {
					SignUp window = new SignUp("Give new user name and type the given Text");
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnSignupNewAccount.setBounds(10, 87, 122, 25);
		btnSignupNewAccount.setText("Signup new Account");
		
		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
					shell.setVisible(false);
					shell.dispose();
					try {
						Login window = new Login("If you have an account type user name and type the given text.");
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			
		});
		btnLogin.setBounds(10, 135, 122, 25);
		btnLogin.setText("User Login");
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				newArray();//Calling method to get time differences of each typing
				
				//Getting user inputs
				username = txtUsername.getText();
				
				//Doing calculations to identify user identity
				average_current = Calculation.returnAverage(secondDifference);
				variance_current = Calculation.returnVariance(secondDifference, average_current);
				standard_deviation_current = Calculation.standardDeviation(variance_current);
			
				//For connecting with the embedded database
				dbSQLite = new SQLiteTest();
				String typedValue = txtAthenticationTypedValue.getText();
				
				if(username!="" && typedValue.equals(text)){
					
					try {
						//Getting previously stored users own specific calculated data from the database
						average_db = (long) dbSQLite.getUserAverage(username);
						variance_db =(long)dbSQLite.getUserVariance(username);
						standard_deviation_db = (long)dbSQLite.getUserStandardDeviation(username);
						
						//For the convenient of coding reading values
						System.out.println("Current average "+average_current);
						System.out.println("DB average"+average_db);
						
						System.out.println("Current variance "+variance_current);
						System.out.println("DB variance "+variance_db);
						
						System.out.println("Current standard deviation "+standard_deviation_current);
						System.out.println("DB standard deviation "+standard_deviation_db);
						
						
						
						
						//Getting statistic value (z) for login user
						/*
						 *Checking this typing reaction time difference with following user name's typing statistics
						 * What here doing is,
						 * 		 calculating z value for the typing reaction time difference of the typer
						 * 		 by taking average, variance and standard deviation
						 * 		 from the username's sign up data set 
						
						*/
						float z  = ((float)average_current-(float)average_db)/((float)standard_deviation_db);
							System.out.println("Z value "+z);
						if(-0.38<=z && 0.38>=z){
							
							shell.setVisible(false);
							shell.dispose();
							try {
								UserAccount window = new UserAccount("Hii, "+username+" is logged in by computer manipulation ability. " +
//										"You have an identity based on your computer manipulation ability" +
//										". Lets play below game on your own level. Now you don't need to " +
//										"handle your game complexity.\n\n" +
//										"**Here we try to " +
//										"find an authentication method based on computer manipulating skill, " +
//										"And it used to give user own system/ game/ software. " +
//										"Complexity of the system/game/software depend on users computer manipulating skills.**" +
										"",average_db*10,1);
								window.open();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							
						}else{
							System.out.println("Insufficient z value");
								
							shell.setVisible(false);
							shell.dispose();
							try {
								Login window = new Login("You cannot identify as the correct user." +
										"Try again with your normal typing pattern");
								window.open();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					} catch (ClassNotFoundException e2) {
						System.out.println("Your given username Not valid. Try Again !!!");
						shell.setVisible(false);
						shell.dispose();
						try {
							Login window = new Login("Your given username Not valid. Try Again !!!");
							window.open();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						//e2.printStackTrace();
						System.out.println("Your given username Not valid. Try Again !!!");
						
						shell.setVisible(false);
						shell.dispose();
						try {
							Login window = new Login("Your given username Not valid. Try Again !!!");
							window.open();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				
				
					
					
//					else{
						//lblFailedMesssage.setText("Login Attempt Failed. Try Again !!!"
																				//+(timeout-timein)
																					//);
					//Conditions to check user inputs are empty or invalid
					
				}else if (username!="" && !(typedValue.equals(text))){
	//				JOptionPane.showMessageDialog(null,"Type the username");
					//String showInputDialog = JOptionPane.showInputDialog(null, "Type your username! If don't have user Account Click Sign in Button."); 
					//JOptionPane.showMessageDialog(null, "Error username"
					System.out.println("Type the Correct Text to login");
					shell.setVisible(false);
					shell.dispose();
					try {
						Login window = new Login("Type the Correct Text to login");
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else if(username =="" && (typedValue.equals(text))){
					System.out.println("Invalid username. You should have a username to Login.");
					shell.setVisible(false);
					shell.dispose();
					try {
						Login window = new Login("Invalid username. You should have a username to Login.");
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else{
					System.out.println("Invalid username and Text.");
					shell.setVisible(false);
					shell.dispose();
					try {
						Login window = new Login("Invalid username and Text.");
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnSubmit.setBounds(547, 441, 75, 25);
		btnSubmit.setText("Submit");
		
		
	}
	
	//get the time differences between each clicks and save in the memory
	protected void newArray(){
		for(int x=0 ; x<(secondsToType.length-1);x++){
			secondDifference[x] = secondsToType[x+1]-secondsToType[x];
		}
	}
}
