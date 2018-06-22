This is a login authentication solution based on biometric authentication data.

How to clone the repository:

	Get the URL, and clone it to the local directory. 
	
	Can use guidlines at https://help.github.com/articles/cloning-a-repository/ 
	
Logic behind the Solution:

	For this solution, it is based on the key strokes and the time difference between two keys presses. 
	
	1. Add the beginning user can signup using the signup button.
	
	2. When user signing up system track his characteristics, based on time differences between keys. 
	
		For this function, system give a random string to the user, and when user types it, system track the relevant time measures. 
		
	3. When user login again, system agai give a interface to type the username, and another random string to type. 
	
		Again calculate the users time differences of two key pressings. 
		
	4. Then need to do some mathematical calculation to verify the user. 
	
		At signing up time differences gives the main characteristics, and it has its own average time, and standard deviation for times.
		
		When login it takes all the time differences of the user's at that time, and calculate the average time difference for login time differences.
		
		Then calculate the Z value of that average time difference using pre calculated average of signing up time differences, signing up standard deviation.
		
		Then need to define the threshold to verify user validity. 