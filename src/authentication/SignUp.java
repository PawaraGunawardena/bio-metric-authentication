package authentication;

import java.sql.SQLException;

import logicComponent.Calculation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

//import databaseconnectivity.DatabaseConnection;
import databaseconnectivity.SQLiteTest;

public class SignUp {

	protected Shell shell;
	private Text txtUsername;
	private Text txtAthenticationTypedValue;
	static long timein;
	static long timeout;
	int counter =0;
	long[] secondsToType = new long[26];
	long[] secondDifference = new long[25];
	AutomatedText automateText;
	String text;
	//DatabaseConnection db;
	
	SQLiteTest dbSQLite;
	String username;
	long average;
	long variance;
	long standard_deviation;
	String error = "";
	
//	public static void main(String[] args) {
//		try {
//			SignUp window = new SignUp();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	public SignUp(){
		
	}
	public SignUp(String error){
		this.error = error;
	}

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
	
	protected void createContents() {
		
		
		shell = new Shell();
		shell.setSize(670, 567);
		//shell.setText("SWT Application");
		shell.setText("Biometric Authentication");
		
		
		Group loginGroup = new Group(shell, SWT.NONE);
		loginGroup.setText("Fill the SignUpForm");
		loginGroup.setBounds(156, 71, 488, 344);
		
		Label lblLogin = new Label(loginGroup, SWT.NONE);
		lblLogin.setBounds(220, 22, 55, 15);
		lblLogin.setText("SignUp");
		
		Label lblBiometricauthentication = new Label(shell, SWT.NONE);
		lblBiometricauthentication.setBounds(272, 26, 140, 15);
		lblBiometricauthentication.setText("Bio-Metric-Authentication");
		
		Label lblUsername = new Label(loginGroup, SWT.NONE);
		lblUsername.setBounds(36, 68, 55, 15);
		lblUsername.setText("Username");
		
		txtUsername = new Text(loginGroup, SWT.BORDER);
		txtUsername.setBounds(142, 65, 336, 21);
		
		
		Label lbltryToType = new Label(loginGroup, SWT.NONE);
		lbltryToType.setBounds(119, 110, 323, 15);
		lbltryToType.setText("*Try to type following text to give login identity");
		
		Button btnSignupNewAccount = new Button(shell, SWT.NONE);
		btnSignupNewAccount.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.setVisible(false);
				shell.dispose();
				try {
					SignUp window = new SignUp("Give your new user name and type the given Text");
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
				try {
					shell.setVisible(false);
					shell.dispose();
					Login window = new Login("If you have an account type user name and type the given text.");
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(10, 135, 122, 25);
		btnLogin.setText("User Login");
		
		Group groupVerify = new Group(loginGroup, SWT.NONE);
		groupVerify.setText("Type the given Text");
		groupVerify.setBounds(21, 147, 445, 159);
		
		final Label lblAuthenticationValue = new Label(groupVerify, SWT.NONE);
		lblAuthenticationValue.setBounds(24, 32, 411, 34);
		automateText = new AutomatedText();
		text =automateText.getWord(25);
		lblAuthenticationValue.setText(text);
		
		final Label lblFailedMesssage = new Label(shell, SWT.NONE);
		lblFailedMesssage.setBounds(156, 462, 488, 15);
		
		Button btnTryAnother = new Button(groupVerify, SWT.NONE);
		btnTryAnother.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				text =automateText.getWord(25);
				lblAuthenticationValue.setText(text);
			}
		});
		
		txtAthenticationTypedValue = new Text(groupVerify, SWT.BORDER);
		txtAthenticationTypedValue.setBounds(22, 73, 413, 34);
		
		//Event handler for change text
		ModifyListener listener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
					valueChanged((Text) e.widget);
			}
		};
		txtAthenticationTypedValue.addModifyListener(listener);
		
		btnTryAnother.setBounds(313, 124, 122, 25);
		btnTryAnother.setText("Try Different Text");
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//submission code
				newArray();
				username = txtUsername.getText();
				average =  Calculation.returnAverage(secondDifference);
				variance =Calculation.returnVariance(secondDifference, average);
				standard_deviation=Calculation.standardDeviation(variance);
				//db=new DatabaseConnection();
				dbSQLite = new SQLiteTest();
				//db.setResult(username, average);
				String typedValue = txtAthenticationTypedValue.getText();
				
				if(username!="" && typedValue.equals(text)){
					
					try {
						dbSQLite.addUser(username, average, variance, standard_deviation);
						
						
						shell.setVisible(false);
						shell.dispose();
						Login window = new Login("If you have an account type user name and type the given text.");
						window.open();
						
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
//					txtUsername.setText("");
//					txtAthenticationTypedValue.setText("");
//					text =automateText.getWord(25);
//					lblAuthenticationValue.setText(text);
//					counter = 0;
					
					
				}else if (username!="" && !(typedValue.equals(text))){
	//				JOptionPane.showMessageDialog(null,"Type the username");
					//String showInputDialog = JOptionPane.showInputDialog(null, "Type your username! If don't have user Account Click Sign in Button."); 
					//JOptionPane.showMessageDialog(null, "Error username"
					System.out.println("Type the Correct Text to SignUp");
					shell.setVisible(false);
					shell.dispose();
					try {
						SignUp window = new SignUp("Type the Correct Text to SignUp");
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else if(username =="" && (typedValue.equals(text))){
					System.out.println("Invalid username. You should have a username to SignUp.");
					shell.setVisible(false);
					shell.dispose();
					try {
						SignUp window = new SignUp("Invalid username. You should have a username to SignUp.");
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else{
					System.out.println("Invalid username and Text.");
					shell.setVisible(false);
					shell.dispose();
					try {
						SignUp window = new SignUp("Invalid username and Text.");
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnSubmit.setBounds(548, 431, 75, 25);
		btnSubmit.setText("Submit");
		
		Label label_error = new Label(shell, SWT.NONE);
		label_error.setBounds(29, 488, 516, 15);
		label_error.setText(error);
	}
	

	protected void valueChanged(Text widget) {
		// TODO Auto-generated method stub
		if (counter<26){
		long x = System.currentTimeMillis();
		System.out.println("Times to type, counter "+counter+" ,time "+ x);
		//System.out.println("My values "+x+"\n");
//		secondsToType = addLong(secondsToType, x);
		secondsToType[counter] = x;
		counter ++ ;
		
		}
	}
	
	protected void newArray(){
		for(int x=0 ; x<25;x++){
//			long[] secondsToType = new long[11];
			secondDifference[x] = secondsToType[x+1]-secondsToType[x];
			System.out.println("Secons Differences "+x+": "+secondDifference[x]);
		}
	}

}
