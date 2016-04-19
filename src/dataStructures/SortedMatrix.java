package dataStructures;

//Contains a 2 NumberedLists of values to simulate a matrix, it can also quickly get all the values within a given rectangle
public class SortedMatrix<T> {
	NumberedList<T> listX;
	NumberedList<T> listY;
	
	public SortedMatrix(int x, int y, int width, int height){
		listX = new NumberedList<T>(x, x+width);
		listY = new NumberedList<T>(y, y+height);
	}
	
	public void put(int x, int y, T obj){
		listX.put(x, obj);
		listY.put(y, obj);
	}
	
	public HashSet<T> getSet(int x, int y, int width, int height){
		HashSet<T> setA = listX.getSet(x, x+width);
		HashSet<T> setB = listY.getSet(y, y+height);
		HashSet<T> returnSet = new HashSet<T>();
		
		if(setA.size() > setB.size()){
			HashSet<T> res = setA;
			setA = setB;
			setB = res;
		}
		ArrayList<T> list = setA.getArrayList();
		
		for(int i = 0; i < list.size(); i++){
			T obj = list.get(i);
			if(obj!=null && setB.contains(obj)){
				returnSet.add(obj);
			}
		}
		return returnSet;
	}
	public HashSet<T> get(int x, int y){
		return getSet(x,y,0,0);
	}
	public ArrayList<T> get(int x, int y, int width, int height){
		HashSet<T> setA = listX.getSet(x, x+width);
		HashSet<T> setB = listY.getSet(y, y+height);
		
		return setB.getOverlap(setA).getArrayList();
	}
	public ArrayList<T> getArrayList(){
		return this.get(listX.min(), listY.min(), listX.range(), listY.range());
	}
	public void clear(){
		listX.clear();
		listY.clear();
	}
	public void remove(int x, int y){
		ArrayList<T> a = listX.getSet(x, x).getOverlap(listY.getSet(y, y)).getArrayList();
		for(int i = 0; i < a.size(); i ++){
			listX.remove(x,a.get(i));
			listY.remove(y,a.get(i));
		}
	}
	public void remove(int x, int y, T obj){
		listX.remove(x,obj);
		listY.remove(y,obj);
	}
	public String toString(){
		return this.getArrayList().toString();
	}
}
