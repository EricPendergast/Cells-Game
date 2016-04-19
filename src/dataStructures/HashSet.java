package dataStructures;

public class HashSet<T>{
	private NumberedList<T> list = new NumberedList<T>(-1000000,1000000);
	
	public void add(T obj){
		//System.out.println("L"+list.get(obj.hashCode()%list.max()) + " " + obj);
		list.put(obj.hashCode()%list.max(), obj);
	}
	public void addAll(ArrayList<T> list){
		for(int i = 0; i < list.size(); i++){
			this.add(list.get(i));
		}
	}
	public ArrayList<T> getArrayList(){
		return list.getArrayList();
	}
	public boolean contains(T obj){
		if(list.get(obj.hashCode()%list.max()).size()>0)
			return true;
		return false;
	}
	public int size(){
		return list.size();
	}
	public String toString(){
		return "" + list.getArrayList();
	}
	public void remove(T obj){
		list.remove(obj.hashCode()%list.max(),obj);
	}
	//Returns a hashset of all the values shared by this and h
	public HashSet<T> getOverlap(HashSet<T> setB){
		HashSet<T> setA = this;
		if(setA.size() > setB.size()){
			HashSet<T> res = setA;
			setA = setB;
			setB = res;
		}
		ArrayList<T> list = setA.getArrayList();
		HashSet<T> returnSet = new HashSet<T>();
		for(int i = 0; i < list.size(); i++){
			T obj = list.get(i);
			if(obj == null)
				continue;
			if(setB.contains(obj)){
				returnSet.add(obj);
			}
		}
		return returnSet;
	}
}
