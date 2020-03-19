public class Node {
	
	private String nodeID;
	
	public Node(String ID) {
		nodeID = ID; //stored as N-X
	}
	
	public static String toString(Node n) {
		return n.nodeID;
	}
	
	public String toString() {
		return this.nodeID;
	}
	
	public int getNodeID() {
		String[] id = this.nodeID.split("-");
		int nID = Integer.parseInt(id[1]);
		return nID;
	}
}
