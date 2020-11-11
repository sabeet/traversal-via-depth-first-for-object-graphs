import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;

public abstract class Interpreter extends Parser
{
	public static HashMap<String, Val> varState = new HashMap<String, Val>(); 
	              // program state, i.e., virtual memory for variables

	public static void arrayWriter(String arg1, String arg2) throws IOException {
		CounterObj special = new CounterObj();
		BufferedWriter bw = new BufferedWriter(new FileWriter(arg2));
		OutputSorter.OutputSorter(arg1, arg2);

			for(String z : OutputSorter.AL3){
				bw.write(z.toString() + '\n');
			}

			bw.write("-----------------------------" + "\n" + "Object traversal has begun" + "\n\n");

			for(String x : Obj.specialAL){
				bw.write(x.toString() + '\n');
			}
				bw.write("There are " + Obj.specialAL.size() + " objects visited from this graph");
				bw.flush();
				bw.close();
				Obj.specialAL.clear();
	}
		
	
	public static void main(String argv[])
	{
		// argv[0]: input file containing an assignment list
		// argv[1]: output file displaying the parse tree
		// argv[2]: output file displaying the numbers of constructed objects and
		//          the visited objects in order of the depth-first traversal
		
		setIO( argv[0], argv[1] );
		setLex();

		getToken();

		AssignmentList assignmentList = assignmentList(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + " : Syntax Error, unexpected symbol where id expected");
		else if ( ! errorFound )
		{
			assignmentList.printParseTree("");       // print the parse tree in linearly indented form in argv[1] file
			assignmentList.M(varState);              // interpret the assignment list
			System.out.println(varState.toString()); // print the program state on the terminal
		}

		CounterObj special = new CounterObj();
		assignmentList.traversal(special, argv[1], argv[2]);// perform depth-first traversal from assignmentList
		try {
			arrayWriter(argv[1],argv[2]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(special.count);
		System.out.println(Obj.specialAL);


		closeIO();
	}
}
