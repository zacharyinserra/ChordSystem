import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

public class ChordSystem {
	
	public static ArrayList<Node> nodeList = new ArrayList<>();
	public static ArrayList<Integer> preNodes = new ArrayList<Integer>();
	public ArrayList<FingerTable> ftList = new ArrayList<FingerTable>();
	public static int nodeSpaces;
	public static int B;
	private int nodes;
	private boolean choice = true;
	private Random rand = new Random();
	public static Node nStart;
	public static int keyID;
	public static Node solNode;
	
	public static void main(String[] args) {
		new ChordSystem();
	}
	
	public ChordSystem() {		
		
		Scanner s = new Scanner(System.in);
		
		while (choice) {
			System.out.println("Enter number of node ID spaces possible. 2^(input)" + "\n" + "Must be between 5 and 10: ");
			B = s.nextInt();
			if (B >= 5 && B <= 10) {
				nodeSpaces = (int) Math.pow(2, B);
				choice = false; }
			else { choice = true; System.err.println("Invalid input" + "\n"); }
		}		
		while (!choice) {
			System.out.println("Enter number of possible nodes in chord." + "\n" + "Must be between 5 and 15: ");
			nodes = s.nextInt();
			if (nodes >= 5 && nodes <= 15) {
				choice = true; }
			else { choice = false; System.err.println("Invalid input" + "\n"); }
		}
		s.close();
		
		//creates sorted list of nodes in chord
		createNodeLists();
		for (Node n: nodeList) {
			FingerTable FT = new FingerTable(n);
			ftList.add(FT);
		}
		
		System.out.println(nodeList);
		System.out.println("Starting node: " + nStart);
		System.out.println("Key ID: K-" + keyID);
		//System.out.println("Solution node: " + solNode);
		
		findKey(nStart);
	}

	private void createNodeLists() {
		for (int i = 0; i < nodes; i++) {
			int nodeNum = rand.nextInt(nodeSpaces-1);
			if (!preNodes.contains(nodeNum)) {
				preNodes.add(nodeNum);
			}
			else {
				i--;
			}
		}
		Collections.sort(preNodes);
		for (int i: preNodes) {
			String id = "N-"+i;
			Node n = new Node(id);
			nodeList.add(n); 
		}
		nStart = nodeList.get(1);
		keyID = rand.nextInt(nodeSpaces-1);
		for (int q = 0; q < preNodes.size(); q++) {
			if (keyID == preNodes.get(q)) {
				solNode = nodeList.get(q);
				break;
			}
			else if (keyID > Collections.max(preNodes) || (keyID <= preNodes.get(0))){
				solNode = nodeList.get(0);
				break;
			}
			else if ((keyID > preNodes.get(q)) && keyID <= preNodes.get(q+1)) {
				solNode = nodeList.get(q+1);
				break;
			}
		}
	}
	
	private void findKey(Node nSt) {
		
		int foundnst = 0;
		int foundSol = 1;
		
		for (int a = 0; a < nodeList.size(); a++) { // find position of start node
			if (nSt.equals(nodeList.get(a))) {
				foundnst = a;
			}
			if (solNode.equals(nodeList.get(a))) { // find position of solution node
				foundSol = a;
			}
		}
		if (foundnst == (foundSol-1)) { // if nStart is right behind solution node
			System.out.println(nodeList.get(foundnst));
			System.out.println(ftList.get(foundnst));
		}
//		else if (foundnst == foundSol) { // if nStart is the solution node
//			System.out.println(nodeList.get(foundnst));
//			System.out.println(ftList.get(foundnst));
//		}
		else {
			System.out.println(nodeList.get(foundnst));
			System.out.println(ftList.get(foundnst));
			int randNum = rand.nextInt(ftList.get(foundnst).getNodesFound().size()-1);
			int searchNode = ftList.get(foundnst).getNodesFound().get(randNum);
			for (Node n: nodeList) {
				if (n.getNodeID() == searchNode) {
					findKey(n);
				}
			}
		}
	}
}


