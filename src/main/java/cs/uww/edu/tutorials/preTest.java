/**
 * 
 */
package cs.uww.edu.tutorials;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hien
 *
 */

import smile.Network;
import smile.learning.*;

public class preTest extends HttpServlet {

	private final int numQuestions= 5;
	private final int numChoice   = 5;
	private final int aScore      = 10;
	private String answers[] = {"Ability to learn", "training set","answer","count", "none"};
	private final double threshold = 0.75*(numQuestions*aScore);
	
	private double getScore(HttpServletRequest req) {
	  double score = 0;
	  int currElt;
	  String currSelection;
	  
	  for (int i=0; i<numQuestions; i++) {
	    currElt = i*numChoice;
	    String questionName = "q"+(i+1);	
	    System.out.println(questionName);
	    currSelection = req.getParameter(questionName);
	    System.out.println(currSelection);
	    if (currSelection != null)
	    	if (currSelection.equals(answers[i])) 
	    		score+= aScore;
	  }
	  return score;  
	}

	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {
		//String smileLibrary = "C:\\Hien\\UWW\\Teaching\\Cs332\\AI\\jsmile.dll";
		String userProfile ="C:\\Hien\\UWW\\Research\\Grant\\FacultyDevelopmentGrant\\2015\\knnStructure11New.xdsl";
	  
		resp.setContentType("text/html");

		System.out.println("We are here"+System.getProperty("java.library.path"));
    	System.load("C:\\Hien\\UWW\\Teaching\\Cs332\\AI\\jsmile.dll");
    
    	Network net = new Network();
     	
    	// Loading the user profile DSL
    	net.readFile(userProfile);
    
		double score = getScore(req);
		System.out.println("Pre-test score ="+ score);
		if (score >= threshold) {
			net.clearAllEvidence();
			net.setEvidence("PreTestResult", "Intermediate");
			// Doing update belief
			net.updateBeliefs();
		}
		else {
			net.clearAllEvidence();
			net.setEvidence("PreTestResult", "Novince");
			// Doing update belief
			net.updateBeliefs();
		}
    	 // Printing out the user choice
    	 // Going through all the children of preTestResult
    	 int childId[] = net.getChildren("PreTestResult");
    	 int maxId =childId[0];
    	 for (int i=1; i< childId.length; i++) {
    		 if (net.getNodeValue(childId[i])[0] > net.getNodeValue(maxId)[0]) 
    			 maxId = childId[i];
    	 	 }
    	 System.out.println(" Next module would be: "+ net.getNodeName(maxId));
    	 
    	 if (net.getNodeName(maxId).equals("Intro")) {
    	    // Load the module indicating introduction here
    		 resp.getWriter().println("<html> <head> <meta charset=\"ISO-8859-1\"> "+
    		 "<title>Introduction to kNN</title>" +
    		 "<link rel=\"stylesheet\" href=\"css/Styles.css\">"+
    		 "</head> <body>"+
    		 "<img src=\"MainLectureFlowchart.jpg\" height=\"200px\" width=\"1300px\" usemap=\"#GCB\">" +
    		 "<map name=\"GCB\">"+
    		 "<area shape=\"rect\" coords=\"130,32,450,164\" href=\"GCBvideo.html\" alt=\"Sun\">"+ 
    		 "<area shape=\"rect\" coords=\"533,12,799,185\" href=\"GCBvideo2.html\" alt=\"Sun\">"+ 
    		 "<area shape=\"rect\" coords=\"1128,12,882,185\" href=\"GCBvideo3.html\" alt=\"Sun\">"+ 
    		 "</map> </body> </html>");
    	 }
    	 else if (net.getNodeName(maxId).equals("ConceptModule")) {
    		 resp.getWriter().println("<html> <head> <meta charset=\"ISO-8859-1\">"+
    		 "<title>Introduction to kNN</title>"+
    		 "<link rel=\"stylesheet\" href=\"css/Styles.css\">"+
    		 "</head> <body>"+
    		 "<div id=\"videoalign\" align=\"center\"> <div>"+
    		 "<h1>Part 1</h1>"+
    		 "<video src=\"video2.mp4\" width=\"640\" height=\"480\" autoplay controls></video>"+
    		 "</div> <div>"+
    		 "<h1>Part 2</h1>"+
    		 "<video src=\"video3.mp4\" width=\"640\" height=\"480\" controls></video>"+
    		 "</div></div>"+
    		 "<button class=\"nbutton nbuttonbackgroundsvg\" onclick=\"window.location='index.jsp'\" title=\"next\" id=\"next\" name=\"next\" type=\"button\" value=\"next\">Home</button>"+
    		 "</body></html>");
    	 }
    	
    }
    
    
}
