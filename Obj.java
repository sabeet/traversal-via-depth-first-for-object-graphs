import java.lang.reflect.Field;
import java.util.ArrayList;


abstract class Obj
{
	boolean visited = false; // indicates if this object has been visited by traverse() function
	static ArrayList<String> specialAL = new ArrayList<>(); //We shall be using this to store every new string line for output

	Obj()

	// This constructor will be invoked every time the constructor of any descendant class is invoked.

	{
		//object names go here...
		OutputSorter.AL.add(this.getClass().getSuperclass().getName());
		OutputSorter.AL.add(this.getClass().getName());
		OutputSorter.AL2.add(this.getClass().getName());
	}


	public void traversal(CounterObj rustic, String arg1, String arg2) {


		Field[] field_inherited = this.getClass().getSuperclass().getDeclaredFields(); //instantiate inherited field
		Field[] fields = this.getClass().getDeclaredFields(); //instantiate field

		//super enhanced forloop
		for (Field field : field_inherited) { //this for loop drives through the superclass field and sets unvisited items to visited
			try {
				if (field.get(this) instanceof Obj) {
					if (!this.visited) {
						this.visited = true;
						specialAL.add(this.getClass().getName()); //add to the arraylist
						System.out.println(this.getClass().getName()); //print for debugging

					}
					((Obj) field.get(this)).traversal(rustic, arg1, arg2); //naturally recurse through traversal till we reach a leaf in the graph
					rustic.count++;
				} else {
					specialAL.add(this.toString());
					System.out.println(this.toString());

				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		//regular forloop
		for (Field field : fields) { //this for loop drives through the class field and sets unvisited items to visited
			try {
				if (field.get(this) instanceof Obj) {
					if (!this.visited) {
						this.visited = true;
						specialAL.add(this.getClass().getName()); //add to the arraylist
						System.out.println(this.getClass().getName());

					}
					((Obj) field.get(this)).traversal(rustic, arg1, arg2); //naturally recurse through traversal till we reach a leaf in the graph
					rustic.count++;
				} else if (!(this instanceof Assignment)) {
					specialAL.add(this.toString());
					System.out.println(this.toString());

				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		}

	}

