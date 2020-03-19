import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FingerTable {
	
	public int [][] ft;
	private int ftn;
	private ArrayList<Integer> lookUpValues = new ArrayList<Integer>();
	private ArrayList<Integer> nodeLookUp = new ArrayList<Integer>(); //determines node values plus lookup values
	private ArrayList<Integer> nodesFound = new ArrayList<Integer>(); //list of actual nodes found in finger table
	public int j = 0;
	
	public FingerTable(Node n) {
		String ftID = Node.toString(n);
		String[] split = ftID.split("-");
		ftn = Integer.parseInt(split[1]);
		
		for (int m = 0; m < ChordSystem.B; m++) {
			int val = (int) Math.pow(2, m);
			lookUpValues.add(val);
			int lookUpVal = ftn + val;
			nodeLookUp.add(lookUpVal);
		}		
		int k = 0;
		for (int i = 0; i < nodeLookUp.size(); i++) {
			ftFindVal(k);
		}		
		ft = new int [lookUpValues.size()][2];
		for (int g = 0; g < lookUpValues.size(); g++) {
			ft[g][k] = lookUpValues.get(g);
			ft[g][k+1] = nodesFound.get(g);
		}
	}
	
	public String toString() {
		String result = Arrays.deepToString(this.ft).replace("], ", "|\n")
										   .replace("[[", "|")
										   .replace("]]", "|")
										   .replace("[", "|")+ "\n" + "\n";
		return result.replaceAll(", ", " | ");
	}
	
	public ArrayList<Integer> getNodesFound() {
		return this.nodesFound;
	}
	
	//compare values in nodeLookUp to values in nodeList to match lookup value to a successor node
	public void ftFindVal(int k) {
		int n = 0;
		if (nodeLookUp.get(j) > ChordSystem.nodeSpaces) { 					 //if lookup val > available nodespaces
			nodeLookUp.set(j, (nodeLookUp.get(j) - ChordSystem.nodeSpaces)); //set 
		}
		if ((nodeLookUp.get(j) > Collections.max(ChordSystem.preNodes)) && (nodeLookUp.get(j) <= ChordSystem.nodeSpaces)) {
			n = ChordSystem.preNodes.get(0);
			nodesFound.add(n);				
			j++;
		}
		else if (nodeLookUp.get(j) <= ChordSystem.preNodes.get(k)) { // if lookup value is <= next node
			n = ChordSystem.preNodes.get(k); 						 // use next node
			nodesFound.add(n);
			j++;
		}
		else if (nodeLookUp.get(j) > ChordSystem.preNodes.get(k)) { // if lookup val is > than next node
			ftFindVal(k+1);											// recursive call to find next node >= lookup val
		}
	}
}

